import javax.sound.midi.*;

public class cloudMidiPlayer 
{
	private static final int[] SCALE = {60, 62, 64, 67, 69};
	private static final int INSTRUMENTS = 5;
	private static final int SCALENOTES = 5;
	private static final int OCTAVES = 2;
	
	private float ticksPerSecond;
	private Sequencer seq;
	private Synthesizer synth;
	private Sequence[][] noteSequences;
	private Sequence song;
	
	private enum SequenceInst 
	{
		PIANO(0), GUITAR(1), DRUM(2), TRUMPET(3), VIOLIN(4);
		
		final int value;
		SequenceInst(int value) {
			this.value = value;
		}
	}
	
	public cloudMidiPlayer() throws Exception 
	{
		loadMidiSystem();
		generateNotes();
		noteSequences = new Sequence[INSTRUMENTS][SCALENOTES * OCTAVES];
		generateNotes();
	}
	
	public void setTempo(float bpm) throws InvalidMidiDataException 
	{
		seq.setTempoInBPM(bpm);
		ticksPerSecond = 1800 * bpm;
		generateNotes();
	}
	
	public void play() 
	{
		seq.start();
	}
	
	public void pause() 
	{
		seq.stop();
	}
	
	public void stop() 
	{
		seq.stop();
		seq.setTickPosition(0);
	}
	
	
// PRIVATE METHODS
	
	private void loadMidiSystem() throws Exception 
	{
		seq = MidiSystem.getSequencer();
		synth = MidiSystem.getSynthesizer();
		
		if (seq == null) {
		    throw new Exception("Sequencer device not supported.");
		} else if (synth == null) {
			throw new Exception("Synthesizer device not supported.");
		} else {
		    // Acquire resources and make operational.
		    seq.open();
		    synth.open();
		}
	}
	

	private void addNote(Sequence s, SequenceInst inst, 
						 int pitch, int startPos, int stopPos) 
						 throws InvalidMidiDataException 
	{
		Track t = s.getTracks()[0];
		ShortMessage m = new ShortMessage();
		m.setMessage(ShortMessage.NOTE_ON, 0, pitch / SCALENOTES * 12 + SCALE[inst.value], 100);
		t.add(new MidiEvent(m, startPos));
		
		ShortMessage m2 = new ShortMessage();
		m2.setMessage(ShortMessage.NOTE_OFF, 0, pitch / SCALENOTES * 12 + SCALE[inst.value], 100);
		t.add(new MidiEvent(m, stopPos));
	}
	
	private void generateNotes() throws InvalidMidiDataException 
	{
		int ticksPerFrame = (int) ticksPerSecond * 30;
		for (SequenceInst inst : SequenceInst.values()) {
			for (int pitch = 0; pitch < SCALENOTES * OCTAVES; pitch++) {
				Sequence s = new Sequence(Sequence.SMPTE_30, ticksPerFrame);
				addNote(s, inst, pitch, 0, (int) ticksPerSecond / 4);
				noteSequences[inst.value][pitch] = s;
			}
		}
		
	}
}
