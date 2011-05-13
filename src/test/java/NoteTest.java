/**
 * Unit test for CloudComposerGroup.Note class!
 */

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;
import CloudComposerGroup.*;

public class NoteTest  {
	@Test
	
	public void testNote() {
		Note n = new CloudComposerGroup.Note(3, 7, 12);
		assertEquals(3, n.length, 0);
		assertEquals(7, n.instrument, 0);
		assertEquals(12, n.pitch, 0);
		}
	}