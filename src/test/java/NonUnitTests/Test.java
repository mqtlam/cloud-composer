import CloudComposerGroup.CloudComposer.*;


public class Test {
	public static void main(String[] args) throws Exception {
		CloudAppletController c = new CloudAppletController();
		c.init();
		//for (int i = 0; i < 5; i++) {
		//	for (int j = 0; j < 10; j++) {
		int[] i = {0, 4, 0, 4};
		c.addNote(i);
		int[] k = {0, 4, 3, 20};
		c.addNote(k);
		int[] j = {1, 6, 0, 4};
				//Thread.sleep(1000);
		c.addNote(j);
		c.play();
		c.stop();
		int thing = c.player.getLastColumn();
		c.play();
		/*while (c.player.seq.isRunning()) {
			Thread.sleep(250);
		}
		c.stop();
		c.setTempo(200);
		c.play();*/
		//	}
		//}
		System.out.println();
	}
}