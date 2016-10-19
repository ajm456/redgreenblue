package game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingUtilities;

import game.bts.libraries.TestBTLibrary;
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
import jbt.execution.core.BTExecutorFactory;
import jbt.execution.core.ContextFactory;
import jbt.execution.core.IBTExecutor;
import jbt.execution.core.IBTLibrary;
import jbt.execution.core.IContext;
import jbt.model.core.ModelTask;

/**
 * Controls the flow of gameplay and manages all events and interactions between
 * game actors.
 * 
 * @author Andrew Matthews
 */
public class GameManager implements CollisionListener
{	
	private Window window;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> projectiles;
	private Grid grid;
	private IBTLibrary btLibrary;
	private IContext context;
	private BTHandler treeHandler;

	private double dT = 0;

	/**
	 * GameManager constructor - initialises all fields used throughout gameplay.
	 */
	public GameManager() {
		window = new Window(this);
		enemies = new ArrayList<Enemy>();
		projectiles = new ArrayList<Projectile>();
		grid = new Grid(this);

		btLibrary = new TestBTLibrary();
		context = ContextFactory.createContext(btLibrary);
		ArrayList<IBTExecutor> treeExecutors = new ArrayList<IBTExecutor>();
		treeHandler = new BTHandler(treeExecutors);
	}

	/**
	 * Handles KeyEvents fired from the Window class when a key is pressed, 
	 * usually passing them to the Player object.
	 * 
	 * @param e		KeyEvent object given by the Window class
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_SPACE) {
			Projectile pShot = new Projectile(grid, player.getX()-5+player.getWidth()/2, player.getY()-2*player.getAxisSpeed()*dT, 10, 10, player.getElement(), 600, 10, player);
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
	 * Builds behaviour trees for assigning AI behaviours to enemies.
	 */
	public void setUpTrees() {
		ModelTask sampleEnemy1Tree = btLibrary.getBT("test");
		IBTExecutor btExecutor = BTExecutorFactory.createBTExecutor(sampleEnemy1Tree, context);
		treeHandler.add(btExecutor);
		new Thread(treeHandler).start();
	}

	/**
	 * Adds some Enemy objects to the current game.
	 */
	public void addSampleEnemies() {		
		SampleEnemy e1 = new SampleEnemy(grid, 50, 70, 40, 40, Element.GREEN, 20);
		enemies.add(e1);
		context.setVariable("CurrentEntityID", "sampleEnemy1");
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
		Iterator<Projectile> i = projectiles.iterator();
		while(i.hasNext()) {
			Projectile p = i.next();
			if(!p.move(dT)) {
				i.remove();
			}
		}

		player.move(dT);

		grid.checkCollisions();
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