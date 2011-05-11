// cloudMidiPlayer.java

// This file provides a class for storing and playing a song.
// It also generates and holds individual note pitch data for playing
// whenever a note is pressed on the user interface.

import javax.sound.midi.*;

public class cloudMidiPlayer 
{
	private static final int[] SCALE = {60, 62, 64, 67, 69};
	private static final int INSTRUMENTS = 5;
	private static final int SCALENOTES = 5;
	private static final int OCTAVES = 2;
	private static final int DEFAULTBPM = 120;
	
	private float ticksPerSecond;
	private Sequencer seq;
	private Synthesizer synth;
	
	private Sequence[][] noteSequences;
	private Sequence song;
	
	// Set of enum values for the list of instruments Cloud Composer supports.
	public enum SequenceInst 
	{
		PIANO(0), GUITAR(1), DRUM(2), TRUMPET(3), VIOLIN(4);
		
		final int value;
		SequenceInst(int value) {
			this.value = value;
		}
	}
	
	// Constructs a cloudMidiPlayer with the default BPM.
	public cloudMidiPlayer() throws Exception 
	{
		loadMidiSystem();
		setTempo(DEFAULTBPM);
		noteSequences = new Sequence[INSTRUMENTS][SCALENOTES * OCTAVES];
		generateNotes();
	}
	
	// Sets the tempo of the song using the provided BPM. 
	public void setTempo(float bpm) throws InvalidMidiDataException 
	{
		pause();
		seq.setTempoInBPM(bpm);
		ticksPerSecond = 1800 * bpm;
		generateNotes();
	}
	
	// Plays the sequence previously loaded.
	public void play() {
		seq.start();
	}
	
	public void playNote(SequenceInst inst, int pitch) throws InvalidMidiDataException 
	{
		pause();
		seq.setSequence(noteSequences[inst.value][pitch]);
		play();
	}
	
	// Sets the song to the provided sequence and plays the it.
	public void playSong(Sequence s) throws InvalidMidiDataException 
	{
		pause();
		setSequence(s);
		play();
	}
	
	// Sets the song to the provided sequence.
	public void setSequence(Sequence s) throws InvalidMidiDataException 
	{
		song = s;
		seq.setSequence(song);
	}
	
	// Pauses the song.
	public void pause() 
	{
		seq.stop();
	}
	
	// Stops the song and returns to the beginning.
	public void stop() 
	{
		seq.stop();
		seq.setTickPosition(0);
	}
	
	public void setPlayTime(float percent) 
	{
		long songLength = seq.getTickLength();
		seq.setTickPosition((long) (songLength * percent));
	}
	
	// Returns the column of the playback bar.
	public int playbackBarColumn() 
	{
		int currentTick = (int) seq.getTickPosition();
		return (int) (currentTick / (ticksPerSecond / 16));
	}
	
	
// PRIVATE METHODS
	
	// Prepares the Midi System for use.
	private void loadMidiSystem() throws Exception 
	{
		seq = MidiSystem.getSequencer();
		synth = MidiSystem.getSynthesizer();
		
		if (seq == null) {
		    throw new Exception("Sequencer device not supported.");
		} else if (synth == null) {
			throw new Exception("Synthesizer device not supported.");
		} else {
		    // Acquire resources and make operational.
		    seq.open();
		    synth.open();
		}
	}
	
	// Adds a note to the sequence provided with the provided details.
	private void addNote(Sequence s, SequenceInst inst, 
						 int pitch, int startPos, int stopPos) 
						 throws InvalidMidiDataException 
	{
		Track t = s.getTracks()[0];
		ShortMessage m = new ShortMessage();
		m.setMessage(ShortMessage.NOTE_ON, 0, pitch / SCALENOTES * 12 + SCALE[inst.value], 100);
		t.add(new MidiEvent(m, startPos));
		
		ShortMessage m2 = new ShortMessage();
		m2.setMessage(ShortMessage.NOTE_OFF, 0, pitch / SCALENOTES * 12 + SCALE[inst.value], 100);
		t.add(new MidiEvent(m, stopPos));
	}
	
	// Generates the Sequences required for single note playback.
	private void generateNotes() throws InvalidMidiDataException 
	{
		int ticksPerFrame = (int) ticksPerSecond * 30;
		for (SequenceInst inst : SequenceInst.values()) {
			for (int pitch = 0; pitch < SCALENOTES * OCTAVES; pitch++) {
				Sequence s = new Sequence(Sequence.SMPTE_30, ticksPerFrame);
				addNote(s, inst, pitch, 0, (int) ticksPerSecond / 4);
				noteSequences[inst.value][pitch] = s;
			}
		}
		
	}
}
