import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 
 */
public class NoteGrid {
	/** note grid that contains instrument notes **/
	private List<HashMap<Integer, ArrayList<Note>>> grid;
	/** the number of instrument notes on note grid **/
	private int size;
	
	/**
	 * Constructs a NoteGrid object
	 */
	public NoteGrid() {
		grid = new ArrayList<HashMap<Integer, ArrayList<Note>>>();
		size = 0;
	}
	
	/**
	 * Adds note onto NoteGrid
	 * 
	 * @param column, integer that represents a column of NoteGrid
	 * @param length, integer that represents the length of an instrument
	 * @param instrument, integer that represents an instrument
	 * @param pitch, integer that represents the pitch of a note
	 * @requires column, length, instrument and pitch >= 0
	 * @modifies this
	 * @effects adds a note onto the NoteGrid. If the same node already exists,
	 * 			node will not be added at all.
	 */
	public void add(Note note, int column) {
		Map<Integer, ArrayList<Note>> columns = grid.get(column);
		if (columns == null) {
			columns = new HashMap<Integer, ArrayList<Note>>();
			List<Note> notes = new ArrayList<Note>();
			if (notes.add(note)) {
				columns.put(note.pitch, (ArrayList<Note>) notes);
				grid.add(column, (HashMap<Integer, ArrayList<Note>>) columns);
			}
		} else {
			List<Note> notes = columns.get(note.pitch);
			if (notes == null) {
				notes = new ArrayList<Note>();
				if (notes.add(note))
					columns.put(note.pitch, (ArrayList<Note>) notes);
			} else
				if (!notes.contains(note))
					try {
						notes.add(note);
					} catch(Exception e) {
						System.err.println("Note is not added");
					}
		}
		size++;
	}
	
	/**
	 * 
	 * @param note
	 * @param column
	 */
	public void remove(Note note, int column) {
		Map<Integer, ArrayList<Note>> columns = grid.get(column);
		List<Note> notes = columns.get(note.pitch);
		for (Note n : notes) {
			if (n.equals(note))
				if (notes.remove(n))
					size--;
		}
	}

	/**
	 * Returns a copy of a list of notes of given column and pitch
	 * 
	 * @param column, index of columns
	 * @param pitch, integer that represents pitch of note
	 * @requires column & pitch >= 0
	 * @return a list of notes of given column and pitch
	 */
	public List<Note> get(int column, int pitch) {
		return new ArrayList<Note>(grid.get(column).get(pitch));
	}
	
	/**
	 * Returns true if note already exists.
	 * Otherwise, returns false.
	 * 
	 * @param note, note object
	 * @param column, index of columns
	 * @return true if note exists on note grid. False, otherwise
	 */
	public boolean contains(Note note, int column) {
		if (grid.get(column) != null)
			for (Note n : grid.get(column).get(note.pitch))
				return (n.instrument == note.instrument && n.length == note.length);
		return false;
	}	

	/**
	 * @return the number of instrument notes on note grid
	 */
	public int size() {
		return size;
	}
		
	/**
	 * clears note grid
	 */
	public void clear() {
		grid.clear();
	}
}
