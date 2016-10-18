package game.main;

public class Main 
{
	public static void main(String[] args) {
		GameManager gm = new GameManager();
		
		gm.buildWindow();
		gm.addPlayer();
		gm.addSampleEnemies();
		gm.setUpTrees();
		while(true) {
			gm.update();
		}
	}
}
