
//package CloudComposerGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

/**
 * This class is for translating notes of song as sequence data,
 * saving them to sequence, and generating it.
 */
public class SongSequence {
	private int instruments;
	
	private NoteGrid grid;		// note grid that contains all the notes
	private Sequence sequence;  // sequence of song to be generated
	private int ticksPerFrame;
		
	/**
	 * Constructs a SongSequence object
	 * 
	 * @param grid, NoteGrid object that contains all the notes
	 * @requires grid != null
	 * @throws InvalidMidiDataException if it cannot create a sequence object
	 */
	public SongSequence(NoteGrid grid, int ticksPerFrame, int instruments) throws InvalidMidiDataException {
		this.grid = grid;
		this.ticksPerFrame = ticksPerFrame;
		this.instruments = instruments;
	}
	
	/**
	 * All the notes information from the note grid will be added to sequence
	 * and return it
	 * 
	 * @return Sequence of song 
	 * @throws InvalidMidiDataException when it fails to add sequence data
	 */
	public Sequence getSequence() throws InvalidMidiDataException {
		sequence = new Sequence(Sequence.SMPTE_30, ticksPerFrame);
		for (int i = 0; i < instruments; i++)
			sequence.createTrack();
		List<Integer> columns = grid.getColumns();
		for (int column : columns) {
			Map<Integer, ArrayList<Note>> pitches = grid.getPitches(column);
			for (int pitch : pitches.keySet()) {
				ArrayList<Note> notes = pitches.get(pitch);
				for (Note n : notes) {
					if (n.instrument == 0)
						CloudMidiPlayer.addNote(sequence, CloudMidiPlayer.SequenceInst.PIANO, pitch, column, column + n.length);
					else if (n.instrument == 1)
						CloudMidiPlayer.addNote(sequence, CloudMidiPlayer.SequenceInst.GUITAR, pitch, column, column + n.length);
					else if (n.instrument == 2)
						CloudMidiPlayer.addNote(sequence, CloudMidiPlayer.SequenceInst.DRUM, pitch, column, column + n.length);
					else if (n.instrument == 3)
						CloudMidiPlayer.addNote(sequence, CloudMidiPlayer.SequenceInst.TRUMPET, pitch, column, column + n.length);
					else
						CloudMidiPlayer.addNote(sequence, CloudMidiPlayer.SequenceInst.VIOLIN, pitch, column, column + n.length);					
				}
			}
		}
		return sequence;
	}
}