package game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingUtilities;

import game.enemy_types.SampleEnemy;
import game.entities.Element;
import game.entities.Enemy;
import game.entities.Entity;
import game.entities.Player;
import game.entities.Projectile;
import game.frame.Window;
import game.grid.CollisionEvent;
import game.grid.CollisionListener;
import game.grid.Grid;

/**
 * Controls the flow of gameplay and manages all events and interactions between
 * game actors.
 * 
 * @author Andrew Matthews
 */
public class GameEngine implements CollisionListener
{	
	private Window window;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> projectiles;
	private Grid grid;

	private double dT = 0;

	/**
	 * GameManager constructor - initialises all fields used throughout gameplay.
	 */
	public GameEngine() {
		window = new Window(this);
		enemies = new ArrayList<Enemy>();
		projectiles = new ArrayList<Projectile>();
		grid = new Grid(this);
	}

	/**
	 * Handles KeyEvents fired from the Window class when a key is pressed, 
	 * usually passing them to the Player object.
	 * 
	 * @param e		KeyEvent object given by the Window class
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_SPACE) {
			Projectile pShot = new Projectile(grid, player.getX()-5+player.getWidth()/2, player.getY()-10-2*player.getAxisSpeed()*dT, 10, 10, player.getElement(), 600, 10, player);
			projectiles.add(pShot);
		} else {
			player.keyPressed(e);
		}
	}

	/**
	 * Handles KeyEvents fired from the Window class when a key is released,
	 * passing them to the Player object.
	 * 
	 * @param e		KeyEvent object given by the Window class
	 */
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	/**
	 * Safely invokes the required Swing methods to create the Window to be
	 * displayed.
	 */
	public void buildWindow() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				window.build();
			}
		});
	}

	/**
	 * Creates the Player object.
	 */
	public void addPlayer() {
		player = new Player(grid, 300, 400, 50, 50, Element.RED);
	}

	/**
	 * Adds some Enemy objects to the current game.
	 */
	public void addSampleEnemies() {		
		SampleEnemy e1 = new SampleEnemy(grid, 50, 70, 40, 40, Element.GREEN, 20);
		enemies.add(e1);
		/*
		SampleEnemy e2 = new SampleEnemy(grid, 80, 70, 40, 40, Element.RED, 10);
		enemies.add(e2);
		SampleEnemy e3 = new SampleEnemy(grid, 50, 200, 40, 40, Element.BLUE, 30);
		enemies.add(e3);
		SampleEnemy e4 = new SampleEnemy(grid, 120, 300, 40, 40, Element.GREEN, 40);
		enemies.add(e4);
		SampleEnemy e5 = new SampleEnemy(grid, 400, 40, 40, 40, Element.RED, 15);
		enemies.add(e5);
		SampleEnemy e6 = new SampleEnemy(grid, 200, 200, 40, 40, Element.BLUE, 20);
		enemies.add(e6);
		*/
	}
	
	/**
	 * Draws all relevant game entities and graphics onto the current Graphics
	 * object.
	 * 
	 * @param g		the Graphics object being displayed
	 */
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		for(Projectile p : projectiles) {
			p.drawEntity(g);
		}
		for(Enemy e : enemies) {
			e.drawEntity(g);
		}
		player.drawEntity(g);
	}

	/**
	 * Moves all entities, checking for any collisions or other events via the
	 * Grid class.
	 */
	public void update(double dT) {
		this.dT = dT;
		
		// Move projectiles - removes ones out of bounds.
		Iterator<Projectile> i1 = projectiles.iterator();
		while(i1.hasNext()) {
			Projectile p = i1.next();
			if(!p.move(dT)) {
				i1.remove();
			}
		}
		
		// Move enemies incl. their behaviours.
		for(Enemy e : enemies) {
			e.behave();
			e.move(dT);
			e.shoot(player);
		}
		
		player.move(dT);

		grid.checkCollisions();
	}
	
	/**
	 * Creates a shot Projectile originating from the given Entity.
	 * @param e		the Entity firing the shot
	 */
	public void createShot(Entity e) {
		Projectile pShot = new Projectile(grid, e.getX()-5+e.getWidth()/2, e.getY()-10-2*e.getYVel()*dT, 10, 10, e.getElement(), 600, 10, player);
		projectiles.add(pShot);
	}
	
	/**
	 * Calls {@link game.frame.Window#render() Window.render()}, rendering
	 * the current Graphics object.
	 */
	public void render() {
		window.render();
	}

	@Override
	public void collisionOccurred(CollisionEvent e) {
		Entity e1 = e.getEntities()[0];
		Entity e2 = e.getEntities()[1];

		//System.out.println("CollisionEvent occurred between " + e1.toString() + " and " + e2.toString());

		if(e1 instanceof Projectile) {
			grid.removeFromGrid(e1);
			projectiles.remove(e1);

			if(e2 instanceof Enemy) {
				if(e1.getElement() == e2.getElement()) {
					((Enemy) e2).incrementPower();
					((Enemy) e2).modHp(((Projectile) e1).getDamage());
				} else {
					((Enemy) e2).modHp(-((Projectile) e1).getDamage());
					if(((Enemy) e2).getHp() <= 0) {
						grid.removeFromGrid(e2);
						enemies.remove(e2);
					}
				}
			}
		} else if(e2 instanceof Projectile) {
			grid.removeFromGrid(e2);
			projectiles.remove(e2);
		} else {

		}
	}
}