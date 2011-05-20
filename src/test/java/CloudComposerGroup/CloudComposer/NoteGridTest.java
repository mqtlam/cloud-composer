/**
 * Unit test for CloudComposerGroup.Note class!
 */

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;
import CloudComposerGroup.CloudComposer.*;
import java.util.*;

/**
 * This is for testing whether NoteGrid object works correct as it 
 * is supposed to without any bugs.
 */
public class NoteGridTest  {
	
	/** NoteGrid object   **/
	private NoteGrid grid;
	
	/** Note objects of different instruments and pitches **/
	private Note n0;
	private Note n1;
	private Note n2;
	private Note n3;
	private Note n4;
	private Note n5;
	private Note n6;
	private Note n7;
	private Note n8;
	private Note n9;

	/**
	 * sets NoteGrid and Note objects every single time it runs a test
	 */
	@Before
	public void setUp() throws Exception {
		grid = new NoteGrid();
		
		n0 = new Note(1, 0, 0);
		n1 = new Note(1, 1, 1);  //length, instrument, pitch
		n2 = new Note(1, 2, 2);
		n3 = new Note(1, 3, 3);
		n4 = new Note(1, 1, 4);
		n5 = new Note(1, 2, 5);
		n6 = new Note(1, 4, 6);
		n7 = new Note(1, 3, 7);
		n8 = new Note(1, 0, 8);
		n9 = new Note(1, 2, 9);		
	}
	
	/**
	 * Test if note grid adds note objects in a column correctly or not
	 * Test the add & contains functions
	 */
	@Test
	public void testNoteGridAddContains00() {
		grid.add(n0, 0);

		assertTrue(grid.contains(n0, 0));
	}
	
	/**
	 * Test if note grid adds note objects in a column correctly or not
	 * Test the add & contains functions
	 */
	@Test
	public void testNoteGridAddContains01() {
		grid.add(n0, 0);
		grid.add(n1, 1);

		assertTrue(grid.contains(n0, 0));
		assertTrue(grid.contains(n1, 1));
	}
	
	/**
	 * Test if note grid adds note objects in a column correctly or not
	 * Test the add & contains functions
	 */
	@Test
	public void testNoteGridAddContains02() {
		grid.add(n0, 9);

		assertTrue(grid.contains(n0, 9));
	}
	
	/**
	 * Test if note grid adds note objects in a column correctly or not
	 * Test the add & contains functions
	 */
	@Test
	public void testNoteGridAddContains03() {
		grid.add(n0, 9);

		assertTrue(grid.contains(n0, 9));
	}
	
	/**
	 * Test if note grid adds note objects in a column correctly or not
	 * Test the add & contains functions
	 */
	@Test
	public void testNoteGridAddContains04() {
		grid.add(n5, 8);
		grid.add(n6, 9);

		assertTrue(grid.contains(n5, 8));
		assertTrue(grid.contains(n6, 9));
	}


	/**
	 * Test if note grid adds note objects in a column correctly or not
	 * Test the add & contains functions
	 */
	@Test
	public void testNoteGridAddContains05() {
		grid.add(n0, 0);
		grid.add(n1, 1);
		grid.add(n2, 2);
		grid.add(n3, 3);
		grid.add(n4, 4);

		assertTrue(grid.contains(n0, 0));
		assertTrue(grid.contains(n1, 1));
		assertTrue(grid.contains(n2, 2));
		assertTrue(grid.contains(n3, 3));
		assertTrue(grid.contains(n4, 4));
	}

	/**
	 * Test if note grid adds note objects in a column correctly or not
	 * Test the add & contains functions
	 */
	@Test
	public void testNoteGridAddContains06() {
		grid.add(n0, 5);
		grid.add(n1, 6);
		grid.add(n2, 7);
		grid.add(n3, 8);
		grid.add(n4, 9);

		assertTrue(grid.contains(n0, 5));
		assertTrue(grid.contains(n1, 6));
		assertTrue(grid.contains(n2, 7));
		assertTrue(grid.contains(n3, 8));
		assertTrue(grid.contains(n4, 9));
	}
	
	/**
	 * Test if note grid adds note objects in a column correctly or not
	 * Test the add & contains functions
	 */
	@Test
	public void testNoteGridAddContains07() {
		grid.add(n0, 0);
		grid.add(n1, 1);
		grid.add(n2, 2);
		grid.add(n3, 3);
		grid.add(n4, 4);
		grid.add(n5, 5);
		grid.add(n6, 6);
		grid.add(n7, 7);
		grid.add(n8, 8);
		grid.add(n9, 9);

		assertTrue(grid.contains(n0, 0));
		assertTrue(grid.contains(n1, 1));
		assertTrue(grid.contains(n2, 2));
		assertTrue(grid.contains(n3, 3));
		assertTrue(grid.contains(n4, 4));
		assertTrue(grid.contains(n5, 5));
		assertTrue(grid.contains(n6, 6));
		assertTrue(grid.contains(n7, 7));
		assertTrue(grid.contains(n8, 8));
		assertTrue(grid.contains(n9, 9));
	}
		
	/**
	 * Test if note grid removes note objects in a column correctly or not
	 * Test the remove & contains functions
	 */
	@Test
	public void testNoteGridRemove01() {
		grid.add(n0, 1);		
		grid.remove(n0, 1);
		assertFalse(grid.contains(n0, 1));
	}
	
	/**
	 * Test if note grid removes note objects in a column correctly or not
	 * Test the remove & contains functions
	 */
	@Test
	public void testNoteGridRemove02() {
		grid.add(n0, 1);
		grid.add(n1, 1);
		grid.remove(n0, 1);
		grid.remove(n1, 1);
		assertFalse(grid.contains(n0, 1));
		assertFalse(grid.contains(n1, 1));
	}

	/**
	 * Test if note grid removes note objects in a column correctly or not
	 * Test the remove & contains functions
	 */
	@Test
	public void testNoteGridRemove03() {
		grid.add(n0, 2);
		grid.add(n1, 2);
		grid.remove(n0, 2);
		grid.remove(n1, 2);
		assertFalse(grid.contains(n0, 2));
		assertFalse(grid.contains(n1, 2));
	}

	/**
	 * Test if note grid removes note objects in a column correctly or not
	 */
	@Test
	public void testNoteGridRemove04() {
		grid.add(n0, 9);		
		grid.remove(n0, 9);
		assertFalse(grid.contains(n0, 9));
	}
	
	/**
	 * Test if note grid removes note objects in a column correctly or not
	 * Test the remove & contains functions
	 */
	@Test
	public void testNoteGridRemove05() {
		grid.add(n0, 9);
		grid.add(n1, 9);		

		grid.remove(n0, 9);
		grid.remove(n1, 9);
		assertFalse(grid.contains(n0, 9));
	}
	
	/**
	 * Test if note grid removes note objects in a column correctly or not
	 * Test the remove & contains functions
	 */
	@Test
	public void testNoteGridRemove06() {
		grid.add(n0, 9);
		grid.add(n1, 8);		

		grid.remove(n0, 9);
		grid.remove(n1, 8);
		assertFalse(grid.contains(n0, 9));
		assertFalse(grid.contains(n1, 8));
	}
	
	/**
	 * Test if note grid returns a list of the notes of a column correctly or not
	 */
	@Test
	public void testNoteGridGetNotes00() {
		List<Note> actual = grid.getNotes(0);

		assertNull(actual);
	}
	
	/**
	 * Test if note grid returns a list of the notes of a column correctly or not
	 */
	@Test
	public void testNoteGridGetNotes01() {
		grid.add(n0, 0);
		List<Note> actual = grid.getNotes(0);
		List<Note> expected = new ArrayList<Note>();
		expected.add(n0);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if note grid returns a list of the notes of a column correctly or not
	 */
	@Test
	public void testNoteGridGetNotes02() {
		grid.add(n0, 0);
		grid.add(n1, 0);
		List<Note> actual = grid.getNotes(0);
		List<Note> expected = new ArrayList<Note>();
		expected.add(n0);
		expected.add(n1);
		
		assertEquals(expected, actual);
	}

	/**
	 * Test if note grid returns a list of the notes of a column correctly or not
	 */
	@Test
	public void testNoteGridGetNotes03() {
		grid.add(n0, 1);
		grid.add(n1, 1);
		List<Note> actual = grid.getNotes(1);
		List<Note> expected = new ArrayList<Note>();
		expected.add(n0);
		expected.add(n1);
		
		assertEquals(expected, actual);
	}

	/**
	 * Test if note grid returns a list of the notes of a column correctly or not
	 */
	@Test
	public void testNoteGridGetNotes04() {
		grid.add(n0, 2);
		grid.add(n1, 2);
		List<Note> actual = grid.getNotes(2);
		List<Note> expected = new ArrayList<Note>();
		expected.add(n0);
		expected.add(n1);
		
		assertEquals(expected, actual);
	}

	/**
	 * Test if note grid returns a list of the notes of a column correctly or not
	 */
	@Test
	public void testNoteGridGetNotes05() {
		grid.add(n0, 8);
		grid.add(n1, 8);
		List<Note> actual = grid.getNotes(8);
		List<Note> expected = new ArrayList<Note>();
		expected.add(n0);
		expected.add(n1);
		
		assertEquals(expected, actual);
	}

	/**
	 * Test if note grid returns a list of the notes of a column correctly or not
	 */
	@Test
	public void testNoteGridGetNotes06() {
		grid.add(n0, 9);
		grid.add(n1, 9);
		List<Note> actual = grid.getNotes(9);
		List<Note> expected = new ArrayList<Note>();
		expected.add(n0);
		expected.add(n1);
		
		assertEquals(expected, actual);
	}

	/**
	 * Test if note grid returns a list of the notes of a column correctly or not
	 */
	@Test
	public void testNoteGridGetNotes07() {
		grid.add(n0, 0);
		grid.add(n1, 0);
		grid.add(n2, 0);
		grid.add(n3, 0);
		grid.add(n4, 0);
		List<Note> actual = grid.getNotes(0);
		
		List<Note> expected = new ArrayList<Note>();
		expected.add(n0);
		expected.add(n1);
		expected.add(n2);
		expected.add(n3);
		expected.add(n4);
		
		assertEquals(expected, actual);
	}

	/**
	 * Test if note grid returns a list of the notes of a column correctly or not
	 */
	@Test
	public void testNoteGridGetNotes08() {
		grid.add(n5, 0);
		grid.add(n6, 0);
		grid.add(n7, 0);
		grid.add(n8, 0);
		grid.add(n9, 0);
		List<Note> actual = grid.getNotes(0);
		
		List<Note> expected = new ArrayList<Note>();
		expected.add(n5);
		expected.add(n6);
		expected.add(n7);
		expected.add(n8);
		expected.add(n9);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if note grid returns a set of integers that represents each column of grid
	 */
	@Test
	public void testNoteGridGetColumns01() {
		grid.add(n0, 0);
		
		List<Integer> actual = new ArrayList<Integer>(grid.getColumns());
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(0);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if note grid returns a set of integers that represents each column of grid
	 */
	@Test
	public void testNoteGridGetColumns02() {
		grid.add(n0, 0);
		grid.add(n0, 1);
		
		List<Integer> actual = new ArrayList<Integer>(grid.getColumns());
		Collections.sort(actual);
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(0);
		expected.add(1);
		
		assertEquals(expected, actual);
	}

	/**
	 * Test if note grid returns a set of integers that represents each column of grid
	 */
	@Test
	public void testNoteGridGetColumns03() {
		grid.add(n0, 0);
		grid.add(n0, 1);
		grid.add(n0, 2);
		
		List<Integer> actual = new ArrayList<Integer>(grid.getColumns());
		Collections.sort(actual);
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(0);
		expected.add(1);
		expected.add(2);
		
		assertEquals(expected, actual);
	}

	/**
	 * Test if note grid returns a set of integers that represents each column of grid
	 */
	@Test
	public void testNoteGridGetColumns04() {
		grid.add(n0, 9);
		
		List<Integer> actual = new ArrayList<Integer>(grid.getColumns());
		Collections.sort(actual);
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(9);
		
		assertEquals(expected, actual);
	}

	/**
	 * Test if note grid returns a set of integers that represents each column of grid
	 */
	@Test
	public void testNoteGridGetColumns05() {
		grid.add(n0, 0);
		grid.add(n0, 1);
		grid.add(n0, 2);
		grid.add(n0, 3);
		grid.add(n0, 4);
		
		List<Integer> actual = new ArrayList<Integer>(grid.getColumns());
		Collections.sort(actual);
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(0);
		expected.add(1);
		expected.add(2);
		expected.add(3);
		expected.add(4);
		
		assertEquals(expected, actual);
	}

	/**
	 * Test if note grid returns a set of integers that represents each column of grid
	 */
	@Test
	public void testNoteGridGetColumns06() {
		grid.add(n0, 5);
		grid.add(n0, 6);
		grid.add(n0, 7);
		grid.add(n0, 8);
		grid.add(n0, 9);
		
		List<Integer> actual = new ArrayList<Integer>(grid.getColumns());
		Collections.sort(actual);
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(5);
		expected.add(6);
		expected.add(7);
		expected.add(8);
		expected.add(9);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if note grid returns a set of integers that represents each column of grid
	 */
	@Test
	public void testNoteGridGetColumns07() {
		grid.add(n0, 0);
		grid.add(n0, 1);
		grid.add(n0, 2);
		grid.add(n0, 3);
		grid.add(n0, 4);
		grid.add(n0, 5);
		grid.add(n0, 6);
		grid.add(n0, 7);
		grid.add(n0, 8);
		grid.add(n0, 9);
		
		List<Integer> actual = new ArrayList<Integer>(grid.getColumns());
		Collections.sort(actual);
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(0);
		expected.add(1);
		expected.add(2);
		expected.add(3);
		expected.add(4);
		expected.add(5);
		expected.add(6);
		expected.add(7);
		expected.add(8);
		expected.add(9);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if note grid contains notes correctly or not
	 * 
	 * grid should not contain a note that is not added
	 */
	@Test
	public void testNoteGridContains01() {
		assertFalse(grid.contains(n0, 0));
		assertFalse(grid.contains(n0, 9));
	}
	
	/**
	 * Test if note grid contains notes correctly or not
	 * 
	 * grid should not contain a note that is not added
	 */
	@Test
	public void testNoteGridContains02() {
		assertFalse(grid.contains(n0, 0));
		assertFalse(grid.contains(n0, 1));
		assertFalse(grid.contains(n0, 8));
		assertFalse(grid.contains(n0, 9));
	}
	
	/**
	 * Test if note grid contains notes correctly or not
	 * 
	 * grid should not contain a note that is not added
	 */
	@Test
	public void testNoteGridContains03() {
		assertFalse(grid.contains(n0, 0));
		assertFalse(grid.contains(n1, 1));
		assertFalse(grid.contains(n2, 2));
		assertFalse(grid.contains(n3, 3));
		assertFalse(grid.contains(n4, 4));
		assertFalse(grid.contains(n5, 5));
		assertFalse(grid.contains(n6, 6));
		assertFalse(grid.contains(n7, 7));
		assertFalse(grid.contains(n6, 8));
		assertFalse(grid.contains(n7, 9));
	}
	
	
	/**
	 * Test if note grid returns its size correctly or not
	 */
	@Test
	public void testNoteGridSize00() {
		int actual = grid.size();
		int expected = 0;
		
		assertEquals(expected, actual);
	}

	/**
	 * Test if note grid returns its size correctly or not
	 */
	@Test
	public void testNoteGridSize01() {
		grid.add(n0, 0);
		
		int actual = grid.size();
		int expected = 1;
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if note grid returns its size correctly or not
	 */
	@Test
	public void testNoteGridSize02() {
		grid.add(n0, 0);
		grid.add(n0, 1);
		
		int actual = grid.size();
		int expected = 2;
		
		assertEquals(expected, actual);
	}

	/**
	 * Test if note grid returns its size correctly or not
	 */
	@Test
	public void testNoteGridSize03() {
		grid.add(n0, 9);
		
		int actual = grid.size();
		int expected = 1;
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if note grid returns its size correctly or not
	 */
	@Test
	public void testNoteGridSize04() {
		grid.add(n0, 8);
		grid.add(n0, 9);
		
		int actual = grid.size();
		int expected = 2;
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if note grid returns its size correctly or not
	 */
	@Test
	public void testNoteGridSize05() {
		grid.add(n0, 4);
		grid.add(n1, 5);
		grid.add(n2, 6);
		grid.add(n3, 7);
		grid.add(n4, 8);
		grid.add(n5, 9);

		int actual = grid.size();
		int expected = 6;
		
		assertEquals(expected, actual);
	}	
	
	/**
	 * Test if note grid clears itself correctly or not
	 */
	@Test
	public void testNoteGridClear01() {
		grid.add(n0, 1);		
		grid.clear();
		assertFalse(grid.contains(n0, 1));
	}
	
	/**
	 * Test if note grid clears itself correctly or not
	 */
	@Test
	public void testNoteGridClear02() {
		grid.add(n0, 1);
		grid.add(n1, 1);
		grid.clear();
		assertFalse(grid.contains(n0, 1));
		assertFalse(grid.contains(n1, 1));
	}

	/**
	 * Test if note grid clears itself correctly or not
	 */
	@Test
	public void testNoteGridClear03() {
		grid.add(n0, 2);
		grid.add(n1, 2);
		grid.clear();
		assertFalse(grid.contains(n0, 2));
		assertFalse(grid.contains(n1, 2));
	}

	/**
	 * Test if note grid clears itself correctly or not
	 */
	@Test
	public void testNoteGridClear04() {
		grid.add(n0, 9);		
		grid.clear();
		assertFalse(grid.contains(n0, 9));
	}
	
	/**
	 * Test if note grid clears itself correctly or not
	 */
	@Test
	public void testNoteGridClear05() {
		grid.add(n0, 9);
		grid.add(n1, 9);		

		grid.clear();
		assertFalse(grid.contains(n0, 9));
	}
	
	/**
	 * Test if note grid clears itself correctly or not
	 */
	@Test
	public void testNoteGridClear06() {
		grid.add(n0, 9);
		grid.add(n1, 8);		

		grid.clear();
		assertFalse(grid.contains(n0, 9));
		assertFalse(grid.contains(n1, 8));
	}
	
	/**
	 * Test if note grid clears itself correctly or not
	 */
	@Test
	public void testNoteGridClear07() {
		grid.add(n0, 0);
		grid.add(n1, 1);		
		grid.add(n2, 2);
		grid.add(n3, 3);		
		grid.add(n4, 4);
		grid.add(n5, 5);		
		grid.add(n6, 6);
		grid.add(n7, 7);		
		grid.add(n8, 8);
		grid.add(n9, 9);		

		grid.clear();
		assertFalse(grid.contains(n0, 0));
		assertFalse(grid.contains(n1, 1));
		assertFalse(grid.contains(n2, 2));
		assertFalse(grid.contains(n3, 3));
		assertFalse(grid.contains(n4, 4));
		assertFalse(grid.contains(n5, 5));
		assertFalse(grid.contains(n6, 6));
		assertFalse(grid.contains(n7, 7));
		assertFalse(grid.contains(n8, 8));
		assertFalse(grid.contains(n9, 9));
	}
}