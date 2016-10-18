package game.grid;

import game.entities.Entity;
import game.frame.Window;

/**
 * A partitioned grid representing the viewable area in game. Each cell
 * contains entities and is used to improve efficiency of collision
 * detection.
 */
public class Grid 
{
	public final static int NUM_CELLS_TOTAL = 100;
	/**
	 * Number of cells width-ways.
	 */
	public final static int NUM_OF_X_CELLS = 10;
	/**
	 * Number of cells length-ways.
	 */
	public final static int NUM_OF_Y_CELLS = 10;
	public final static int CELL_WIDTH = 60;
	public final static int CELL_HEIGHT = 70;
	private Entity[][] cells;
	private CollisionListener gm;
	
	/**
	 * Fills the Grid object with null entries.
	 * @param gm
	 */
	public Grid(CollisionListener gm) {
		this.gm = gm;
		cells = new Entity[10][10];
	}
	
	/**
	 * Adds an Entity to the correct cell based on its current
	 * position. If it is successfully added, true is returned - 
	 * if it cannot be added (i.e. outside of the viewable area)
	 * false is returned.
	 * 
	 * @param 	e	the Entity object to be added to the grid
	 * @return		true if the Entity object is added to a cell,
	 * 				false if not
	 */
	public boolean add(Entity e) {
		if(!Window.BOUNDS.contains(e.getRectangle())) {
			return false;
		}
		int cellX = (int) (e.getX() / CELL_WIDTH);
		int cellY = (int) (e.getY() / CELL_HEIGHT);
		
		e.setCellX(cellX);
		e.setCellY(cellY);
		e.setPrev(null);
		e.setNext(cells[cellX][cellY]);
		cells[cellX][cellY] = e;
		
		if(e.getNext() != null) {
			e.getNext().setPrev(e);
		}
		return true;
	}
	
	/**
	 * Iterates through the entire grid, calling {@link #handleCell(int, int) handleCell}
	 * on each cell.
	 */
	public void checkCollisions() {
		for(int x=0; x<10; x++) {
			for(int y=0; y<10; y++) {
				handleCell(x, y);
			}
		}
	}
	
	/**
	 * For every Entity in a cell at given coordinates, checks
	 * whether said Entity object is colliding with any others.
	 * Also checks collisions with said Entity object and any
	 * Entity objects in surrounding cells (left and above only
	 * to prevent useless repetition).
	 * 
	 * @param 	x	the x location of the cell being checked
	 * @param 	y	the y location of the cell being checked
	 */
	private void handleCell(int x, int y) {
		Entity e = cells[x][y];
		while(e != null) {
			// Handle other units in this cell.
			handleEntity(e, e.getNext());
			
			// Also try the neighbouring cells.
			if(x > 0 && y > 0)
				handleEntity(e, cells[x-1][y-1]);
			if(x > 0)
				handleEntity(e, cells[x-1][y]);
			if(y > 0)
				handleEntity(e, cells[x][y-1]);
			if(x > 0 && y < NUM_OF_Y_CELLS-1)
				handleEntity(e, cells[x-1][y+1]);
			
			e = e.getNext();
		}
	}
	
	/**
	 * Checks for a collision between a primary Entity object and a
	 * secondary Entity object, before also checking the primary
	 * Entity object against every other Entity object in the secondary
	 * Entity object's cell.
	 * 
	 * @param 	e		the primary Entity object
	 * @param 	other	the secondary Entity object
	 */
	public void handleEntity(Entity e, Entity other) {
		while(other != null) {
			if(e.getRectangle().intersects(other.getRectangle())) {
				fireCollisionEvent(e, other);
			}
			other = other.getNext();
		}
	}
	
	/**
	 * Handles the movement of an Entity from one position to a new one.
	 * Handled from inside the Grid class in order to correctly update
	 * which cell an Entity object is in should it change.
	 * 
	 * @param 	e	the Entity object moving
	 * @param 	x	the new x location of the Entity object
	 * @param 	y	the new y location of the Entity object
	 * @return		true if the new location of the Entity object is
	 * 				inside the viewable area, false if not
	 */
	public boolean move(Entity e, double x, double y) {
		// See which cell it was in.
		int oldCellX = (int)(e.getX() / CELL_WIDTH);
		int oldCellY = (int)(e.getY() / CELL_HEIGHT);
		
		// See which cell it's moving to.
		int cellX = (int)(x / CELL_WIDTH);
		int cellY = (int)(y / CELL_HEIGHT);
		
		e.setX(x);
		e.setY(y);
		
		// If it didn't change cells, we're done.
		if(oldCellX == cellX && oldCellY == cellY) {
			return true;
		}
		
		e.setCellX(cellX);
		e.setCellY(cellY);
		
		// Unlink it from the list of its old cell.
		if(e.getPrev() != null) {
			e.getPrev().setNext(e.getNext());
		}

		if(e.getNext() != null) {
			e.getNext().setPrev(e.getPrev());
		}

		// If it's the head of a list, remove it.
		if(cells[oldCellX][oldCellY] == e) {
			cells[oldCellX][oldCellY] = e.getNext();
		}
		
		// Add it back to the grid at its new cell.
		return add(e);
	}
	
	public void removeFromGrid(Entity e) {
		// Unlink it from the list of its old cell.
		if(e.getPrev() != null) {
			e.getPrev().setNext(e.getNext());
		}

		if(e.getNext() != null) {
			e.getNext().setPrev(e.getPrev());
		}

		// If it's the head of a list, remove it.
		if(cells[e.getCellX()][e.getCellY()] == e) {
			cells[e.getCellX()][e.getCellY()] = e.getNext();
		}
	}
	
	/**
	 * Creates a new {@link game.grid.CollisionEvent CollisionEvent} and sends
	 * it to the current {@link game.main.GameManager GameManager} object.
	 * 
	 * @param 	e1	the first Entity object involved in the collision
	 * @param 	e2	the second Entity object involved in the collision
	 */
	private void fireCollisionEvent(Entity e1, Entity e2) {
		gm.collisionOccurred(new CollisionEvent(this, e1, e2));
	}
}