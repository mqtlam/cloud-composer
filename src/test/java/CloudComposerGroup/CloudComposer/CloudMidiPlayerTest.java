import CloudComposerGroup.CloudComposer.*;

import static org.junit.Assert.assertEquals;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import org.junit.Before;
import org.junit.Test;


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
	 * Test if CloudMidiPlayer sets and returns a sequence correctly or not 
	 * 
	 * @throws InvalidMidiDataException
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
	 * @throws InvalidMidiDataException
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
	 * @throws InvalidMidiDataException
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
	 * @throws InvalidMidiDataException
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
	 * @throws InvalidMidiDataException
	 */
	@Test
	public void testSetGetSequence05() throws InvalidMidiDataException {
		Sequence expected = new Sequence(Sequence.SMPTE_30DROP, 0);
		player.setSequence(expected);
		Sequence actual = player.getSequence();
		assertEquals(expected, actual);
	}


}
