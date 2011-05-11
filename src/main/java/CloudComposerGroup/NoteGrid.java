import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 */
public class NoteGrid {
	/** note grid that contains instrument notes **/
	private Map<Integer, HashMap<Integer, ArrayList<Note>>> grid;
	/** the number of instrument notes on note grid **/
	private int size;
	
	/**
	 * Constructs a NoteGrid object
	 */
	public NoteGrid() {
		grid = new HashMap<Integer, HashMap<Integer, ArrayList<Note>>>();
		size = 0;
	}
	
	/**
	 * 
	 * @param note
	 * @param column
	 */
	public void add(Note note, int column) {
		Map<Integer, ArrayList<Note>> columns = grid.get(column);
		if (columns == null) {
			Map<Integer, ArrayList<Note>> newColumn = new HashMap<Integer, ArrayList<Note>>();
			ArrayList<Note> newRow = new ArrayList<Note>();
			newRow.add(note);
			newColumn.put(note.pitch, newRow);
		} else {
			ArrayList<Note> row = columns.get(columns);
			if (row == null) {
				ArrayList<Note> newRow = new ArrayList<Note>();
				newRow.add(note);
				columns.put(note.pitch, newRow);
			} else {
				if (!row.contains(note)) {
					row.add(note);
					columns.put(note.pitch, row);
				} else
					return;
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
		ArrayList<Note> row = columns.get(note.pitch);
		row.remove(note);
		size--;
	}
	
	/**
	 * 
	 * @param column
	 * @param pitch
	 * @return
	 */
	public boolean removeAllNotes(int column, int pitch) {
		size -= grid.get(column).get(pitch).size();
		return grid.get(column).remove(pitch) != null;
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
