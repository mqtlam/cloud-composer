package CloudComposerGroup.CloudComposer;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import java.applet.Applet;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * CloudAppletController.java
 *
 * This file is the applet used to interact between the UI,
 * the Java model of the grid, the NoteGrid to MIDI Sequence converter
 * and the Midi Player itself.
 */
public class CloudAppletController extends Applet { 

	private static final long serialVersionUID = 1L;
	public CloudMidiPlayer player;
	private SongSequence sequencer;
	public NoteGrid grid;
	private boolean changed;
	public int songPosition;

	/** 
	 * Initializes the Applet and its components.
	 */
	public void init() {
		try {
			grid = new NoteGrid();
			player = new CloudMidiPlayer();
			sequencer = new SongSequence(grid);
			changed = false;
			songPosition = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Adds the provided data as a note on the NoteGrid.
	 * @param noteData, format: {instrument, pitch, startColumn, endColumn}
	 */
	public void addNote(int[] noteData)
	{
		addRemoveHelper(noteData, true);
	}

	/** 
	 * Removes the provided data as a note from the NoteGrid.
	 * @param noteData, format: {instrument, pitch, startColumn, endColumn}
	 */
	public void removeNote(int[] noteData)
	{
		addRemoveHelper(noteData, false);
	}

	/** 
	 * noteData format: [instrument, pitch, startPos, stopPos]	 
	 * Adds or removes a note given the data for the note.
	 * @param noteData, format: {instrument, pitch, startColumn, endColumn}
	 * @param add, determines whether we are adding a note or removing one
	 */
	private void addRemoveHelper(int[] noteData, boolean add) {
		changed = true;
		int length = noteData[3] - noteData[2] + 1; // for example, starting column is 2 and ending column is 5. 5 - 2 + 1 = 4: 2, 3, 4, 5 

		if (add)
			grid.add(new Note(length, noteData[0], noteData[1]), noteData[2]);	
		else
			grid.remove(new Note(length, noteData[0], noteData[1]), noteData[2]);
	}

	/** 
	 * Returns a list of instruments.
	 * @return String array of the instrument names.
	 */
	public String[] getInstruments() {
		String[] list = new String[5];
		for (int i = 0; i < 5; i++) {
			list[i] = player.getInstruments()[i].name();
		}
		return list;
	}

	/** 
	 * Checks if the song has changed since it last played.
	 * If so, it remakes the song and then plays.  Otherwise, it plays
	 * what it currently has loaded.
	 * Throws an error if the new Sequence data is invalid.
	 * @throws InvalidMidiDataException if the sequence is not constructed properly
	 */
	public void play() throws InvalidMidiDataException
	{
		updateSequence();
		player.play();
	}

	/** 
	 * Plays the note indicated by the instrument and the pitch.
	 * Throws an error if somehow the Midi data is invalid.
	 * @param instrument, the integer representation of an instrument
	 * @param pitch, maps the row on which the note exists to a pitch
	 * @throws InvalidMidiDataException if the Note is not generated properly
	 */
	public void playNote(int instrument, int pitch) throws InvalidMidiDataException 
	{
		player.playNote(player.getInstruments()[instrument], pitch);
	}

	/** 
	 * Stops the song, moving its playback position to the beginning.
	 */
	public void stop() 
	{
		player.stop();
	}

	/** 
	 * Stops a song, but doesn't change the playback position.
	 */
	public void pause()
	{
		player.pause();
	}

	/** 
	 * Updates the sequence and then tells the Midiplayer to write the Midi file.
	 * Throws an exception if our newly composed Sequence is invalid, or
	 * if there is a writing exception.
	 * @param location, a string of the file location to write to
	 * @throws InvalidMidiDataException if the composition translates to invalid Midi data
	 * @throws IOException if the file cannot be opened
	 * @throws PrivilegedActionException 
	 */ 
	public void download() throws InvalidMidiDataException, IOException, PrivilegedActionException 
	{
		updateSequence();
		Frame f = new MyFrame("MIDI File");

		// create instance of FileDialog
		FileDialog fd = new FileDialog(f, "Save Midi File", FileDialog.SAVE);
		// make FileDialog visible
		fd.setVisible(true);
		if (fd.getFile() != null) {
			final String filename = fd.getDirectory() + fd.getFile();

			AccessController.doPrivileged(new PrivilegedExceptionAction() {
				public Object run() {
					try {
						player.writeToFile(filename);
					} catch (Exception e) {

					}
					return null;
				}
			});
		}
		changed = true; // revert changes from download playback fixing
		updateSequence();
	}




	//Create a subclass of Frame
	private class MyFrame extends Frame {
		private static final long serialVersionUID = 1L;

		MyFrame(String title) {
			super(title);
			// create an object to handle window events
			MyWindowAdapter adapter = new MyWindowAdapter(this);
			// register it to receive those events
			addWindowListener(adapter);
		}
	}

	private class MyWindowAdapter extends WindowAdapter {
		MyFrame myFrame;

		public MyWindowAdapter(MyFrame myFrame) {
			this.myFrame = myFrame;
		}

		public void windowClosing(WindowEvent we) {
			myFrame.setVisible(false);
		}
	}


	/** 
	 * Sets the current position of the song in the player.
	 * @param column, the column to move the Midi player to
	 * @throws InvalidMidiDataException 
	 */
	public void setSongPosition(int column) throws InvalidMidiDataException 
	{
		//updateSequence();
		//player.setPlayTime(column);
		if (!changed) 
		{
			player.setPlayTime(column);
		}
		songPosition = column;


	}

	/** 
	 * Gets the current song position.
	 * @return Current column of the Midi Player.
	 */
	public int currentSongPosition()
	{
		return player.playbackBarColumn();
	}

	/** 
	 * Sets the tempo to the provided BPM.
	 * @throws InvalidMidiDataException if the BPM causes an invalid song.
	 */
	public void setTempo(float bpm) throws InvalidMidiDataException
	{
		player.setTempo(bpm);
	}

	/**
	 * Returns the tempo to the provided BPM.
	 */
	public float getTempo() 
	{
		return player.getTempo();
	}

	/**
	 * Gets the currently loaded song.
	 * @return The Sequence loaded in the player.
	 */
	public Sequence getSongSequence() {
		return player.getSequence();
	}

	// Determines if a song is currently being played.
	//public boolean isPlaying() {
	//	return player.isPlaying();
	//} Shouldn't be needed.

	/** 
	 * Updates the Midiplayer with the actual song data.
	 * @throws InvalidMidiDataException if the Midi data is invalid.
	 */
	public void updateSequence() throws InvalidMidiDataException {
		if (changed)
		{
			changed = false;
			Sequence s = sequencer.getSequence();
			player.setSequence(s);
		}
		player.setPlayTime(songPosition);
	}

}
