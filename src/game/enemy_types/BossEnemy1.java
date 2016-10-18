package game.enemy_types;

import game.entities.Element;
import game.entities.Enemy;
import game.grid.Grid;

public class BossEnemy1 extends Enemy 
{

	public BossEnemy1(Grid grid, float x, float y, int width, int height, Element element, int hp) {
		super(grid, x, y, width, height, element, hp);
	}

	@Override
	public boolean move() {
		return true;
	}
	
}
