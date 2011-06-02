import javax.sound.midi.Instrument;

import com.sun.swing.internal.plaf.synth.resources.synth;

import CloudComposerGroup.CloudComposer.*;
import java.util.*;


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
		Random r = new Random();
		int nextPlace = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				int[] k = {1, j, nextPlace, nextPlace + 8 - 2*i};
				nextPlace += 8 - 2 * i + 1;
				c.addNote(k);
			}
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				if (i == 3 || i == 4 && j < 5) {
					int[] k = {1, 9-j, nextPlace, nextPlace};
					c.addNote(k);
				} else if (i == 4 && j > 4) {
					int[] k = {1, j-1, nextPlace, nextPlace};
					c.addNote(k);
				}
				if (i != 4 || i == 4 && j < 9) {
					int[] k = {1, j, nextPlace, nextPlace};
					nextPlace += 1;
					c.addNote(k);
				} else {
					int[] k = {1, j, nextPlace, nextPlace+12};
					nextPlace += 13;
					c.addNote(k);
				}
			}
		}
		int[] k = {1, 4, nextPlace, nextPlace+16};
		c.addNote(k);
		for (int i = 0; i < 16; i++) {
			int[] l = {3, 0, nextPlace + 4 * i, nextPlace + 4 * i + 4};
			c.addNote(l);
		}
		nextPlace += 16;
		int nextStop = nextPlace + 40;
		for (int i = 0; i < 5; i++) {
			int[] l = {0, i * 2 + 1, nextPlace, nextStop};
			c.addNote(l);
			if (i != 4) {
				int[] m = {0, i * 2, nextPlace + 4, nextPlace + 12};
				c.addNote(m);
			}
			nextPlace += 8;
		}

		//c.download("/Users/crombrodin/randomsong.midi");
		//int[] j = {2, 6, 7, 10};
				//Thread.sleep(1000);
		//System.out.println(c.currentSongPosition());
		//c.addNote(j);
		//c.playNote(0, 1);
		//Thread.sleep(500);
		//c.setSongPosition(5);
		c.play();
		//c.pause();
		//System.out.println(c.currentSongPosition());
		
		//c.setTempo(200);
		//System.out.println(c.getTempo());
		//c.play();
		//Thread.sleep(1000);
		//c.stop();
		//System.out.println(c.getTempo());
		//int thing = c.player.getLastColumn();
		//c.play();
		//System.out.println(c.getTempo());
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
		//System.out.println();
	}
}