package game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.grid.Grid;

public abstract class Entity 
{
	protected Grid grid;
	protected double x, y;
	protected int width, height, cellX, cellY;
	protected Color color;
	protected Element element;
	private Entity prev, next;
	
	public Entity(Grid grid, double x, double y, int width, int height, Element element) {
		this.grid = grid;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.element = element;
		color = element.toColor();
		grid.add(this);
	}
	
	public double getX() { return x; }
	public void setX(double x) { this.x = x; }
	public double getY() { return y; }
	public void setY(double y) { this.y = y; }
	public int getCellX() { return cellX; }
	public void setCellX(int x) { cellX = x; }
	public int getCellY() { return cellY; }
	public void setCellY(int y) { cellY = y; }
	public Color getColor() { return color; }
	public Element getElement() { return element; }
	public Rectangle getRectangle() { return new Rectangle((int)x, (int)y, width, height); }
	public Graphics drawEntity(Graphics g) {
		Color tempCol = g.getColor();
		g.setColor(color);
		g.fillRect((int)x, (int)y, width, height);
		g.setColor(tempCol);
		return g;
	}
	public Entity getPrev() { return prev; }
	public void setPrev(Entity prev) { this.prev = prev; }
	public Entity getNext() { return next; }
	public void setNext(Entity next) { this.next = next; }
	
	public abstract boolean move(double dT);
}