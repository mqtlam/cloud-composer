

public class Test {
	public static void main(String[] args) throws Exception {
		CloudAppletController c = new CloudAppletController();
		c.init();
		//for (int i = 0; i < 5; i++) {
		//	for (int j = 0; j < 10; j++) {
		c.playNote(0, 5);
		Thread.sleep(1000);
		c.playNote(0, 5);
		Thread.sleep(1000);
		c.playNote(0, 5);
				//Thread.sleep(1000);
		//	}
		//}
		System.out.println();
	}
}
