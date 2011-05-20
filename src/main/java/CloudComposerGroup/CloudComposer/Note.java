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
	 * Returns true if this and another note object is the same or equal.
	 * Otherwise, returns false.
	 * 
	 * @param other, note object for equality
	 * @return true if this.length == other.length && this.instrument == other.instrument && this.pitch == other.pitch
	 *         false, otherwise.
	 */
	public boolean equals(Note other) {
		return (this.length == other.length && this.instrument == other.instrument && this.pitch == other.pitch);
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
