

public class Test {
	public static void main(String[] args) throws Exception {
		CloudAppletController c = new CloudAppletController();
		c.init();
		//for (int i = 0; i < 5; i++) {
		//	for (int j = 0; j < 10; j++) {
		for (int i = 0; i < 10; i++) {
			int[] j = {0, i, 0, 24};
			c.addNote(j);
		}
		c.play();
		//	}
		//}
		System.out.println();
	}
}
