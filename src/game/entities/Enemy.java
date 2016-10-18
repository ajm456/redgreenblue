package game.entities;

import java.awt.Point;

import game.grid.Grid;

public abstract class Enemy extends Entity 
{
	protected int hp, maxHp;
	protected int power;
	protected double speed, xVel, yVel;
	protected Point destination;
	
	public Enemy(Grid grid, double x, double y, int width, int height, Element element, int hp) {
		super(grid, x, y, width, height, element);
		this.hp = hp;
		maxHp = hp;
		power = 1;
		destination = new Point((int)x, (int)y);
	}
	
	public int getHp() { return hp; }
	public void modHp(int modifier) {
		if(hp + modifier <= maxHp) {
			hp += modifier; 
		} else {
			hp = maxHp;
		}
	}
	public void incrementPower() { power++; }
	
	protected boolean reachedDestination(Point destination) {
		return Math.hypot(x - destination.getX(), y - destination.getY()) <= 15;
	}
	
	protected void setXYVel(Point destination) {
		float absDist = (float) Math.hypot(x - destination.getX(), y - destination.getY());
		xVel = (destination.getX() - x)*speed / absDist;
		yVel = (destination.getY() - y)*speed / absDist;
	}
}