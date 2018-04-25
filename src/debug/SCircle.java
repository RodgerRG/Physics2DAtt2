package debug;

import java.awt.Graphics;

import javax.swing.JComponent;

import bodies.CircleBody;
import bodies.RigidBody2;

public class SCircle extends JComponent{
	private int radius;
	private int posX;
	private int posY;
	private CircleBody body;
	
	public SCircle(CircleBody body) {
		radius = (int) body.getSizeX();
		posX = (int) body.getPosition().getX();
		posY = (int) body.getPosition().getY();
		this.body = body;
	}
	
	public void update() {
		radius = (int) body.getSizeX();
		posX = (int) body.getPosition().getX();
		posY = (int) body.getPosition().getY();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawOval(posX, posY, radius, radius);
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawOval(posX, posY, radius, radius);
	}
}
