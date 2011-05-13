package CloudComposerGroup;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

/**
 * This class is for translating notes of song as sequence data,
 * saving them to sequence, and generating it.
 */
public class SongSequence {
	private int instruments;	// the number of instruments
	private NoteGrid grid;		// note grid that contains all the notes
	private Sequence sequence;  // sequence of song to be generated
	private int ticksPerFrame;  // duration of sequence
		
	/**
	 * Constructs a SongSequence object
	 * 
	 * @param grid, NoteGrid object that contains all the notes
	 * @param ticksPerFrame, integer that represents duration of sequence
	 * @param instruments, integer that represents the number of instruments
	 * @requires grid != null && ticksPerFrame > 0 && instruments > 0
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
			List<Note> columnNotes = grid.getNotes(column);
			for (Note n : columnNotes) {
				if (n.instrument == 0)
					CloudMidiPlayer.addNote(sequence, CloudMidiPlayer.SequenceInst.PIANO, n.pitch, column, column + n.length);
				else if (n.instrument == 1)
					CloudMidiPlayer.addNote(sequence, CloudMidiPlayer.SequenceInst.GUITAR, n.pitch, column, column + n.length);
				else if (n.instrument == 2)
					CloudMidiPlayer.addNote(sequence, CloudMidiPlayer.SequenceInst.DRUM, n.pitch, column, column + n.length);
				else if (n.instrument == 3)
					CloudMidiPlayer.addNote(sequence, CloudMidiPlayer.SequenceInst.TRUMPET, n.pitch, column, column + n.length);
				else
					CloudMidiPlayer.addNote(sequence, CloudMidiPlayer.SequenceInst.VIOLIN, n.pitch, column, column + n.length);					
			}
		}
		return sequence;
	}
}