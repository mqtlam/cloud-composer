import javax.sound.midi.*;

public class cloudMidiPlayer {
	private static final int[] scale = {60, 62, 64, 67, 69};
	private float ticksPerSecond;
	private Sequencer seq;
	private Synthesizer synth;
	private Sequence[][] notes;
	private Sequence song;
	
	private enum SequenceInst {
		PIANO(0), GUITAR(1), DRUM(2), TRUMPET(3), VIOLIN(4);
		
		final int value;
		SequenceInst(int value) {
			this.value = value;
		}
	}
	
	public cloudMidiPlayer() throws Exception {
		loadMidiSystem();
		generateNotes();
	}
	
	public void setTempo(float bpm) throws InvalidMidiDataException {
		seq.setTempoInBPM(bpm);
		ticksPerSecond = 1800 * bpm;
		generateNotes();
	}
	
	private int ticksPerFrame() {
		return (int) ticksPerSecond * 30;
	}
	
	private void addNote(Sequence s, SequenceInst inst, 
						 int pitch, int startPos, int stopPos) 
						 throws InvalidMidiDataException 
	{
		Track t = s.getTracks()[0];
		ShortMessage m = new ShortMessage();
		m.setMessage(ShortMessage.NOTE_ON, 0, pitch / 5 * 12 + scale[inst.value], 100);
		t.add(new MidiEvent(m, startPos));
		
		ShortMessage m2 = new ShortMessage();
		m2.setMessage(ShortMessage.NOTE_OFF, 0, pitch / 5 * 12 + scale[inst.value], 100);
		t.add(new MidiEvent(m, stopPos));
	}
	
	private void generateNotes() throws InvalidMidiDataException 
	{
		int ticksPerFrame = (int) ticksPerSecond*30;
		for (SequenceInst inst : SequenceInst.values()) {
			for (int i = 0; i < 10; i++) {
				Sequence s = new Sequence(Sequence.SMPTE_30, ticksPerFrame);
				
			}
		}
		
	}
	

	private void loadMidiSystem() throws Exception {
		seq = MidiSystem.getSequencer();
		synth = MidiSystem.getSynthesizer();
		if (seq == null) {
			// Error -- sequencer device is not supported.
		    // Inform user and return...
		    throw new Exception("Sequencer device not supported.");
		} else if (synth == null) {
			throw new Exception("Synthesizer device not supported.");
		} else {
		    // Acquire resources and make operational.
		    seq.open();
		    synth.open();
		}
	}
	
	public void play() {
		
	}
	
}
