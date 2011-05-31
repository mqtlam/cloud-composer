import CloudComposerGroup.CloudComposer.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class CloudMidiPlayerTest extends TestCase {

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
		player.setSequence(CloudMidiPlayer.basicSequence());
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
		player.setTempo((float) 120);
		assertEquals((float) 120, player.getTempo(), 0.1);
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
		player.playNote(CloudMidiPlayer.SequenceInst.GUITAR, 0);
		assertTrue(player.isPlaying());
	}

	/**
	 * Test if CloudMidiPlayer plays a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote03() throws InvalidMidiDataException {
		player.playNote(CloudMidiPlayer.SequenceInst.PIANO, 0);
		assertTrue(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer plays a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote04() throws InvalidMidiDataException {
		player.playNote(CloudMidiPlayer.SequenceInst.DRUM, 2);
		assertTrue(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer plays a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote05() throws InvalidMidiDataException {
		player.playNote(CloudMidiPlayer.SequenceInst.GUITAR, 5);
		assertTrue(player.isPlaying());
	}

	/**
	 * Test if CloudMidiPlayer plays a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote06() throws InvalidMidiDataException {
		player.playNote(CloudMidiPlayer.SequenceInst.PIANO, 9);
		assertTrue(player.isPlaying());
	}
	
	/**
	 * Test if CloudMidiPlayer plays a note correctly or not
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testPlayNote07() throws InvalidMidiDataException {
		assertFalse(player.isPlaying());
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
		placeNote(0);
		player.setPlayTime(0);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn02() {
		int expected = 1;
		placeNote(1);
		player.setPlayTime(1);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn03() {
		int expected = 2;
		placeNote(2);
		player.setPlayTime(2);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn04() {
		int expected = 5;
		placeNote(5);
		player.setPlayTime(5);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn05() {
		int expected = 6;
		placeNote(6);
		player.setPlayTime(6);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn06() {
		int expected = 100;
		placeNote(100);
		player.setPlayTime(100);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn07() {
		int expected = 500;
		placeNote(500);
		player.setPlayTime(500);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn08() {
		int expected = 1000;
		placeNote(1000);
		player.setPlayTime(1000);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}

	/**
	 * Test if CloudMidiPlayer sets and returns a column correctly
	 */
	@Test
	public void testSetPlayTimeAndPlaybackBarColumn09() {
		int expected = 10000;
		placeNote(10000);
		player.setPlayTime(10000);
		int actual = player.playbackBarColumn();
		assertEquals(expected, actual);
	}
	
	/**
	 * Helper method to add notes to the grid for testSetPlayTimeAndPlaybackBarColumn
	 * 
	 * @param column, integer to represent the starting position of a note
	 */
	private void placeNote(int column) {
		Sequence seq = player.getSequence();
		CloudMidiPlayer.SequenceInst inst = player.getInstruments()[0];
		int pitch = 0;
		int stopPos = column + 4;
		CloudMidiPlayer.addNote(seq, inst, pitch, column, stopPos);
	}
	
	/**
	 * Test whether CloudMidiPlayer returns the basic Sequence object correctly 
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testBasicSequence01() throws InvalidMidiDataException {
		Sequence actual = CloudMidiPlayer.basicSequence();
		Sequence expected = new Sequence(Sequence.PPQ, (int) CloudMidiPlayer.TICKSPERFRAME);
		expected.createTrack();
		for (CloudMidiPlayer.SequenceInst inst : CloudMidiPlayer.SequenceInst.values())
			setInstrument(expected, inst);
		
		Track[] t1 = actual.getTracks();
		Track[] t2 = expected.getTracks();
		assertTrue(t1.length == t2.length);
		assertTrue(t1.length == 1);
		assertTrue(t1[0].size() == t2[0].size());
	}
	
	/**
	 * Test whether CloudMidiPlayer returns the basic Sequence object correctly 
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testBasicSequence02() throws InvalidMidiDataException {
		Sequence actual = CloudMidiPlayer.basicSequence();
		Sequence expected = new Sequence(Sequence.PPQ, (int) CloudMidiPlayer.TICKSPERFRAME);
		expected.createTrack();
		for (CloudMidiPlayer.SequenceInst inst : CloudMidiPlayer.SequenceInst.values())
			setInstrument(expected, inst);
		
		Track track1 = actual.getTracks()[0];
		Track track2 = expected.getTracks()[0];
		for (int j = 0; j < track1.size(); j++) {
			MidiEvent m1 = track1.get(j);
			MidiEvent m2 = track2.get(j);
			assertTrue(m1.getTick() == m2.getTick());
		}
	}
	
	/**
	 * Test whether CloudMidiPlayer returns the basic Sequence object correctly 
	 * 
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testBasicSequence03() throws InvalidMidiDataException {
		Sequence actual = CloudMidiPlayer.basicSequence();
		Sequence expected = new Sequence(Sequence.PPQ, (int) CloudMidiPlayer.TICKSPERFRAME);
		expected.createTrack();
		for (CloudMidiPlayer.SequenceInst inst : CloudMidiPlayer.SequenceInst.values())
			setInstrument(expected, inst);
		
		Track track1 = actual.getTracks()[0];
		Track track2 = expected.getTracks()[0];
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
	 * Helping method for testBasicSequence to set instruments into a sequence
	 * 
	 * @param s, sequence object
	 * @param inst, SequenceInst object
	 * @throws InvalidMidiDataException
	 */
	private void setInstrument(Sequence s, CloudMidiPlayer.SequenceInst inst) throws InvalidMidiDataException {
		ShortMessage m = new ShortMessage();
		m.setMessage(ShortMessage.PROGRAM_CHANGE, inst.value, CloudMidiPlayer.INSTRUMENTS[inst.value], 0);
		s.getTracks()[0].add(new MidiEvent(m, 0));
	}

}
