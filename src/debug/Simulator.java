package debug;

import javax.swing.JPanel;
import javax.swing.JWindow;

import bodies.RigidBody2;
import world.PhysicsWorld;

public class Simulator extends Thread{
	private JPanel window;
	
	public void addWorld(JPanel window) {
		this.window = window;
	}
	
	@Override
	public void run() {
		while(true) {
			window.repaint();
		}
	}
	
}
