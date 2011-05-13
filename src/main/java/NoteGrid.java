
package CloudComposerGroup;

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
	 * Adds a note in a proper position of note grid
	 * 
	 * @param note, note object
	 * @param column, integer that represents a column of note grid
	 * @requires note != null && column >= 0
	 * @modifies this
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
			} else
				if (!row.contains(note)) {
					row.add(note);
					columns.put(note.pitch, row);
				} else
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
		Map<Integer, ArrayList<Note>> columns = grid.get(column);
		ArrayList<Note> row = columns.get(note.pitch);
		row.remove(note);
		size--;
	}
	
	/**
	 * true if it removes all the notes of a particular column and pitch.
	 * Otherwise, false.
	 * 
	 * @param column, integer that represents a column of the note grid
	 * @param pitch, integer that represents a pitch of a note
	 * @requires column && pitch >= 0
	 * @modifies this
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
	public List<Note> getNotes(int column, int pitch) {
		return new ArrayList<Note>(grid.get(column).get(pitch));
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
	 * Returns HashMap object to provide the notes of a different pitch of a column
	 * 
	 * @param column, integer that represents a column of note grid
	 * @requires column >= 0
	 * @return HashMap object that contains the notes of a different pitch corresponding to a particular column
	 */
	public HashMap<Integer, ArrayList<Note>> getPitches(int column) {
		return grid.get(column);
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