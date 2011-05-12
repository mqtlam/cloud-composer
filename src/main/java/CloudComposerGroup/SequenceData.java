import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;


public class SequenceData {
	private NoteGrid grid;
	private Sequence sequence;
	
	private final int velocity = 100;
	
	public SequenceData(NoteGrid grid) throws InvalidMidiDataException {
		this.grid = grid;
		sequence = new Sequence(Sequence.SMPTE_30, 10);
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public Sequence getSequenceData() throws IOException {
		Track track = sequence.createTrack();
		
		
		
		
		
		
		return sequence;
	}
	
	/**
	 * 
	 * @param nKey, integer that represents pitch of note
	 * @param lTick, integer that represents longness of note
	 * @return
	 */
	private MidiEvent createNoteOnEvent(int nKey, long lTick) {
		return createNoteEvent(ShortMessage.NOTE_ON, nKey, velocity, lTick);
	}

	/**
	 * 
	 * @param nKey, integer that represents pitch of note
	 * @param lTick, integer that represents longness of note
	 * @return
	 */
	private MidiEvent createNoteOffEvent(int nKey, long lTick) {
		return createNoteEvent(ShortMessage.NOTE_OFF, nKey, 0, lTick);
	}

	/**
	 * 
	 * @param nCommand, integer that represents start or end of a song
	 * @param nKey, integer that represents pitch of note
	 * @param nVelocity, integer that represents loudness of sound
	 * @param lTick, integer that represents longness of note
	 * @return
	 */
	private MidiEvent createNoteEvent(int nCommand, int nKey, int nVelocity, long lTick) {
		ShortMessage message = new ShortMessage();
		try {
			message.setMessage(nCommand, 4, nKey, nVelocity);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return new MidiEvent(message, lTick);
	}
}
