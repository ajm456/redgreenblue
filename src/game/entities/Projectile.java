package game.entities;

import game.grid.Grid;

public class Projectile extends Entity 
{
	private double speed;
	private int damage;
	private Entity parent;
	
	public Projectile(Grid grid, double x, double y, int width, int height, Element element, int speed, int damage, Entity parent) {
		super(grid, x, y, width, height, element);
		this.speed = speed;
		this.damage = damage;
		this.parent = parent;
	}

	@Override
	public boolean move(double dT) {
		return grid.move(this, x, y - speed*dT);
	}
	
	public boolean collidedWith(Entity e) {
		return getRectangle().intersects(e.getRectangle());
	}
	
	public int getDamage() { return damage; }
	public Entity getParent() { return parent; }
	
	@Override
	public String toString() {
		return element.toString() + " projectile at location (" + (int)x + "," + (int)y + ")";
	}
}
