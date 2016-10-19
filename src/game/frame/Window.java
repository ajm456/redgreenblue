package game.frame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import game.main.GameManager;

public class Window 
{
	public final static int WIDTH = 600;
	public final static int HEIGHT = 700;
	public final static Rectangle BOUNDS = new Rectangle(0, 0, WIDTH, HEIGHT);
	
	private JFrame frame;
	private JPanel panel;
	
	public Window(GameManager gm) {
		frame = new JFrame();
		panel = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				gm.draw(g);
			}
		};
		
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				gm.keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				gm.keyReleased(e);
			}
			@Override
			public void keyTyped(KeyEvent e) {}
		});
	}
	
	public void build() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void render() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					panel.repaint();
					frame.repaint();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
