package game.enemy_types;

import java.awt.Point;

import game.entities.Element;
import game.entities.Enemy;
import game.frame.Window;
import game.grid.Grid;

public class SampleEnemy extends Enemy 
{	
	public SampleEnemy(Grid grid, double x, double y, int width, int height, Element element, int hp) {
		super(grid, x, y, width, height, element, hp);
		speed = 4;
	}

	@Override
	public boolean move(double dT) {
		
		Point destination = new Point(Window.WIDTH/2, Window.HEIGHT/2);
		setXYVel(destination);
		return true;
	}

}
