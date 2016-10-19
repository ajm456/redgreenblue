package game.main;

public class Main 
{
	public static void main(String[] args) {
		GameManager gm = new GameManager();

		gm.buildWindow();
		gm.addPlayer();
		gm.addSampleEnemies();
		gm.setUpTrees();

		long t1 = 0L;
		long t2 = System.nanoTime();
		while(true) {
			t1 = t2;
			t2 = System.nanoTime();
			double dT = ((double) (t2-t1))/1000000000;

			gm.update(dT);
			gm.render();
			
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(1/dT);
		}
	}
}
