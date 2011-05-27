/**
 * Unit test for CloudComposerGroup.Note class!
 */
import CloudComposerGroup.CloudComposer.*;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NoteTest {

	/** Note objects **/
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
	private Note n11;
	private Note n12;
	private Note n13;
	private Note n14;
	private Note n15;
	private Note n16;


	/** Sets Note objects every time it runs a test	**/
	@Before
	public void setUp() {
		n1 = new Note(1, 0, 0);
		n2 = new Note(1, 1, 1);
		n3 = new Note(1, 2, 2);
		n4 = new Note(1, 3, 3);
		n5 = new Note(1, 4, 4);
		n6 = new Note(2, 0, 0);
		n7 = new Note(2, 1, 1);
		n8 = new Note(2, 2, 2);
		n9 = new Note(2, 3, 3);
		n10 = new Note(2, 4, 4);
		n11 = new Note(2, 4, 9);
		n12 = new Note(5, 0, 6);
		n13 = new Note(6, 3, 6);
		n14 = new Note(7, 2, 8);
		n15 = new Note(100, 2, 8);
		n16 = new Note(10000, 0, 5);
	}
	
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote01() {
		String actual = n1.length + " " + n1.instrument + " " + n1.pitch;
		String expected = 1 + " " + 0 + " " + 0;
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote02() {
		String actual = n2.length + " " + n2.instrument + " " + n2.pitch;
		String expected = 1 + " " + 1 + " " + 1;
		assertEquals(expected, actual);
	}

	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote03() {
		String actual = n3.length + " " + n3.instrument + " " + n3.pitch;
		String expected = 1 + " " + 2 + " " + 2;
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote04() {
		String actual = n4.length + " " + n4.instrument + " " + n4.pitch;
		String expected = 1 + " " + 3 + " " + 3;
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote05() {
		String actual = n5.length + " " + n5.instrument + " " + n5.pitch;
		String expected = 1 + " " + 4 + " " + 4;
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote06() {
		String actual = n6.length + " " + n6.instrument + " " + n6.pitch;
		String expected = 2 + " " + 0 + " " + 0;
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote07() {
		String actual = n7.length + " " + n7.instrument + " " + n7.pitch;
		String expected = 2 + " " + 1 + " " + 1;
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote08() {
		String actual = n8.length + " " + n8.instrument + " " + n8.pitch;
		String expected = 2 + " " + 2 + " " + 2;
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote09() {
		String actual = n9.length + " " + n9.instrument + " " + n9.pitch;
		String expected = 2 + " " + 3 + " " + 3;
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote10() {
		String actual = n10.length + " " + n10.instrument + " " + n10.pitch;
		String expected = 2 + " " + 4 + " " + 4;
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote11() {
		String actual = n11.length + " " + n11.instrument + " " + n11.pitch;
		String expected = 2 + " " + 4 + " " + 9;
		assertEquals(expected, actual);
	}
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote12() {
		String actual = n12.length + " " + n12.instrument + " " + n12.pitch;
		String expected = 5 + " " + 0 + " " + 6;
		assertEquals(expected, actual);
	}
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote13() {
		String actual = n13.length + " " + n13.instrument + " " + n13.pitch;
		String expected = 6 + " " + 3 + " " + 6;
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote14() {
		String actual = n14.length + " " + n14.instrument + " " + n14.pitch;
		String expected = 7 + " " + 2 + " " + 8;
		assertEquals(expected, actual);
	}
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote15() {
		String actual = n15.length + " " + n15.instrument + " " + n15.pitch;
		String expected = 100 + " " + 2 + " " + 8;
		assertEquals(expected, actual);
	}
	/**
	 * Test if a Note object is created correctly or not
	 */
	@Test
	public void TestNote16() {
		String actual = n16.length + " " + n16.instrument + " " + n16.pitch;
		String expected = 10000 + " " + 0 + " " + 5;
		assertEquals(expected, actual);
	}

}