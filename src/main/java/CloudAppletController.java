// CloudAppletController.java
// This file is the applet used to interact between the UI,
// the Java model of the grid, the NoteGrid to MIDI Sequence converter
// and the Midi Player itself.

//package CloudComposerGroup;

import java.applet.Applet;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;


public class CloudAppletController extends Applet { //implements ActionListener {
	private CloudMidiPlayer player;
	//private SongSequence sequencer;
	//private NoteGrid grid;
	private boolean changed;
	
	// Initializes the Applet and its components.
	public void init() {
		try {
//			grid = new NoteGrid();
			player = new CloudMidiPlayer();
//			sequencer = new SongSequence(grid, player.getTicksPerFrame(), player.getInstruments().length);
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
	
	private void addRemoveHelper(int[] noteData, boolean add) {
		changed = true;
		int length = noteData[3] - noteData[2] + 1;
	//	for (int i = 0; i < length; i++)
	//		if (add)
	//			grid.add(new Note(1, noteData[0], noteData[1]), noteData[2] + i);	
	//		else
	//			grid.remove(new Note(1, noteData[0], noteData[1]), noteData[2] + i);
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
	//		Sequence s = sequencer.getSequence();
//			player.playSong(s);
		} else
		{
			player.play();
		}
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
	public void download(String location) 
	{
		
	}
	
	// Sets the current position of the song in the player.
	public void setSongPosition(float percent) 
	{
		player.setPlayTime(percent);
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
	
	
	
	/*@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}*/

}
