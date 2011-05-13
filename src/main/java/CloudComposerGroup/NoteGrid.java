

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is for creating a note grid to contain
 * all the notes of a song
 */
public class NoteGrid {
	/** note grid that contains instrument notes **/
	private Map<Integer, ArrayList<Note>> grid;
	/** the number of instrument notes on note grid **/
	private int size;
	
	/**
	 * Constructs a NoteGrid object
	 */
	public NoteGrid() {
		grid = new HashMap<Integer, ArrayList<Note>>();
		size = 0;
	}	
	
	/**
	 * Adds a note in a proper position of note grid
	 * 
	 * @param note, note object
	 * @param column, integer that represents a column of note grid
	 * @requires note != null && column >= 0
	 * @modifies this
	 */
	public void add(Note note, int column) {
		List<Note> columnNotes = grid.get(column);
		if (columnNotes == null) {
			columnNotes = new ArrayList<Note>();
			columnNotes.add(note);
			grid.put(column, (ArrayList<Note>) columnNotes);
		} else {
			if (!columnNotes.contains(note))
				columnNotes.add(note);
			else
				return;				
		}
		size++;
	}
	
	/**
	 * Removes a note at a particular of column and pitch
	 * 
	 * @param note, Note object
	 * @param column, integer that represents a column of the note grid
	 * @requires note != null && column >= 0
	 * @modifies this
	 */
	public void remove(Note note, int column) {
		List<Note> columnNotes = grid.get(column);
		columnNotes.remove(note);
		size--;
	}
	
	/**
	 * Returns true if it removes all the notes of a particular column.
	 * Otherwise, false.
	 * 
	 * @param column, integer that represents a column of the note grid
	 * @requires column >= 0
	 * @modifies this
	 */
	public boolean removeAllNotes(int column) {
		size -= grid.get(column).size();
		return grid.remove(column) != null;
	}
	
	/**
	 * Returns a copy of a list of notes of given column
	 * 
	 * @param column, index of columns
	 * @requires column >= 0
	 * @return list of notes of column
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Note> getNotes(int column) {
		return new ArrayList(grid.get(column));
	}
	
	/**
	 * Returns a list of integers that represents the columns of the note grid
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getColumns() {
		List<Integer> columns = (List<Integer>) grid.keySet();
		Collections.sort(columns);
		return columns;
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
			for (Note n : grid.get(column))
				return n.equals(note);
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
		size = 0;
	}
}