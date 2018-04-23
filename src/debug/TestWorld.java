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

		PaintPanel pPanel = new PaintPanel();

		PhysicsWorld pWorld = new PhysicsWorld();
		CircleBody c1 = new CircleBody(new Vec2(500, 500), new Vec2(600, 600), new Vec2(10, 0), 200, 10, 1, 0, 0);
		pWorld.addBody(c1);
		CircleBody c2 = new CircleBody(new Vec2(800, 500), new Vec2(900, 600), new Vec2(-10, 0), 200, 10, 1, 0, 0);
		pWorld.addBody(c2);

		SCircle circle1 = new SCircle(c1);
		SCircle circle2 = new SCircle(c2);

		pPanel.add(circle1);
		pPanel.add(circle2);

		mainFrame.add(pPanel);

		mainFrame.setVisible(true);

		int count = 0;

		while(true) {
			pWorld.tick(1000);
			circle1.update();
			circle2.update();
			pPanel.repaint();
		}
	}
}