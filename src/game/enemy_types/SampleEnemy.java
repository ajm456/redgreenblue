package game.enemy_types;

import game.entities.Element;
import game.entities.Enemy;
import game.frame.Window;
import game.grid.Grid;

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

	@Override
	public void behave() {
		if(!hasTarget) {
			targetX = Math.random()*Window.WIDTH;
			targetY = Math.random()*Window.HEIGHT;
			setXYVel(targetX, targetY);
			hasTarget = true;
		}
		reachedDestination();
	}

}
