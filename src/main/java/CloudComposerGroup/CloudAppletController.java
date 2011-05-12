import java.applet.Applet;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;


public class CloudAppletController extends Applet { //implements ActionListener {
	private CloudMidiPlayer player;
	private SongSequence sequencer;
	private NoteGrid grid;
	
	public void init() {
		try {
			grid = new NoteGrid();
			player = new CloudMidiPlayer();
			sequencer = new SongSequence(grid, player.getTicksPerFrame(), player.getInstrumentCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/*@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}*/

}
