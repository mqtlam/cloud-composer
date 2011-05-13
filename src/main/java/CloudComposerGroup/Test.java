

public class Test {
	public static void main(String[] args) throws Exception {
		CloudAppletController c = new CloudAppletController();
		c.init();
		//for (int i = 0; i < 5; i++) {
		//	for (int j = 0; j < 10; j++) {
		int[] i = {0, 4, 0, 4};
		c.addNote(i);
				//Thread.sleep(1000);
		//	}
		//}
		System.out.println();
	}
}
