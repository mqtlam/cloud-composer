package CloudComposerGroup;

/**
 * This class is for creating a Note object that
 * contains note data: length, instrument, and pitch
 */
public class Note {
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
	 */
	public Note(int length, int instrument, int pitch) {
		this.length = length;
		this.instrument = instrument;
		this.pitch = pitch;
	}
}
