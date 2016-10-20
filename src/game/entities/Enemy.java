package game.entities;

import game.grid.Grid;

public abstract class Enemy extends Entity 
{
	protected int hp, maxHp;
	protected int power;
	protected double speed, xVel, yVel, targetX, targetY;
	
	public Enemy(Grid grid, double x, double y, int width, int height, Element element, int hp) {
		super(grid, x, y, width, height, element);
		this.hp = hp;
		maxHp = hp;
		power = 1;
		targetX = x;
		targetY = y;
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
	
	public boolean reachedDestination(double targetX, double targetY) {
		return Math.hypot(x - targetX, y - targetY) <= 15;
	}
	
	public double getXVel() { return xVel; }
	public double getYVel() { return yVel; }
	public void setXYVel(double targetX, double targetY) {
		double absDist = Math.hypot(x - targetX, y - targetY);
		xVel = (targetX - x)*speed / absDist;
		yVel = (targetY - y)*speed / absDist;
	}
}