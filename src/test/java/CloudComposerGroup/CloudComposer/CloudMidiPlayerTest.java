import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;
import CloudComposerGroup.CloudComposer.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;


public class CloudMidiPlayerTest {

	/**	  CloudMidiPlayer object   **/
	private CloudMidiPlayer player;
	
	/**
	 * Sets CloudMidiPlayer object every time it runs a test
	 * 
	 * @throws Exception if something goes wrong
	 */
	@Before
	public void setUp() throws Exception {
		player = new CloudMidiPlayer();
	}

	/**
	 * Test if CloudMidiPlayer returns an array of instruments correctly or not
	 */
	@Test
	public void testGetInstruments() {
		String actual = "" + player.getInstruments()[0];
		for (int i = 1; i < player.getInstruments().length; i++) {
			actual += ", " + player.getInstruments()[i]; 
		}
		assertEquals("PIANO, GUITAR, DRUM, TRUMPET, VIOLIN", actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns tempo correctly or not
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetGetTempo00() throws InvalidMidiDataException {
		player.setTempo((float) 1);
		assertEquals((float) 1, player.getTempo(), 0);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns tempo correctly or not
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetGetTempo01() throws InvalidMidiDataException {
		player.setTempo((float) 1.215136);
		assertEquals((float) 1.215136, player.getTempo(), (float) 1.115);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns tempo correctly or not
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testSetGetTempo02() throws InvalidMidiDataException {
		player.setTempo((float) 5.1521136);
		assertEquals((float) 5.1521136, player.getTempo(), (float) 1.115);
	}
	
	/**
	 * Test if CloudMidiPlayer plays a song correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlay01() throws InvalidMidiDataException {
		player.setSequence(new Sequence(0, 0));
		player.play();
		assertTrue(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer plays a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testPlay02() throws InvalidMidiDataException {
		player.setSequence(new Sequence(Sequence.PPQ, 0));
		player.play();
		assertTrue(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer plays a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testPlay03() throws InvalidMidiDataException {
		player.setSequence(new Sequence(Sequence.SMPTE_24, 0));
		player.play();
		assertTrue(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer plays a song correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testPlay04() throws InvalidMidiDataException {
		player.setSequence(new Sequence(Sequence.SMPTE_30DROP, 0));
		player.play();
		assertTrue(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer plays a song correctly or not
	 * 
	 * Player is not suppose to play a song until it is requested. 
	 */
	@Test
	public void testPlay05() {
		assertFalse(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer plays a song correctly or not
	 * 
	 * Player is not suppose to play a song until it is requested. 
	 */
	@Test
	public void testPlay06() {
		assertFalse(player.isPlaying());
	}
		
	/**
	 * Test if CloudMidiPlayer plays a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote02() throws InvalidMidiDataException {
		c.player.playNote(CloudMidiPlayer.SequenceInst.GUITAR, 0);
		assertTrue(c.player.isPlaying());
	}

	/**
	 * Test if CloudMidiPlayer plays a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote03() throws InvalidMidiDataException {
		c.player.playNote(CloudMidiPlayer.SequenceInst.PIANO, 0);
		assertTrue(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer plays a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote04() throws InvalidMidiDataException {
		c.player.playNote(CloudMidiPlayer.SequenceInst.DRUM, 2);
		assertTrue(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer plays a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote05() throws InvalidMidiDataException {
		c.player.playNote(CloudMidiPlayer.SequenceInst.GUITAR, 5);
		assertTrue(c.player.isPlaying());
	}

	/**
	 * Test if CloudMidiPlayer plays a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote06() throws InvalidMidiDataException {
		c.player.playNote(CloudMidiPlayer.SequenceInst.PIANO, 9);
		assertTrue(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer plays a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote07() throws InvalidMidiDataException {
		assertFalse(c.player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a sequence correctly or not 
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testSetGetSequence01() throws InvalidMidiDataException {
		Sequence expected = new Sequence(0, 0);
		player.setSequence(expected);
		Sequence actual = player.getSequence();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a sequence correctly or not 
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testSetGetSequence02() throws InvalidMidiDataException {
		Sequence expected = new Sequence(Sequence.SMPTE_24, 0);
		player.setSequence(expected);
		Sequence actual = player.getSequence();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a sequence correctly or not 
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testSetGetSequence03() throws InvalidMidiDataException {
		Sequence expected = new Sequence(Sequence.PPQ, 0);
		player.setSequence(expected);
		Sequence actual = player.getSequence();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a sequence correctly or not 
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testSetGetSequence04() throws InvalidMidiDataException {
		Sequence expected = new Sequence(Sequence.SMPTE_25, 0);
		player.setSequence(expected);
		Sequence actual = player.getSequence();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a sequence correctly or not 
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testSetGetSequence05() throws InvalidMidiDataException {
		Sequence expected = new Sequence(Sequence.SMPTE_30DROP, 0);
		player.setSequence(expected);
		Sequence actual = player.getSequence();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer pauses playing correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testPause01() throws InvalidMidiDataException {
		player.setSequence(new Sequence(0, 0));
		player.play();
		player.stop();
		assertFalse(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer pauses playing correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testPause02() throws InvalidMidiDataException {
		player.setSequence(new Sequence(Sequence.PPQ, 0));
		player.play();
		player.stop();
		assertFalse(player.isPlaying());
	}

	/**
	 * Test if CloudMidiPlayer pauses playing correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testPause03() throws InvalidMidiDataException {
		player.setSequence(new Sequence(Sequence.SMPTE_24, 0));
		player.play();
		player.stop();
		assertFalse(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer pauses playing correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testPause04() throws InvalidMidiDataException {
		player.setSequence(new Sequence(Sequence.SMPTE_30, 0));
		player.play();
		player.stop();
		assertFalse(player.isPlaying());
	}

	/**
	 * Test if CloudMidiPlayer pauses playing correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testPause05() throws InvalidMidiDataException {
		player.setSequence(new Sequence(Sequence.SMPTE_30DROP, 0));
		player.play();
		player.stop();
		assertFalse(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer stops playing correctly or not
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testStop01() throws InvalidMidiDataException {
		player.setSequence(new Sequence(0, 0));
		player.play();
		player.stop();
		assertFalse(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer stops playing correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testStop02() throws InvalidMidiDataException {
		player.setSequence(new Sequence(Sequence.PPQ, 0));
		player.play();
		player.stop();
		assertFalse(player.isPlaying());
	}

	/**
	 * Test if CloudMidiPlayer stops playing correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testStop03() throws InvalidMidiDataException {
		player.setSequence(new Sequence(Sequence.SMPTE_24, 0));
		player.play();
		player.stop();
		assertFalse(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer stops playing correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testStop04() throws InvalidMidiDataException {
		player.setSequence(new Sequence(Sequence.SMPTE_30, 0));
		player.play();
		player.stop();
		assertFalse(player.isPlaying());
	}

	/**
	 * Test if CloudMidiPlayer stops playing correctly or not
	 * 
	 * @throws InvalidMidiDataException if it fails to run the player
	 */
	@Test
	public void testStop05() throws InvalidMidiDataException {
		player.setSequence(new Sequence(Sequence.SMPTE_30DROP, 0));
		player.play();
		player.stop();
		assertFalse(player.isPlaying());
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn01() {
		int expected = 0;
		player.setPlayTime(expected);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn02() {
		int expected = 1;
		player.setPlayTime(expected);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn03() {
		int expected = 2;
		player.setPlayTime(expected);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn04() {
		int expected = 5;
		player.setPlayTime(expected);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn05() {
		int expected = 6;
		player.setPlayTime(expected);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn06() {
		int expected = 100;
		player.setPlayTime(expected);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn07() {
		int expected = 500;
		player.setPlayTime(expected);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn08() {
		int expected = 1000;
		player.setPlayTime(expected);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn09() {
		int expected = 10000;
		player.setPlayTime(expected);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}

	
}
