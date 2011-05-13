// cloudMidiPlayer.java

// This file provides a class for storing and playing a song.
// It also generates and holds individual note pitch data for playing
// whenever a note is pressed on the user interface.

package CloudComposerGroup;

import javax.sound.midi.*;

public class CloudMidiPlayer 
{
	private static final int[] SCALE = {60, 62, 64, 67, 69};
	//private static final int INSTRUMENTS = 5;
	private static final int SCALENOTES = 5;
	private static final int OCTAVES = 2;
	private static final int DEFAULTBPM = 120;
	
	private static float ticksPerSecond;
	private Sequencer seq;
	private Synthesizer synth;
	
	private Sequence[][] noteSequences;
	private Sequence song;
	
	public final SequenceInst[] instruments = SequenceInst.values();
	
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
	public CloudMidiPlayer() throws Exception 
	{
		loadMidiSystem();
		noteSequences = new Sequence[instruments.length][SCALENOTES * OCTAVES];
		setTempo(DEFAULTBPM);
	}
	
	// Sets the tempo of the song using the provided BPM. 
	public void setTempo(float bpm) throws InvalidMidiDataException 
	{
		pause();
		seq.setTempoInBPM(bpm);
		ticksPerSecond = 1800 * bpm;
		generateNotes();
	}
	
	public int getTicksPerFrame() 
	{
		return (int) ticksPerSecond * 30;
	}

	// Plays the sequence previously loaded.
	public void play() throws InvalidMidiDataException 
	{
		pause();
		seq.setSequence(song);
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
	
	// Sets the current place in the song based on a percentage of the song length.
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
	
	// Adds a note to the sequence provided with the provided details.
	// startPos and stopPos are in terms of the column locations,
	// and pitch is also in terms of the row location of the note.
	// This is done here to enforce a specific format related to our Midi Player.
	public static void addNote(Sequence s, SequenceInst inst, 
						 int pitch, int startPos, int stopPos) 
						 throws InvalidMidiDataException 
	{
		int startTick = (int) (startPos * ticksPerSecond / 16);
		int stopTick = (int) (stopPos * ticksPerSecond / 16);
		Track t = s.getTracks()[inst.value];
		ShortMessage m = new ShortMessage();
		int realPitch = pitch / SCALENOTES * 12 + SCALE[pitch % SCALENOTES];
		m.setMessage(ShortMessage.NOTE_ON, 0, realPitch, 100);
		t.add(new MidiEvent(m, startTick));
		
		ShortMessage m2 = new ShortMessage();
		m2.setMessage(ShortMessage.NOTE_OFF, 0, realPitch, 100);
		t.add(new MidiEvent(m, stopTick));
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
	
	// Generates the Sequences required for single note playback.
	private void generateNotes() throws InvalidMidiDataException 
	{
		int ticksPerFrame = getTicksPerFrame();
		for (SequenceInst inst : SequenceInst.values()) {
			for (int pitch = 0; pitch < SCALENOTES * OCTAVES; pitch++) {
				Sequence s = new Sequence(Sequence.SMPTE_30, ticksPerFrame);
				addNote(s, inst, pitch, 0, 4);
				noteSequences[inst.value][pitch] = s;
			}
		}
		
	}
}
