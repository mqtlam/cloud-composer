import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;
import CloudComposerGroup.CloudComposer.*;

public class CloudAppletControllerTest extends TestCase {
	
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
		c.player.setSequence(CloudMidiPlayer.basicSequence());
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

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testRemoveNote01() {
		int[] noteData = {0, 0, 0, 0};
		c.addNote(noteData);
		c.removeNote(noteData);
		assertFalse(c.grid.contains(new Note(1, 0, 0), 0));
	}
	
	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testRemoveNote02() {
		int[] noteData = {1, 0, 0, 0};
		c.addNote(noteData);
		c.removeNote(noteData);
		assertFalse(c.grid.contains(new Note(1, 1, 0), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testRemoveNote03() {
		int[] noteData = {2, 0, 0, 0};
		c.addNote(noteData);
		c.removeNote(noteData);
		assertFalse(c.grid.contains(new Note(1, 2, 0), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testRemoveNote04() {
		int[] noteData = {3, 0, 0, 0};
		c.addNote(noteData);
		c.removeNote(noteData);
		assertFalse(c.grid.contains(new Note(1, 3, 0), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testRemoveNote05() {
		int[] noteData = {4, 0, 0, 0};
		c.addNote(noteData);
		c.removeNote(noteData);
		assertFalse(c.grid.contains(new Note(1, 4, 0), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testRemoveNote06() {
		int[] noteData = {1, 4, 0, 0};
		c.addNote(noteData);
		c.removeNote(noteData);
		assertFalse(c.grid.contains(new Note(1, 1, 4), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testRemoveNote07() {
		int[] noteData = {3, 2, 0, 0};
		c.addNote(noteData);
		c.removeNote(noteData);
		assertFalse(c.grid.contains(new Note(1, 3, 2), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testRemoveNote08() {
		int[] noteData = {4, 6, 0, 5};
		c.addNote(noteData);
		c.removeNote(noteData);
		assertFalse(c.grid.contains(new Note(6, 4, 6), 0));
	}

	/**
	 * Test if CloudAppletController add notes correctly or not
	 */
	@Test
	public void testRemoveNote09() {
		int[] noteData = {3, 9, 6, 20};
		c.addNote(noteData);
		c.removeNote(noteData);
		assertFalse(c.grid.contains(new Note(15, 3, 9), 6));
	}

	/**
	 * Test if CloudAppletController returns a list of strings of instruments correctly or not
	 */
	@Test
	public void testGetInstrument01() {
		String expected = "PIANO, GUITAR, DRUM, TRUMPET, VIOLIN";
		String[] inst = c.getInstruments();
		String actual = "" + inst[0];
		for (int i = 1; i < 5; i++) {
			actual += ", " + inst[i];
		}
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudAppletController returns a list of strings of instruments correctly or not
	 */
	@Test
	public void testGetInstrument02() {
		String expected = "";
		String[] inst = c.getInstruments();
		String actual = "" + inst[0];
		for (int i = 1; i < 5; i++) {
			actual += ", " + inst[i];
		}
		boolean result = expected.equals(actual);
		assertFalse(result);
	}
	
	/**
	 * Test if CloudAppletController returns a list of strings of instruments correctly or not
	 */
	@Test
	public void testGetInstrument03() {
		String expected = "PIANO, GUITAR, DRUMMM, TRUMPET, VIOLIN";
		String[] inst = c.getInstruments();
		String actual = "" + inst[0];
		for (int i = 1; i < 5; i++) {
			actual += ", " + inst[i];
		}
		boolean result = expected.equals(actual);
		assertFalse(result);
	}

	/**
	 * Test if CloudAppletController returns a list of strings of instruments correctly or not
	 */
	@Test
	public void testGetInstrument04() {
		String expected = "PIANOOOO, GUITAR, DRUM, TRUMPET, VIOLIN";
		String[] inst = c.getInstruments();
		String actual = "" + inst[0];
		for (int i = 1; i < 5; i++) {
			actual += ", " + inst[i];
		}
		boolean result = expected.equals(actual);
		assertFalse(result);
	}

	/**
	 * Test if CloudAppletController controls playing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlay01() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(0, 0));
		c.player.play();
		assertTrue(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls playing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the c.player
	 */
	@Test
	public void testPlay02() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.PPQ, 0));
		c.player.play();
		assertTrue(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls playing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the c.player
	 */
	@Test
	public void testPlay03() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_24, 0));
		c.player.play();
		assertTrue(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls playing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the c.player
	 */
	@Test
	public void testPlay04() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_30DROP, 0));
		c.player.play();
		assertTrue(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls playing a song correctly or not
	 * 
	 * Player is not suppose to play a song until it is requested. 
	 */
	@Test
	public void testPlay05() {
		assertFalse(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls playing a song correctly or not
	 * 
	 * Player is not suppose to play a song until it is requested. 
	 */
	@Test
	public void testPlay06() {
		assertFalse(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls playing a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote02() throws InvalidMidiDataException {
		c.player.playNote(CloudMidiPlayer.SequenceInst.GUITAR, 0);
		assertTrue(c.player.isPlaying());
	}

	/**
	 * Test if CloudAppletController controls playing a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote03() throws InvalidMidiDataException {
		c.player.playNote(CloudMidiPlayer.SequenceInst.PIANO, 0);
		assertTrue(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls playing a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote04() throws InvalidMidiDataException {
		c.player.playNote(CloudMidiPlayer.SequenceInst.DRUM, 2);
		assertTrue(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls playing a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote05() throws InvalidMidiDataException {
		c.player.playNote(CloudMidiPlayer.SequenceInst.GUITAR, 5);
		assertTrue(c.player.isPlaying());
	}

	/**
	 * Test if CloudAppletController controls playing a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote06() throws InvalidMidiDataException {
		c.player.playNote(CloudMidiPlayer.SequenceInst.PIANO, 9);
		assertTrue(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls playing a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote07() throws InvalidMidiDataException {
		assertFalse(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls stopping a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	@Test
	public void testStop01() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(0, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls stopping a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	@Test
	public void testStop02() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.PPQ, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}

	/**
	 * Test if CloudAppletController controls stopping a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	@Test
	public void testStop03() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_24, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls stopping a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	@Test
	public void testStop04() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_30, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}

	/**
	 * Test if CloudAppletController controls stopping a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	@Test
	public void testStop05() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_30DROP, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}

	/**
	 * Test if CloudAppletController controls pausing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	@Test
	public void testPause01() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(0, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls pausing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	@Test
	public void testPause02() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.PPQ, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}

	/**
	 * Test if CloudAppletController controls pausing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	@Test
	public void testPause03() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_24, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls pausing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	@Test
	public void testPause04() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_30, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}

	/**
	 * Test if CloudAppletController controls pausing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	@Test
	public void testPause05() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_30DROP, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition01() {
		int expected = 0;
		c.setSongPosition(0);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition02() {
		int expected = 1;
		c.setSongPosition(1);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition03() {
		int expected = 2;
		c.setSongPosition(2);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition04() {
		int expected = 5;
		c.setSongPosition(5);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition05() {
		int expected = 6;
		c.setSongPosition(6);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition06() {
		int expected = 100;
		c.setSongPosition(100);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition07() {
		int expected = 500;
		c.setSongPosition(500);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition08() {
		int expected = 1000;
		c.setSongPosition(1000);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition09() {
		int expected = 10000;
		c.setSongPosition(10000);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}

}
