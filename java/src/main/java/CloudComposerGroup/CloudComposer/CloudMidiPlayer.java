package CloudComposerGroup.CloudComposer;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.midi.*;

/**
 * cloudMidiPlayer.java
 * This file provides a class for storing and playing a song.
 * It also generates and holds individual note pitch data for playing
 * whenever a note is pressed on the user interface.
 */
public class CloudMidiPlayer 
{
	public static final int[] SCALE = {60, 62, 64, 67, 69};
	public static final int OCTAVES = 2;
	public static final int DEFAULTBPM = 120;
	private float bpm;
	private MidiEvent endSong;
	
	public static final float TICKSPERFRAME = 4;
	
	private Sequencer seq;
	private Synthesizer synth;
	
	private Sequence[][] noteSequences;
	private Sequence song;
	
	public static String earlySetString; // Stores last error for use by front end.
	
	/** 
	 * Set of enum values for the list of instruments Cloud Composer supports.
	 * @author James Vaughan
	 */
	public enum SequenceInst 
	{
		PIANO(0,0), GUITAR(1,30), DRUM(2,114), TRUMPET(3,56), VIOLIN(4,40);
		
		public final int value;
		public final int instrument;
		SequenceInst(int value, int instrument) {
			this.value = value;
			this.instrument = instrument;
		}
		
	}
	
	/** 
	 * Constructs a cloudMidiPlayer with the default BPM.
	 * Sets the earlySetString error message if an exception is caught.
	 */
	public CloudMidiPlayer() 
	{
		earlySetString = "";
		try {
			loadMidiSystem();
			noteSequences = new Sequence[getInstruments().length][SCALE.length * OCTAVES];
			setTempo(DEFAULTBPM);
		} catch (Exception e) {
			earlySetString = e.getMessage();
		}
	}
	
	/** 
	 * Gets a list of the instruments.
	 * @return An array of SequenceInst enums.
	 */
	public SequenceInst[] getInstruments() 
	{
		return SequenceInst.values();
	}
	
	/** 
	 * Sets the tempo of the song using the provided BPM. 
	 * @param bpm, what the beats per minute of the song should be set to
	 */
	public void setTempo(float bpm)
	{
		guaranteeTempo(bpm);
		generateNotes();
	}
	
	private void guaranteeTempo(float bpm)
	{
		this.bpm = bpm;
		seq.setTempoInBPM(bpm);
	}
	
	/**
	 * Returns the tempo of the song using the provided BPM.
	 * @return A float that represents the current BPM.
	 */
	public float getTempo() {
		return bpm;
	}
	
	/** 
	 * Plays the sequence previously loaded from the provided position.
	 * Sets the earlySetString error message if an exception is caught.
	 */
	public void play()
	{
		pause();
		guaranteeTempo(bpm);
		seq.start();
	}
	
	/** Plays a note using the provided instrument at the given pitch. 
	 * Sets the earlySetString error message if an exception is caught.
	 * @param inst, the instrument to be played
	 * @param pitch, the pitch of the note to play
	 */
	public void playNote(SequenceInst inst, int pitch) 
	{
		pause();
		try {
			seq.setSequence(noteSequences[inst.value][pitch]);
			guaranteeTempo(bpm);
			seq.setTickPosition(0);
			seq.start();
		} catch (InvalidMidiDataException e) {
			earlySetString = e.getMessage();
		}
	}
	
	/** Sets the song to the provided sequence
	 * Sets the earlySetString error message if an exception is caught.
	 * @param s, the sequence to load as the song
	 */
	public void setSequence(Sequence s)
	{
		song = s;
		try {
			seq.setSequence(song);
		} catch (InvalidMidiDataException e) {
			earlySetString = e.getMessage();
		}
	}
	
	/**
	 * @return The Sequence for the composition. 
	 */
	public Sequence getSequence() {
		return song;
	}
	
	/**
	 *  Pauses the song.
	 */
	public void pause() 
	{
		seq.stop();
	}
	
	/**
	 *  Stops the song and returns to the beginning.
	 */
	public void stop() 
	{
		seq.stop();
		seq.setTickPosition(0);
	}
	
	/**
	 * @return Whether or not Midi is being played.
	 */
	public boolean isPlaying() 
	{
		return seq.isRunning();
	}
	
	/**
	 *  Sets the current column of the song.
	 * @param column, the location in the song to start playing from
	 */
	public void setPlayTime(int column) 
	{
		seq.setTickPosition((long) column);
	}
	
	/** 
	 * @return The column of the playback bar.
	 */
	public int playbackBarColumn() 
	{
		return (int) seq.getTickPosition();
	}
	
	/** 
	 * Writes the currently composed Midi file to the indicated file on the FTP server.
	 *  @param location, the String of the place to write the file.
	 */
	public void writeToFile(String location) 
	{
		SimpleFTPClient s = new SimpleFTPClient();
		s.setHost("ftp.publicstaticdroid.com");
		s.setUser("cc_guest%40publicstaticdroid.com");
		s.setPassword("compose");
		s.setRemoteFile(location);
		s.connect();
		
		setSilentEndNote();
		s.uploadSequence(song, location);
		killSilentEndNote();
	}
	
	/** 
	 * Adds a note to the sequence provided with the provided details.
	 * startPos and stopPos are in terms of the column locations,
	 * and pitch is also in terms of the row location of the note.
	 * This is done here to enforce a specific format related to our Midi Player.
	 *  Sets the earlySetString error message if an exception is caught.
	 * @param s, the Sequence to add the note to
	 * @param inst, the instrument to play with the indicated note
	 * @param pitch, the pitch of the note to play
	 * @param startPos, the first column the note plays
	 * @param stopPos, the last column the note plays
	 */
	public static void addNote(Sequence s, SequenceInst inst, 
						 int pitch, int startPos, int stopPos) 
	{
		Track t = s.getTracks()[0];
		int realPitch = pitch / SCALE.length * 12 + SCALE[pitch % SCALE.length];

		try {
			ShortMessage m = new ShortMessage();
			m.setMessage(ShortMessage.NOTE_ON, inst.value, realPitch, 100);
			t.add(new MidiEvent(m, startPos));
			
			ShortMessage m2 = new ShortMessage();
			m2.setMessage(ShortMessage.NOTE_OFF, inst.value, realPitch, 100);
			t.add(new MidiEvent(m2, stopPos));
		} catch (InvalidMidiDataException e) {
			earlySetString = e.getMessage();
		}
		
	}
	
	/** 
	 * Returns a basic Sequence with the instruments defined for use
	 * with converting a NoteGrid into a fully composed song. 
	 * Sets the earlySetString error message if an exception is caught.
	 * @return A silent Sequence with the instruments predefined for each channel.
	 */
	public static Sequence basicSequence() 
	{
		Sequence s = null;
		try {
			s = new Sequence(Sequence.PPQ, (int) TICKSPERFRAME);
			s.createTrack();
			for (SequenceInst inst : SequenceInst.values())
				setInstrument(s, inst);
		} catch (InvalidMidiDataException e) {
			earlySetString = e.getMessage();
		}
		
		return s;
	}
	
	/*public int getLastColumn()
	*{
	*	return (int) song.getTracks()[0].ticks();
	*}  This should not be used.*/
	
	
// PRIVATE METHODS
	
	/** 
	 * Sets an Instrument's channel in a Sequence to use the proper instrument.
	 *  Sets the earlySetString error message if an exception is caught.
	 * @param s, the Sequence to set the note for
	 * @param inst, the instrument to set the note to play
	 */
	private static void setInstrument(Sequence s, SequenceInst inst)
	{
		try {
			ShortMessage m = new ShortMessage();
			m.setMessage(ShortMessage.PROGRAM_CHANGE, inst.value, inst.instrument, 0);
			s.getTracks()[0].add(new MidiEvent(m, 0));
		} catch (InvalidMidiDataException e) {
			earlySetString = e.getMessage();
		}
	}
	
	private void setSilentEndNote() {
		try {
			//ShortMessage m = new ShortMessage();
			//m.setMessage(ShortMessage.NOTE_ON, SequenceInst.values().length, 0, 0);
			//song.getTracks()[0].add(new MidiEvent(m, song.getTickLength()));
			ShortMessage m2 = new ShortMessage();
			m2.setMessage(ShortMessage.NOTE_OFF, SequenceInst.values().length, 0, 0);
			endSong = new MidiEvent(m2, song.getTickLength() + 2);
			song.getTracks()[0].add(endSong);
		} catch (InvalidMidiDataException e) {
			earlySetString = e.getMessage();
		}
	}
	
	private void killSilentEndNote() {
		song.getTracks()[0].remove(endSong);
		endSong = null;
	}
	
	/**
	 * Prepares the Midi System for use. 
	 * Sets the earlySetString error message if an exception is caught.
	 */
	private void loadMidiSystem()
	{
		try {
			seq = MidiSystem.getSequencer();
			synth = MidiSystem.getSynthesizer();
			
			if (seq == null) {
				earlySetString = "Sequencer device not supported.";
				return;
			} else if (synth == null) {
				earlySetString = "Synthesizer device not supported.";
				return;
			} else {
			    // Acquire resources and make operational.
			    seq.open();
			    synth.open();
			}
		} catch (MidiUnavailableException e) {
			earlySetString = e.getMessage();
		}
		
	}
	
	/**
	 *  Generates the Sequences required for single note playback.
	 *  Sets the earlySetString error message if an exception is caught, then
	 *  terminates the loop early.
	 */
	private void generateNotes() 
	{
		for (SequenceInst inst : SequenceInst.values()) {
			for (int pitch = 0; pitch < SCALE.length * OCTAVES; pitch++) {
				Sequence s = basicSequence();
				addNote(s, inst, pitch, 0, (int) TICKSPERFRAME);
				noteSequences[inst.value][pitch] = s;
			}
		}
		
	}
}
