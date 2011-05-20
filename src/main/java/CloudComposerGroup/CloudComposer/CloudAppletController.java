package CloudComposerGroup.CloudComposer;


import java.applet.Applet;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

//CloudAppletController.java
//This file is the applet used to interact between the UI,
//the Java model of the grid, the NoteGrid to MIDI Sequence converter
//and the Midi Player itself.
public class CloudAppletController extends Applet { //implements ActionListener {

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
	
	// TODO: Add note to NoteGrid
	public void addNote(int[] noteData)
	{
		addRemoveHelper(noteData, true);
	}
	
	// TODO: Remove note from NoteGrid
	public void removeNote(int[] noteData)
	{
		addRemoveHelper(noteData, false);
	}
	
	// noteData format: [instrument, pitch, startPos, stopPos]
	private void addRemoveHelper(int[] noteData, boolean add) {
		changed = true;
		int length = noteData[3] - noteData[2] + 1;    // for example, starting column is 2 and ending column is 5. 5 - 2 + 1 = 4: 2, 3, 4, 5 
		
		
		//for (int i = 0; i < length; i++)             //  Note: The reason I used a loop is that I wanted to put the same note except length
		if (add) 								       //  in the columns for the extension of a note. -June
			grid.add(new Note(length, noteData[0], noteData[1]), noteData[2]);	
		else
			grid.remove(new Note(length, noteData[0], noteData[1]), noteData[2]);
	}
	
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
	public void play() throws InvalidMidiDataException
	{
		if (changed)
		{
			changed = false;
			Sequence s = sequencer.getSequence();
			player.setSequence(s);
		}
			player.play();
	}
	
	// Plays the note indicated by the instrument and the pitch.
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
	
	// TODO: Download MIDI file
	public void download(String location) throws InvalidMidiDataException, IOException 
	{
		if (changed)
		{
			changed = false;
			Sequence s = sequencer.getSequence();
			player.setSequence(s);
		}
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
	public void setTempo(float bpm) throws InvalidMidiDataException
	{
		player.setTempo(bpm);
	}
	
	public Sequence getSongSequence() {
		return player.getSequence();
	}
	
	public boolean isPlaying() {
		return player.isPlaying();
	}
	
	
	/*@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}*/

}
