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
			t2 = t1;
			t2 = System.nanoTime();
			double dT = (double) (t2-t1);
			
			gm.update(dT);
			gm.render();
			
			try {
				Thread.sleep(128000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
