

public class Test {
	public static void main(String[] args) throws Exception {
		CloudAppletController c = new CloudAppletController();
		c.init();
		//for (int i = 0; i < 5; i++) {
		//	for (int j = 0; j < 10; j++) {
		int[] i = {0, 4, 0, 24};
		c.addNote(i);
		int[] j = {1, 7, 0, 8};
				//Thread.sleep(1000);
		c.addNote(j);
		c.removeNote(i);
		c.play();
		//	}
		//}
		System.out.println();
	}
}
