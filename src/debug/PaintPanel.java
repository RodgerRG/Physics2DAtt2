package debug;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class PaintPanel extends JPanel{
	@Override
	public void paintComponent(Graphics g) {
		for(Component c : this.getComponents()) {
			c.paint(g);
		}
	}
}
