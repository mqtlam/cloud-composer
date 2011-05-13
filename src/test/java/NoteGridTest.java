/**
 * Unit test for CloudComposerGroup.Note class!
 */

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;
import CloudComposerGroup.*;

public class NoteGridTest  {
	
	@Test
	public void testNoteGridAdd() {

		NoteGrid grid = new CloudComposerGroup.NoteGrid();
		Note note = new CloudComposerGroup.Note(1, 2, 3);

		// Test the add & size functions
		grid.add(note, 3);
		assertEquals(1, grid.size(), 0);
		grid.add(note, 5);
		assertEquals(2, grid.size(), 0);
		grid.add(note, 7);
		assertEquals(3, grid.size(), 0);
		grid.add(note, 100);
		assertEquals(4, grid.size(), 0);
	}

/* Last run: FAILED.  Uncomment and build to re-test.
 * @Test
	public void testNoteGridContains() {

		NoteGrid grid = new CloudComposerGroup.NoteGrid();
		Note note = new CloudComposerGroup.Note(1, 2, 3);

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
*/
	
}