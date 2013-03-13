import javax.swing.*;
import java.awt.*;

public class Test extends JComponent{
	Graphics2D graphics2D;
	Image image;
	public void paintComponent(Graphics g){
		 image = createImage(getSize().width, getSize().height);
		 graphics2D = (Graphics2D) image.getGraphics();
		graphics2D.setColor(Color.black);
		graphics2D.setStroke(new BasicStroke(20));
		graphics2D.drawLine(1, 1, 100, 100);
		g.drawImage(image,0,0,null);
	}
}
