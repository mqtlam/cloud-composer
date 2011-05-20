
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class CloudAppletControllerTest {


	private CloudAppletController c;
	
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
	
}
