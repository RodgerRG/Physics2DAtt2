package debug;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.Timer;

import bodies.CircleBody;
import bodies.RigidBody2;
import vecmath.Vec2;
import world.PhysicsWorld;

public class TestWorld {
	public static void main(String[] args) throws InterruptedException {
		JFrame mainFrame = new JFrame();
		mainFrame.setSize(new Dimension(1920, 1080));

		final PaintPanel pPanel = new PaintPanel();

		final PhysicsWorld pWorld = new PhysicsWorld();
		CircleBody c1 = new CircleBody(new Vec2(500, 500), new Vec2(600, 600), new Vec2(50, 0), 200, 10, 1, 0, 1);
		pWorld.addBody(c1);
		CircleBody c2 = new CircleBody(new Vec2(800, 500), new Vec2(900, 600), new Vec2(-50, 0), 200, 10, 1, 0, 1);
		pWorld.addBody(c2);

		SCircle circle1 = new SCircle(c1);
		SCircle circle2 = new SCircle(c2);

		pPanel.add(circle1);
		pPanel.add(circle2);
		
		pPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int x = arg0.getX();
				int y = arg0.getY();
				
				CircleBody c = new CircleBody(new Vec2(x - 100, y - 100), new Vec2(x, y), new Vec2((0.5 - Math.random()) * 100, (0.5 - Math.random()) * 100), 200, 10, 1, 0, 1);
				pWorld.addBody(c);
				SCircle circle = new SCircle(c);
				pPanel.add(circle);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

		mainFrame.add(pPanel);

		mainFrame.setVisible(true);
		
		long cTime = System.nanoTime();
		long sTime = System.nanoTime();

		while(true) {
			if((System.nanoTime() - cTime) / 1e6 >= 1) {
				pWorld.tick(1);
				circle1.update();
				circle2.update();
				
				cTime = System.nanoTime();
			}
			
			if((System.nanoTime() - sTime) / 1e6 >= 1) {
				pPanel.repaint();
				
				sTime = System.nanoTime();
			}
		}
	}
}