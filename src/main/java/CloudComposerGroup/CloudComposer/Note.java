package CloudComposerGroup.CloudComposer;


/**
 * This class is for creating a Note object that
 * contains note data: length, instrument, and pitch
 */
public class Note implements Comparable<Note> {
	/** to represent length of note **/
	public int length;
	/**	to represent a type of instrument **/
	public int instrument;
	/**	to represent pitch of note	**/
	public int pitch;
	
	/**
	 * Constructs a Note object
	 * 
	 * @param length, integer to represent length of note 
	 * @param instrument, integer to represent a type of instrument
	 * @param pitch, integer to represent pitch of note
	 * @requires length > 0 && 0 <= instrument <= 4 && 0 <= pitch <= 9 
	 */
	public Note(int length, int instrument, int pitch) {
		this.length = length;
		this.instrument = instrument;
		this.pitch = pitch;
	}

	/**
	 * Returns an integer by comparing with another Note object.
	 * Notes are in increasing order
	 * 
	 * @param n, another Note object to compare
	 * @requires n != null
	 */
	public int compareTo(Note n) {
		if (length != n.length)
			return n.length - length;
		else if (instrument != n.instrument)
			return n.instrument - instrument;
		else
			return n.pitch - pitch;
	}
}
