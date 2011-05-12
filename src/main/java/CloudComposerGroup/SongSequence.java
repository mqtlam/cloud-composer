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
	private NoteGrid grid;		// note grid that contains all the notes
	private Sequence sequence;  // sequence of song to be generated
		
	/**
	 * Constructs a SongSequence object
	 * 
	 * @param grid, NoteGrid object that contains all the notes
	 * @requires grid != null
	 * @throws InvalidMidiDataException if it cannot create a sequence object
	 */
	public SongSequence(NoteGrid grid) throws InvalidMidiDataException {
		this.grid = grid;
		sequence = new Sequence(Sequence.SMPTE_30, 10);
	}
	
	/**
	 * All the notes information from the note grid will be added to sequence
	 * and return it
	 * 
	 * @return Sequence of song 
	 * @throws InvalidMidiDataException when it fails to add a sequence data
	 */
	public Sequence getSequence() throws InvalidMidiDataException {
		List<Integer> columns = grid.getColumns();
		for (int column : columns) {
			Map<Integer, ArrayList<Note>> pitches = grid.getPitches(column);
			for (int pitch : pitches.keySet()) {
				ArrayList<Note> notes = pitches.get(pitch);
				for (Note n : notes) {
					if (n.instrument == 0)
						cloudMidiPlayer.addNote(sequence, cloudMidiPlayer.SequenceInst.PIANO, pitch, column, column + n.length);
					else if (n.instrument == 1)
						cloudMidiPlayer.addNote(sequence, cloudMidiPlayer.SequenceInst.GUITAR, pitch, column, column + n.length);
					else if (n.instrument == 2)
						cloudMidiPlayer.addNote(sequence, cloudMidiPlayer.SequenceInst.DRUM, pitch, column, column + n.length);
					else if (n.instrument == 3)
						cloudMidiPlayer.addNote(sequence, cloudMidiPlayer.SequenceInst.TRUMPET, pitch, column, column + n.length);
					else
						cloudMidiPlayer.addNote(sequence, cloudMidiPlayer.SequenceInst.VIOLIN, pitch, column, column + n.length);					
				}
			}
		}
		return sequence;
	}
}