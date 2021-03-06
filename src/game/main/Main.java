package game.main;

public class Main 
{
	public static void main(String[] args) {
		GameEngine gm = new GameEngine();
		
		gm.buildWindow();
		gm.addPlayer();
		gm.addSampleEnemies();

		long t1 = 0L;
		long t2 = System.nanoTime();
		while(true) {
			t1 = t2;
			t2 = System.nanoTime();
			double dT = ((double) (t2-t1))/1000000000;
			//System.out.println(1/dT);
			if(dT > 0.15)
				dT = 0.15;

			gm.update(dT);
			gm.render();
		}
	}
}
