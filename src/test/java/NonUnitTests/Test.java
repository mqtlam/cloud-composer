import javax.sound.midi.Instrument;

import com.sun.swing.internal.plaf.synth.resources.synth;

import CloudComposerGroup.CloudComposer.*;


public class Test {
	public static void main(String[] args) throws Exception {
		CloudAppletController c = new CloudAppletController();
		c.init();
		//for (int in = 0; in < inst.length; in++) {
			
		//}
		//Instrument[] inst = c.player.synth.getAvailableInstruments();
		//c.player.synth.remapInstrument(inst[101], inst[296]);
		//for (int i = 0; i < 5; i++) {
		//	for (int j = 0; j < 10; j++) {
		/*int[] i = {0, 4, 0, 4};
		c.addNote(i);
		int[] k = {0, 4, 3, 20};
		c.addNote(k);*/
		int[] j = {2, 6, 0, 4};
				//Thread.sleep(1000);
		c.addNote(j);
		c.setTempo(200);
		System.out.println(c.getTempo());
		c.play();
		Thread.sleep(1000);
		c.stop();
		System.out.println(c.getTempo());
		//int thing = c.player.getLastColumn();
		c.play();
		System.out.println(c.getTempo());
		/*while (c.player.seq.isRunning()) {
			Thread.sleep(250);
		}
		c.stop();
		c.setTempo(200);
		c.play();*/
		//	}
		//}
		
		//for (int in = 0; in < inst.length; in++) {
		//	System.out.println(in + " " + inst[in].getName());
		//}
		System.out.println();
	}
}