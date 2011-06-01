import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

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
		c.player.setSequence(CloudMidiPlayer.basicSequence());
		c.player.play();
		assertTrue(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls playing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the c.player
	 */
	/*@Test
	public void testPlay02() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.PPQ, 0));
		c.player.play();
		assertTrue(c.player.isPlaying());
	}*/
	
	/**
	 * Test if CloudAppletController controls playing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the c.player
	 */
	/*@Test
	public void testPlay03() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_24, 0));
		c.player.play();
		assertTrue(c.player.isPlaying());
	}*/
	
	/**
	 * Test if CloudAppletController controls playing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the c.player
	 */
	/*@Test
	public void testPlay04() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_30DROP, 0));
		c.player.play();
		assertTrue(c.player.isPlaying());
	}*/
	
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
	/*@Test
	public void testStop01() throws InvalidMidiDataException {
		c.player.setSequence(CloudMidiPlayer.basicSequence());
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}*/
	
	/**
	 * Test if CloudAppletController controls stopping a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	/*@Test
	public void testStop02() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.PPQ, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}*/

	/**
	 * Test if CloudAppletController controls stopping a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	/*@Test
	public void testStop03() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_24, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}*/
	
	/**
	 * Test if CloudAppletController controls stopping a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	/*@Test
	public void testStop04() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_30, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}*/

	/**
	 * Test if CloudAppletController controls stopping a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	/*@Test
	public void testStop05() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_30DROP, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}*/

	/**
	 * Test if CloudAppletController controls pausing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	@Test
	public void testPause01() throws InvalidMidiDataException {
		c.player.setSequence(CloudMidiPlayer.basicSequence());
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudAppletController controls pausing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	/*@Test
	public void testPause02() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.PPQ, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}*/

	/**
	 * Test if CloudAppletController controls pausing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	/*@Test
	public void testPause03() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_24, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}*/
	
	/**
	 * Test if CloudAppletController controls pausing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	/*@Test
	public void testPause04() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_30, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}*/

	/**
	 * Test if CloudAppletController controls pausing a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run c.player
	 */
	/*@Test
	public void testPause05() throws InvalidMidiDataException {
		c.player.setSequence(new Sequence(Sequence.SMPTE_30DROP, 0));
		c.player.play();
		c.player.stop();
		assertFalse(c.player.isPlaying());
	}*/

	/**
	 * Test if CloudAppletController controls setting and returning a column correctly
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition01() throws InvalidMidiDataException {
		int expected = 0;
		c.setSongPosition(0);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudAppletController controls setting and returning a column correctly
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition02() throws InvalidMidiDataException {
		int expected = 1;
		c.setSongPosition(1);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudAppletController controls setting and returning a column correctly
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition03() throws InvalidMidiDataException {
		int expected = 2;
		c.setSongPosition(2);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudAppletController controls setting and returning a column correctly
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition04() throws InvalidMidiDataException {
		int expected = 5;
		c.setSongPosition(5);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudAppletController controls setting and returning a column correctly
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition05() throws InvalidMidiDataException {
		int expected = 6;
		c.setSongPosition(6);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudAppletController controls setting and returning a column correctly
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition06() throws InvalidMidiDataException {
		int expected = 100;
		c.setSongPosition(100);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudAppletController controls setting and returning a column correctly
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition07() throws InvalidMidiDataException {
		int expected = 500;
		c.setSongPosition(500);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudAppletController controls setting and returning a column correctly
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition08() throws InvalidMidiDataException {
		int expected = 1000;
		c.setSongPosition(1000);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudAppletController controls setting and returning a column correctly
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetSongPositionAndCurrentSongPosition09() throws InvalidMidiDataException {
		int expected = 10000;
		c.setSongPosition(10000);
		int actual = c.currentSongPosition();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudAppletController controls setting and returning tempo correctly or not
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetGetTempo00() throws InvalidMidiDataException {
		c.setTempo((float) 120);
		assertEquals((float) 120, c.getTempo(), 0.1);
	}
	
	/**
	 * Test if CloudAppletController controls setting and returning tempo correctly or not
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetGetTempo01() throws InvalidMidiDataException {
		c.setTempo((float) 1.215136);
		assertEquals((float) 1.215136, c.getTempo(), (float) 1.115);
	}
	
	/**
	 * Test if CloudAppletController controls setting and returning tempo correctly or not
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetGetTempo02() throws InvalidMidiDataException {
		c.setTempo((float) 5.1521136);
		assertEquals((float) 5.1521136, c.getTempo(), (float) 1.115);
	}
	
	/**
	 * Constructs the Sequence objects for testGetSequencesxx.
	 */
	private Sequence buildSequences(int[] note) throws InvalidMidiDataException {
		buildPlayerSequence(note);
		return buildControlSequence(note);
	}
	
	/**
	 * Constructs the outer Sequence object to test against.
	 * @param note, the note to add to the Sequence.
	 * @return The control Outside Sequence generated.
	 */
	private Sequence buildControlSequence(int[] note) {
		Sequence s = CloudMidiPlayer.basicSequence();
		CloudMidiPlayer.SequenceInst instrument = c.player.getInstruments()[note[0]];
		CloudMidiPlayer.addNote(s, instrument, note[1], note[2], note[3]+1);
		CloudMidiPlayer.setSilentEndNote(s);
		return s;
	}
	
	/**
	 * Constructs the Player's Sequence using the CloudAppletController's methods.
	 * @param note, the note to add to the Sequence.
	 * @throws InvalidMidiDataException
	 */
	private void buildPlayerSequence(int[] note) throws InvalidMidiDataException {
		c.addNote(note);
		c.updateSequence();
	}
	
	/**
	 * Tests to make sure that the Player's Sequence is never null.
	 */
	@Test
	public void testGetSongSequence01() {
		Sequence s = c.getSongSequence();
		assertFalse(s == null);
	}
	
	/**
	 * Ensures the track lengths are equivalent and are always 1.
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testGetSongSequence02() throws InvalidMidiDataException {
		Sequence s = buildSequences(new int[] {0, 0, 0, 3});
		Sequence s2 = c.getSongSequence();
		Track[] t1 = s.getTracks();
		Track[] t2 = s2.getTracks();
		assertTrue(t1.length == t2.length);
		assertTrue(t1.length == 1);
	}
	
	/**
	 * Ensures that the number of nothing is erroneously added to the song data.
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testGetSongSequence03() throws InvalidMidiDataException {
		Sequence s = buildSequences(new int[] {1, 2, 3, 5});
		Sequence s2 = c.getSongSequence();
		Track track1 = s.getTracks()[0];
		Track track2 = s2.getTracks()[0];
		assertTrue(track1.size() == track2.size());
	}
	
	/**
	 * Ensures that the Messages that happen in each Sequence are at the same location.
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testGetSongSequence04() throws InvalidMidiDataException {
		Sequence s = buildSequences(new int[] {3, 3, 3, 3});
		Sequence s2 = c.getSongSequence();
		Track track1 = s.getTracks()[0];
		Track track2 = s2.getTracks()[0];
		for (int j = 0; j < track1.size(); j++) {
			MidiEvent m1 = track1.get(j);
			MidiEvent m2 = track2.get(j);
			assertTrue(m1.getTick() == m2.getTick());
		}
	}
	
	/**
	 * Deep test in the actual byte data to compare the Sequences 
	 * and make sure they are exactly the same.
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testGetSongSequence05() throws InvalidMidiDataException {
		Sequence s = buildSequences(new int[] {4, 9, 1, 11});
		Sequence s2 = c.getSongSequence();
		Track track1 = s.getTracks()[0];
		Track track2 = s2.getTracks()[0];
		for (int j = 0; j < track1.size(); j++) {
			MidiEvent m1 = track1.get(j);
			MidiEvent m2 = track2.get(j);
			byte[] b1 = m1.getMessage().getMessage();
			byte[] b2 = m2.getMessage().getMessage();
			assertTrue(b1.length == b2.length);
			for (int k = 0; k < b1.length; k++) {
				assertTrue(b1[k] == b2[k]);
			}
		}
	}
	
	/**
	 * Tests updateSequence to make sure updating a sequence actually sets it.
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testUpdateSequence01() throws InvalidMidiDataException {
		c.updateSequence();
		Sequence s = c.getSongSequence();
		assertFalse(s == null);
	}
	
	/**
	 * Tests updateSequence to test that the Sequence is not modified after setting.
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testUpdateSequence02() throws InvalidMidiDataException {
		Sequence s = buildSequences(new int[] {0, 3, 0, 6});
		c.updateSequence();
		Sequence s2 = c.getSongSequence();
		assertFalse(s.equals(s2));
	}

	/**
	 * Tests update sequence to ensure different sequences are not confused.
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testUpdateSequence03() throws InvalidMidiDataException {
		Sequence s = buildSequences(new int[] {0, 3, 0, 6});
		c.addNote(new int[] {1, 1, 1, 2});
		c.updateSequence();
		Sequence s2 = c.getSongSequence();
		assertFalse(s.equals(s2));
	}
	
	/**
	 * Tests updateSequence to test against another sequence to ensure difference.
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testUpdateSequence04() throws InvalidMidiDataException {
		Sequence s = buildSequences(new int[] {0, 0, 0, 10});
		c.addNote(new int[] {1, 1, 1, 2});
		c.addNote(new int[] {2, 9, 1, 4});
		c.updateSequence();
		Sequence s2 = c.getSongSequence();
		Track track1 = s.getTracks()[0];
		Track track2 = s2.getTracks()[0];
		assertFalse(track1.size() == track2.size());
	
	}
	
	/**
	 * Tests updateSequence to test against another sequence to ensure difference.
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testUpdateSequence05() throws InvalidMidiDataException {
		Sequence s = buildSequences(new int[] {0, 0, 0, 10});
		c.addNote(new int[] {1, 1, 1, 2});
		c.addNote(new int[] {4, 9, 1, 4});
		c.addNote(new int[] {3, 3, 1, 4});
		c.addNote(new int[] {0, 1, 1, 4});
		c.addNote(new int[] {2, 7, 1, 4});
		c.updateSequence();
		Sequence s2 = c.getSongSequence();
		Track track1 = s.getTracks()[0];
		Track track2 = s2.getTracks()[0];
		assertFalse(track1.size() == track2.size());
	}
	
	/**
	 * Tests to make sure the byte data of each MidiEvent is the same
	 * after updating.
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testUpdateSequence06() throws InvalidMidiDataException {
		Sequence s = buildSequences(new int[] {4, 9, 1, 11});
		c.addNote(new int[] {1, 1, 1, 2});
		c.removeNote(new int[] {1, 1, 1, 2});
		c.updateSequence();
		Sequence s2 = c.getSongSequence();
		Track track1 = s.getTracks()[0];
		Track track2 = s2.getTracks()[0];
		for (int j = 0; j < track1.size(); j++) {
			MidiEvent m1 = track1.get(j);
			MidiEvent m2 = track2.get(j);
			byte[] b1 = m1.getMessage().getMessage();
			byte[] b2 = m2.getMessage().getMessage();
			assertTrue(b1.length == b2.length);
			for (int k = 0; k < b1.length; k++) {
				assertTrue(b1[k] == b2[k]);
			}
		}
	}
	
	/**
	 * Tests adding a long series of repeating notes to ensure that the notes
	 * are being added properly to the sequence.
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testAddBunchesOfNotes() throws InvalidMidiDataException {
		Sequence s = CloudMidiPlayer.basicSequence();
		
		for (int i = 0; i < 50; i++) {
			int[] note = {0,0,i,i+1};
			c.addNote(note);
		}
		for (int i = 0; i < 50; i++) {
			CloudMidiPlayer.addNote(s, c.player.getInstruments()[0], 0, i, i+2);
		}
		CloudMidiPlayer.setSilentEndNote(s);
		c.updateSequence();
		Sequence s2 = c.getSongSequence();
		Track track1 = s.getTracks()[0];
		Track track2 = s2.getTracks()[0];
		for (int j = 0; j < track1.size(); j++) {
			MidiEvent m1 = track1.get(j);
			MidiEvent m2 = track2.get(j);
			byte[] b1 = m1.getMessage().getMessage();
			byte[] b2 = m2.getMessage().getMessage();
			assertTrue(b1.length == b2.length);
			/*boolean active = false;*/
			for (int k = 0; k < b1.length; k++) {
				/*System.out.println("j = " + j + " k = " + k);
				if (b1[k] != b2[k] || active) {
					System.out.println(b1[k] + " " + b2[k]);
					active = true;
				}*/
				assertTrue("Failed event "+j+" byte "+k,b1[k] == b2[k]);
			}
		}
		
	}


}
