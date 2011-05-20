package CloudComposerGroup.CloudComposer;


import java.applet.Applet;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

//CloudAppletController.java
//This file is the applet used to interact between the UI,
//the Java model of the grid, the NoteGrid to MIDI Sequence converter
//and the Midi Player itself.
public class CloudAppletController extends Applet { 

	private static final long serialVersionUID = 1L;
	public CloudMidiPlayer player;
	private SongSequence sequencer;
	public NoteGrid grid;
	private boolean changed;
	
	// Initializes the Applet and its components.
	public void init() {
		try {
			grid = new NoteGrid();
			player = new CloudMidiPlayer();
			sequencer = new SongSequence(grid);
			changed = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Adds the provided data as a note on the NoteGrid.
	public void addNote(int[] noteData)
	{
		addRemoveHelper(noteData, true);
	}
	
	// Removes the provided data as a note from the NoteGrid.
	public void removeNote(int[] noteData)
	{
		addRemoveHelper(noteData, false);
	}
	
	// noteData format: [instrument, pitch, startPos, stopPos]
	// Adds or removes a note given the data for the note.
	private void addRemoveHelper(int[] noteData, boolean add) {
		changed = true;
		int length = noteData[3] - noteData[2] + 1;    // for example, starting column is 2 and ending column is 5. 5 - 2 + 1 = 4: 2, 3, 4, 5 
		
		if (add)
			grid.add(new Note(length, noteData[0], noteData[1]), noteData[2]);	
		else
			grid.remove(new Note(length, noteData[0], noteData[1]), noteData[2]);
	}
	
	// Returns a list of instruments.
	public String[] getInstruments() {
		String[] list = new String[5];
		for (int i = 0; i < 5; i++) {
			list[i] = player.getInstruments()[i].name();
		}
		return list;
	}
	
	// Checks if the song has changed since it last played.
	// If so, it remakes the song and then plays.  Otherwise, it plays
	// what it currently has loaded.
	// Throws an error if the new Sequence data is invalid.
	public void play() throws InvalidMidiDataException
	{
		updateSequence();
		player.play();
	}
	
	// Plays the note indicated by the instrument and the pitch.
	// Throws an error if somehow the Midi data is invalid.
	public void playNote(int instrument, int pitch) throws InvalidMidiDataException 
	{
		player.playNote(player.getInstruments()[instrument], pitch);
	}
	
	// Stops the song, moving its playback position to the beginning.
	public void stop() 
	{
		player.stop();
	}
	
	// Stops a song, but doesn't change the playback position.
	public void pause()
	{
		player.pause();
	}
	
	// Updates the sequence and then tells the Midiplayer to write the Midi file.
	// Throws an exception if our newly composed Sequence is invalid, or
	// if there is a writing exception.
	public void download(String location) throws InvalidMidiDataException, IOException 
	{
		updateSequence();
		player.writeToFile(location);
	}
	
	// Sets the current position of the song in the player.
	public void setSongPosition(int column) 
	{
		player.setPlayTime(column);
	}
	
	// Gets the current song position.
	public int currentSongPosition()
	{
		return player.playbackBarColumn();
	}
	
	// Sets the tempo to the provided BPM.
	// Throws an exception if the BPM is somehow invalid.
	public void setTempo(float bpm) throws InvalidMidiDataException
	{
		player.setTempo(bpm);
	}
	
	// Returns the current Midi song data, regardless of if it has been modified.
	public Sequence getSongSequence() {
		return player.getSequence();
	}
	
	// Determines if a song is currently being played.
	//public boolean isPlaying() {
	//	return player.isPlaying();
	//} Shouldn't be needed.
	
	// Updates the Midiplayer with the actual song data.
	// Throws an exception if the Midi data is invalid.
	private void updateSequence() throws InvalidMidiDataException {
		if (changed)
		{
			changed = false;
			Sequence s = sequencer.getSequence();
			player.setSequence(s);
		}
	}

}
