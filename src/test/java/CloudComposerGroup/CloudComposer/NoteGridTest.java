/**
 * Unit test for CloudComposerGroup.Note class!
 */

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;
import CloudComposerGroup.CloudComposer.*;


public class NoteGridTest  {
	
	private NoteGrid grid;
	
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
	
	private Note n10;

	/**
	 * sets NoteGrid and Note objects every single time it runs a test
	 */
	@Before
	public void setUp() {
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
	 */
	@Test
	public void testNoteGridAdd00() {
		grid.add(n0, 0);

		assertTrue(grid.contains(n0, 0));
	}
	
	/**
	 * Test if note grid adds note objects in a column correctly or not
	 */
	@Test
	public void testNoteGridAdd01() {
		grid.add(n0, 0);
		grid.add(n1, 1);

		assertTrue(grid.contains(n0, 0));
		assertTrue(grid.contains(n1, 1));
	}
	
	/**
	 * Test if note grid adds note objects in a column correctly or not
	 */
	@Test
	public void testNoteGridAdd02() {
		grid.add(n0, 9);

		assertTrue(grid.contains(n0, 9));
	}
	
	/**
	 * Test if note grid adds note objects in a column correctly or not
	 */
	@Test
	public void testNoteGridAdd03() {
		grid.add(n0, 9);

		assertTrue(grid.contains(n0, 9));
	}
	
	/**
	 * Test if note grid adds note objects in a column correctly or not
	 */
	@Test
	public void testNoteGridAdd04() {
		grid.add(n5, 8);
		grid.add(n6, 9);

		assertTrue(grid.contains(n5, 8));
		assertTrue(grid.contains(n6, 9));
	}


	/**
	 * Test if note grid adds note objects in a column correctly or not
	 */
	@Test
	public void testNoteGridAdd05() {
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
	 */
	@Test
	public void testNoteGridAdd06() {
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
	 * Test if note grid removes note objects in a column correctly or not
	 */
	@Test
	public void testNoteGridRemove01() {
		grid.add(n0, 1);		
		grid.remove(n0, 1);
		assertFalse(grid.contains(n0, 1));
	}
	
	/**
	 * Test if note grid removes note objects in a column correctly or not
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

	
	public void testNoteGridContains() {

		NoteGrid grid = new NoteGrid();
		Note note = new Note(1, 2, 3);

		// Test the add & contains functions
		grid.add(note, 3);
		grid.add(note, 5);
		grid.add(note, 7);
		grid.add(note, 100);
		assertTrue(grid.contains(note, 3));
		assertTrue(grid.contains(note, 5));
		assertTrue(grid.contains(note, 7));
		assertTrue(grid.contains(note, 100));
	}

}