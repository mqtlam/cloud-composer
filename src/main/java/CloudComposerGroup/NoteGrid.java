import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 
 */
public class NoteGrid {
	/** the grid that contains instrument notes **/
	private List<HashMap<Integer, ArrayList<Note>>> grid;
	
	/**
	 * Constructs a NoteGrid object
	 */
	public NoteGrid() {
		grid = new ArrayList<HashMap<Integer, ArrayList<Note>>>();
	}
	
	/**
	 * Adds note onto NoteGrid
	 * 
	 * @param column, integer that represents a column of NoteGrid
	 * @param length, integer that represents the length of an instrument
	 * @param instrument, integer that represents an instrument
	 * @param pitch, integer that represents the pitch of a note
	 * @requires column, length, instrument and pitch >= 0
	 * @effects adds a note onto the NoteGrid
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
			boolean existed = true;
			List<Note> notes = columns.get(note.pitch);
			if (notes == null) {
				notes = new ArrayList<Note>();
				existed = false;
			}
			if (notes.add(note))
				if (!existed)
					columns.put(note.pitch, (ArrayList<Note>) notes);
		}
	}
	
	public void remove(int column, int length, int instrument, int pitch) {
		 
	}
	
	
//	public boolean columnExists(int column) {
//		return grid.get(column) != null;
//	}
//	
//	public boolean noteExists(int column, Note note) {
//		if (columnExists(column))
//			for (Note n : grid.get(column).get(note.pitch))
//				return (n.instrument == note.instrument && n.length == note.length);
//		return false;
//	}	
}
