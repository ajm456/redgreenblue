package game.enemy_types;

import game.entities.Element;
import game.entities.Enemy;
import game.grid.Grid;
import game.util.Maths;

public class SampleEnemy extends Enemy 
{	
	public SampleEnemy(Grid grid, double x, double y, int width, int height, Element element, int hp) {
		super(grid, x, y, width, height, element, hp);
		speed = 200;
	}
	
	@Override
	public boolean move(double dT) {
		return grid.move(this, x + xVel*dT, y + yVel*dT);
	}

}
