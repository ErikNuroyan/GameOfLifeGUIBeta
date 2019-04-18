package guiLife;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
public class GamePanel extends JPanel {
	private World world = null;
	@Override
	protected void paintComponent(java.awt.Graphics g) {
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if(this.world!=null){
		g.setColor(Color.LIGHT_GRAY);
		int min=Math.min(getWidth()/this.world.getWidth(),getHeight()/this.world.getHeight());
		for(int i=0;i<this.world.getHeight();i++) {
			for(int z=0;z<this.world.getWidth();z++) {
				if(world.getCell(z,i)) {
					g.setColor(Color.BLACK);
					g.fillRect(i*min, z+z*min, min, min);
				}
				else {
					g.setColor(Color.LIGHT_GRAY);
					g.drawRect(i*min, z+z*min, min, min);
				}
			}
		}
			
		}
		
}
public void display(World w) {
	world = w;
	repaint();
	}
}
