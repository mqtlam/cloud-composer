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
	public void add(int column, Note note) {
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
					if (notes.add(note)) {}
		}
		size++;
	}
	
	public void remove(int column, Note note) {
		Map<Integer, ArrayList<Note>> columns = grid.get(column);
		List<Note> notes = columns.get(note.pitch);
		notes.remove(note);
		size--;
	}

	public List<Note> get(int column, int pitch) {
		return grid.get(column).get(pitch);
	}
	
	public boolean contains(int column, Note note) {
		if (grid.get(column) != null)
			for (Note n : grid.get(column).get(note.pitch))
				return (n.instrument == note.instrument && n.length == note.length);
		return false;
	}	

	public int size() {
		return size;
	}
		
	public void clear() {
		grid.clear();
	}

}
