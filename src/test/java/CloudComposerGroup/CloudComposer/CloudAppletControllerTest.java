import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import CloudComposerGroup.CloudComposer.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
	@SuppressWarnings("deprecation")
	@Test
	public void testGetInstrument01() {
		String[] expected = {"PIANO", "GUITAR", "DRUM", "TRUMPET", "VIOLIN"};
		String[] actual = c.getInstruments();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudAppletController returns a list of strings of instruments correctly or not
	 */
	@Test
	public void testGetInstrument02() {
		String[] expected = {"", "", "", "", ""};
		String[] actual = c.getInstruments();
		boolean result = expected.equals(actual);
		assertFalse(result);
	}
	
	/**
	 * Test if CloudAppletController returns a list of strings of instruments correctly or not
	 */
	@Test
	public void testGetInstrument03() {
		String[] expected = {"PIANO", "GUITAR", "DRUMMM", "TRUMPET", "VIOLIN"};
		String[] actual = c.getInstruments();
		boolean result = expected.equals(actual);
		assertFalse(result);
	}
	
	/**
	 * Test if CloudAppletController returns a list of strings of instruments correctly or not
	 */
	@Test
	public void testGetInstrument04() {
		String[] expected = {"PIANOOOO", "GUITAR", "DRUMMM", "TRUMPET", "VIOLIN"};
		String[] actual = c.getInstruments();
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


}
