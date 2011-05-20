import CloudComposerGroup.CloudComposer.*;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CloudAppletControllerTest {
	
	/**   CloudAppletController   **/
	private CloudAppletController c;
	
	/**
	 * sets CloudAppletContoller object every time it runs a test
	 * 
	 * @throws Exception if something goes wrong
	 */
	@Before
	public void setUp() throws Exception {
		c = new CloudAppletController();
		c.init();
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testAddNote01() {
		int[] noteData = {0, 0, 0, 0};
		c.addNote(noteData);
		assertTrue(c.grid.contains(new Note(1, 0, 0), 0));
	}
	
	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testAddNote02() {
		int[] noteData = {1, 0, 0, 0};
		c.addNote(noteData);
		assertTrue(c.grid.contains(new Note(1, 1, 0), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testAddNote03() {
		int[] noteData = {2, 0, 0, 0};
		c.addNote(noteData);
		assertTrue(c.grid.contains(new Note(1, 2, 0), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testAddNote04() {
		int[] noteData = {3, 0, 0, 0};
		c.addNote(noteData);
		assertTrue(c.grid.contains(new Note(1, 3, 0), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testAddNote05() {
		int[] noteData = {4, 0, 0, 0};
		c.addNote(noteData);
		assertTrue(c.grid.contains(new Note(1, 4, 0), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testAddNote06() {
		int[] noteData = {1, 4, 0, 0};
		c.addNote(noteData);
		assertTrue(c.grid.contains(new Note(1, 1, 4), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testAddNote07() {
		int[] noteData = {3, 2, 0, 0};
		c.addNote(noteData);
		assertTrue(c.grid.contains(new Note(1, 3, 2), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testAddNote08() {
		int[] noteData = {4, 6, 0, 5};
		c.addNote(noteData);
		assertTrue(c.grid.contains(new Note(6, 4, 6), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testAddNote09() {
		int[] noteData = {3, 9, 6, 20};
		c.addNote(noteData);
		assertTrue(c.grid.contains(new Note(15, 3, 9), 6));
	}

	
}
