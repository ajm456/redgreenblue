package game.grid;

public interface CollisionListener
{
	/**
	 * Called when a collision occurs between entities.
	 * @param e		the CollisionEvent being fired
	 */
	void collisionOccurred(CollisionEvent e);
}
