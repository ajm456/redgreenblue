package game.entities;

import java.awt.event.KeyEvent;

import game.frame.Window;
import game.grid.Grid;

public class Player extends Entity 
{
	private double axisSpeed, diagSpeed;
	private boolean[] keys, atBounds;
	private int boolCount;
	private boolean colorChanged;
	
	public Player(Grid grid, double x, double y, int width, int height, Element element) {
		super(grid, x, y, width, height, element);
		axisSpeed = 300;
		xVel = axisSpeed;
		yVel = axisSpeed;
		diagSpeed = Math.sqrt(Math.pow(axisSpeed, 2)/2);
		keys = new boolean[] {false, false, false, false};
		atBounds = new boolean[] {false, false, false, false};
		boolCount = 0;
		colorChanged = false;
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SHIFT && !colorChanged) {
			element = element.getNext();
			color = element.toColor();
			colorChanged = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP && !keys[0]) {
			keys[0] = true;
			boolCount++;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN && !keys[1]) {
			keys[1] = true;
			boolCount++;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT && !keys[2]) {
			keys[2] = true;
			boolCount++;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && !keys[3]) {
			keys[3] = true;
			boolCount++;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SHIFT && colorChanged) {
			colorChanged = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP && keys[0]) {
			keys[0] = false;
			boolCount--;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN && keys[1]) {
			keys[1] = false;
			boolCount--;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT && keys[2]) {
			keys[2] = false;
			boolCount--;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && keys[3]) {
			keys[3] = false;
			boolCount--;
		}
	}
	
	@Override
	public boolean move(double dT) {
		
		if(boolCount == 1) {
			if(keys[0]) {
				if(!atBounds[0] && y - yVel*dT <= 0) {
					atBounds[0] = true;
					grid.move(this, x, 0);
				} else {
					
				}
				grid.move(this, x, y - yVel*dT);
			} else if(keys[1] && y + yVel*dT <= Window.HEIGHT - height) {
				grid.move(this, x, y + yVel*dT);
			} else if(keys[2] && x - xVel*dT >= 0) {
				grid.move(this, x - xVel*dT, y);
			} else if(keys[3] && x + xVel*dT <= Window.WIDTH - width) {
				grid.move(this, x + xVel*dT, y);
			}
		}
		
		
		
		
		
		
		
		
		
		
		return true;
	}
	
	public double getAxisSpeed() { return diagSpeed; }
	
	@Override
	public String toString() {
		return element.toString() + " player at location (" + (int)x + "," + (int)y + ")";
	}
}
