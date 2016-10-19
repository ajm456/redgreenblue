package game.entities;

import java.awt.event.KeyEvent;

import game.frame.Window;
import game.grid.Grid;

public class Player extends Entity 
{
	private float speed;
	private static final float AXIS_SPEED = 5;
	private static final float DIAG_SPEED = (float) Math.sqrt(Math.pow(AXIS_SPEED, 2)/2);
	private boolean upPressed, downPressed, leftPressed, rightPressed, colorChanged;
	
	public Player(Grid grid, int x, int y, int width, int height, Element element) {
		super(grid, x, y, width, height, element);
		speed = 0;
		upPressed = false;
		downPressed = false;
		leftPressed = false;
		rightPressed = false;
		colorChanged = false;
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SHIFT && !colorChanged) {
			element = element.getNext();
			color = element.toColor();
			colorChanged = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP && !upPressed) {
			upPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN && !downPressed) {
			downPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT && !leftPressed) {
			leftPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && !rightPressed) {
			rightPressed = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SHIFT && colorChanged) {
			colorChanged = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP && upPressed) {
			upPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN && downPressed) {
			downPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT && leftPressed) {
			leftPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && rightPressed) {
			rightPressed = false;
		}
	}
	
	@Override
	public boolean move(double dT) {
		if	(
				upPressed && leftPressed 	||
				upPressed && rightPressed 	||
				downPressed && leftPressed 	||
				downPressed && rightPressed	)	{
			speed = DIAG_SPEED;
		} else {
			speed = AXIS_SPEED;
		}
		if(upPressed && !downPressed && y-speed*dT >= 0) {
			grid.move(this, x, y - speed*dT);
		} else if(downPressed && !upPressed && y+height+speed*dT <= Window.HEIGHT) {
			grid.move(this, x, y + speed*dT);
		}
		if(leftPressed && !rightPressed && x-speed*dT >= 0) {
			grid.move(this, x - speed*dT, y);
		} else if(rightPressed && !leftPressed && x+width+speed*dT <= Window.WIDTH) {
			grid.move(this, x + speed*dT, y);
		}
		
		return true;
	}
	
	public int getWidth() { return width; }
	
	@Override
	public String toString() {
		return element.toString() + " player at location (" + (int)x + "," + (int)y + ")";
	}
}
