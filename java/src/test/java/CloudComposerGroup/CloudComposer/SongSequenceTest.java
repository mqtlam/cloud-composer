import CloudComposerGroup.CloudComposer.*;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class SongSequenceTest extends TestCase {

	/** SongSequence object **/
	private SongSequence s;
	/**	NoteGrid object		**/
	private NoteGrid grid;
	/** list of notes  **/
	private List<Note> notes;
	/** list of integer representing columns of grid **/
	private List<Integer> columns;
	
	@Before
	public void setUp() throws Exception {
		grid = new NoteGrid();
		s = new SongSequence(grid);
		notes = new ArrayList<Note>();
		columns = new ArrayList<Integer>();
	}
	
	/**
	 * Test whether SongSequence returns a Sequence object correctly
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testGetSequence01() throws InvalidMidiDataException {
		grid.add(new Note(1, 0, 0), 0);
		Sequence actual = s.getSequence();
		
		notes.add(new Note(1, 0, 0));
		columns.add(0);
		Sequence expected = createSequence(notes, columns);
		
		Track[] t1 = actual.getTracks();
		Track[] t2 = expected.getTracks();
		assertTrue(t1.length == t2.length);
		assertTrue(t1.length == 1);
		assertTrue(t1[0].size() == t2[0].size());
	}
	
	/**
	 * Test whether SongSequence returns a Sequence object correctly
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testGetSequence02() throws InvalidMidiDataException {
		grid.add(new Note(1, 0, 0), 0);
		grid.add(new Note(1, 1, 1), 0);
		Sequence actual = s.getSequence();
		
		notes.add(new Note(1, 0, 0));
		notes.add(new Note(1, 1, 1));
		columns.add(0);
		columns.add(0);
		Sequence expected = createSequence(notes, columns);
		
		Track[] t1 = actual.getTracks();
		Track[] t2 = expected.getTracks();
		assertTrue(t1.length == t2.length);
		assertTrue(t1.length == 1);
		assertTrue(t1[0].size() == t2[0].size());
	}
	
	/**
	 * Test whether SongSequence returns a Sequence object correctly
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testGetSequence03() throws InvalidMidiDataException {
		grid.add(new Note(1, 0, 0), 0);
		grid.add(new Note(1, 1, 1), 0);
		grid.add(new Note(3, 2, 2), 0);
		Sequence actual = s.getSequence();
		
		notes.add(new Note(1, 0, 0));
		notes.add(new Note(1, 1, 1));
		notes.add(new Note(3, 2, 2));
		columns.add(0);
		columns.add(0);
		columns.add(0);
		Sequence expected = createSequence(notes, columns);
		
		Track[] t1 = actual.getTracks();
		Track[] t2 = expected.getTracks();
		assertTrue(t1.length == t2.length);
		assertTrue(t1.length == 1);
		assertTrue(t1[0].size() == t2[0].size());
	}
	
	/**
	 * Test whether SongSequence returns a Sequence object correctly
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testGetSequence04() throws InvalidMidiDataException {
		grid.add(new Note(1, 0, 0), 0);
		grid.add(new Note(1, 1, 1), 0);
		grid.add(new Note(3, 2, 2), 0);
		grid.add(new Note(3, 2, 2), 1);
		grid.add(new Note(3, 0, 2), 1);
		grid.add(new Note(3, 4, 9), 1);
		grid.add(new Note(3, 3, 8), 5);
		grid.add(new Note(3, 2, 5), 5);
		grid.add(new Note(3, 3, 6), 5);
		grid.add(new Note(1, 0, 0), 6);
		grid.add(new Note(3, 2, 1), 6);
		grid.add(new Note(3, 3, 2), 6);
		grid.add(new Note(10, 4, 5), 6);
		Sequence actual = s.getSequence();
		
		notes.add(new Note(1, 0, 0));
		notes.add(new Note(1, 1, 1));
		notes.add(new Note(3, 2, 2));
		notes.add(new Note(3, 2, 2));
		notes.add(new Note(3, 0, 2));
		notes.add(new Note(3, 4, 9));
		notes.add(new Note(3, 3, 8));
		notes.add(new Note(3, 2, 5));
		notes.add(new Note(3, 3, 6));
		notes.add(new Note(1, 0, 0));
		notes.add(new Note(3, 2, 1));
		notes.add(new Note(3, 3, 2));
		notes.add(new Note(10, 4, 5));
		columns.add(0);
		columns.add(0);
		columns.add(0);
		columns.add(1);
		columns.add(1);
		columns.add(1);
		columns.add(5);
		columns.add(5);
		columns.add(5);
		columns.add(6);
		columns.add(6);
		columns.add(6);
		columns.add(6);
		Sequence expected = createSequence(notes, columns);
	
		Track[] t1 = actual.getTracks();
		Track[] t2 = expected.getTracks();
		assertTrue(t1.length == t2.length);
		assertTrue(t1.length == 1);
		assertTrue(t1[0].size() == t2[0].size());
	}
	
	/**
	 * Test whether SongSequence returns a Sequence object correctly
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testGetSequence05() throws InvalidMidiDataException {
		grid.add(new Note(1, 0, 0), 0);
		Sequence actual = s.getSequence();
		
		notes.add(new Note(1, 0, 0));
		columns.add(0);
		Sequence expected = createSequence(notes, columns);
		
		Track track1 = actual.getTracks()[0];
		Track track2 = expected.getTracks()[0];
		for (int j = 0; j < track1.size(); j++) {
			MidiEvent m1 = track1.get(j);
			MidiEvent m2 = track2.get(j);
			assertTrue(m1.getTick() == m2.getTick());
		}
	}
	
	/**
	 * Test whether SongSequence returns a Sequence object correctly
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testGetSequence06() throws InvalidMidiDataException {
		grid.add(new Note(1, 0, 0), 0);
		grid.add(new Note(1, 1, 1), 0);
		Sequence actual = s.getSequence();
		
		notes.add(new Note(1, 0, 0));
		notes.add(new Note(1, 1, 1));
		columns.add(0);
		columns.add(0);
		Sequence expected = createSequence(notes, columns);
		
		Track track1 = actual.getTracks()[0];
		Track track2 = expected.getTracks()[0];
		for (int j = 0; j < track1.size(); j++) {
			MidiEvent m1 = track1.get(j);
			MidiEvent m2 = track2.get(j);
			assertTrue(m1.getTick() == m2.getTick());
		}
	}
	
	/**
	 * Test whether SongSequence returns a Sequence object correctly
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testGetSequence07() throws InvalidMidiDataException {
		grid.add(new Note(1, 0, 0), 0);
		grid.add(new Note(1, 1, 1), 0);
		grid.add(new Note(3, 2, 2), 0);
		Sequence actual = s.getSequence();
		
		notes.add(new Note(1, 0, 0));
		notes.add(new Note(1, 1, 1));
		notes.add(new Note(3, 2, 2));
		columns.add(0);
		columns.add(0);
		columns.add(0);
		Sequence expected = createSequence(notes, columns);
		
		Track track1 = actual.getTracks()[0];
		Track track2 = expected.getTracks()[0];
		for (int j = 0; j < track1.size(); j++) {
			MidiEvent m1 = track1.get(j);
			MidiEvent m2 = track2.get(j);
			assertTrue(m1.getTick() == m2.getTick());
		}
	}
	
	/**
	 * Test whether SongSequence returns a Sequence object correctly
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testGetSequence08() throws InvalidMidiDataException {
		grid.add(new Note(1, 0, 0), 0);
		grid.add(new Note(1, 1, 1), 0);
		grid.add(new Note(3, 2, 2), 0);
		grid.add(new Note(3, 2, 2), 1);
		grid.add(new Note(3, 0, 2), 1);
		grid.add(new Note(3, 4, 9), 1);
		grid.add(new Note(3, 3, 8), 5);
		grid.add(new Note(3, 2, 5), 5);
		grid.add(new Note(3, 3, 6), 5);
		grid.add(new Note(1, 0, 0), 6);
		grid.add(new Note(3, 2, 1), 6);
		grid.add(new Note(3, 3, 2), 6);
		grid.add(new Note(10, 4, 5), 6);
		Sequence actual = s.getSequence();
		
		notes.add(new Note(1, 0, 0));
		notes.add(new Note(1, 1, 1));
		notes.add(new Note(3, 2, 2));
		notes.add(new Note(3, 2, 2));
		notes.add(new Note(3, 0, 2));
		notes.add(new Note(3, 4, 9));
		notes.add(new Note(3, 3, 8));
		notes.add(new Note(3, 2, 5));
		notes.add(new Note(3, 3, 6));
		notes.add(new Note(1, 0, 0));
		notes.add(new Note(3, 2, 1));
		notes.add(new Note(3, 3, 2));
		notes.add(new Note(10, 4, 5));
		columns.add(0);
		columns.add(0);
		columns.add(0);
		columns.add(1);
		columns.add(1);
		columns.add(1);
		columns.add(5);
		columns.add(5);
		columns.add(5);
		columns.add(6);
		columns.add(6);
		columns.add(6);
		columns.add(6);
		Sequence expected = createSequence(notes, columns);
	
		Track track1 = actual.getTracks()[0];
		Track track2 = expected.getTracks()[0];
		for (int j = 0; j < track1.size(); j++) {
			MidiEvent m1 = track1.get(j);
			MidiEvent m2 = track2.get(j);
			assertTrue(m1.getTick() == m2.getTick());
		}
	}

	/**
	 * Test whether SongSequence returns a Sequence object correctly
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testGetSequence09() throws InvalidMidiDataException {
		grid.add(new Note(1, 0, 0), 0);
		Sequence actual = s.getSequence();
		
		notes.add(new Note(1, 0, 0));
		columns.add(0);
		Sequence expected = createSequence(notes, columns);
		
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
	 * Test whether SongSequence returns a Sequence object correctly
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testGetSequence10() throws InvalidMidiDataException {
		grid.add(new Note(1, 0, 0), 0);
		grid.add(new Note(1, 1, 1), 0);
		Sequence actual = s.getSequence();
		
		notes.add(new Note(1, 0, 0));
		notes.add(new Note(1, 1, 1));
		columns.add(0);
		columns.add(0);
		Sequence expected = createSequence(notes, columns);
		
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
	 * Test whether SongSequence returns a Sequence object correctly
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testGetSequence11() throws InvalidMidiDataException {
		grid.add(new Note(1, 0, 0), 0);
		grid.add(new Note(1, 1, 1), 0);
		grid.add(new Note(3, 2, 2), 0);
		Sequence actual = s.getSequence();
		
		notes.add(new Note(1, 0, 0));
		notes.add(new Note(1, 1, 1));
		notes.add(new Note(3, 2, 2));
		columns.add(0);
		columns.add(0);
		columns.add(0);
		Sequence expected = createSequence(notes, columns);
		
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
	 * Test whether SongSequence returns a Sequence object correctly
	 * 
	 * @throws InvalidMidiDataException 
	 */
	@Test
	public void testGetSequence12() throws InvalidMidiDataException {
		grid.add(new Note(1, 0, 0), 0);
		grid.add(new Note(1, 1, 1), 0);
		grid.add(new Note(3, 2, 2), 0);
		grid.add(new Note(3, 2, 2), 1);
		grid.add(new Note(3, 0, 2), 1);
		grid.add(new Note(3, 4, 9), 1);
		grid.add(new Note(3, 3, 8), 5);
		grid.add(new Note(3, 2, 5), 5);
		grid.add(new Note(3, 3, 6), 5);
		grid.add(new Note(1, 0, 0), 6);
		grid.add(new Note(3, 2, 1), 6);
		grid.add(new Note(3, 3, 2), 6);
		grid.add(new Note(10, 4, 5), 6);
		Sequence actual = s.getSequence();
		
		notes.add(new Note(1, 0, 0));
		notes.add(new Note(1, 1, 1));
		notes.add(new Note(3, 2, 2));
		notes.add(new Note(3, 2, 2));
		notes.add(new Note(3, 0, 2));
		notes.add(new Note(3, 4, 9));
		notes.add(new Note(3, 3, 8));
		notes.add(new Note(3, 2, 5));
		notes.add(new Note(3, 3, 6));
		notes.add(new Note(1, 0, 0));
		notes.add(new Note(3, 2, 1));
		notes.add(new Note(3, 3, 2));
		notes.add(new Note(10, 4, 5));
		columns.add(0);
		columns.add(0);
		columns.add(0);
		columns.add(1);
		columns.add(1);
		columns.add(1);
		columns.add(5);
		columns.add(5);
		columns.add(5);
		columns.add(6);
		columns.add(6);
		columns.add(6);
		columns.add(6);
		Sequence expected = createSequence(notes, columns);
	
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
	 * Helping method to generate Sequence object
	 * 
	 * @param notes, list of notes
	 * @param column, list of integers representing columns of grid
	 * @return Sequence object
	 */
	private Sequence createSequence(List<Note> notes, List<Integer> column) {
		Sequence sequence = CloudMidiPlayer.basicSequence();
		for (int i = 0; i < notes.size(); i++) 
			CloudMidiPlayer.addNote(sequence, CloudMidiPlayer.SequenceInst.values()[notes.get(i).instrument], notes.get(i).pitch, 
									column.get(i), column.get(i) + notes.get(i).length);
		return sequence;
	}
	
}
