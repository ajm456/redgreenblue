package game.grid;

import java.util.EventObject;

import game.entities.Entity;

public class CollisionEvent extends EventObject 
{
	private Entity e1, e2;
	
	public CollisionEvent(Object source, Entity e1, Entity e2) {
		super(source);
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public Entity[] getEntities() {
		return new Entity[] {e1, e2};
	}
}
