package debug;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JWindow;

import bodies.CircleBody;
import vecmath.Vec2;
import world.PhysicsWorld;

public class TestWorld {
	public static void main(String[] args) {
		JFrame mainFrame = new JFrame();
		mainFrame.setSize(new Dimension(1920, 1080));

		PhysicsWorld pWorld = new PhysicsWorld();
		CircleBody c = new CircleBody(new Vec2(500, 500), new Vec2(600, 600), new Vec2(10, 10), 200, 10, 1, 0, 0);
		pWorld.addBody(c);

		SCircle circle = new SCircle(c);
		mainFrame.add(circle);
		mainFrame.setVisible(true);

		int count = 0;

		while(true) {
			pWorld.tick(100);
			if(count % 20 == 0) {
				circle.update();
				circle.repaint();
			}
			count++;
		}
	}
}