import java.awt.*;
import java.applet.Applet;

import javax.swing.JApplet;
import javax.swing.JComponent;

public class MyAvatar extends JComponent{
	public void paint(Graphics g){
		Dimension d = this.getSize();
		Color n = new Color(210,161,140);
		this.setSize(600, 600);
	
		g.setColor(Color.BLACK);
		int [] x = {120,140,140,230,230,270,310,310,400,400,420};
		int [] y = {215,180,185,110,115,80,115,110,185,180,215};
		g.fillPolygon(x,y,11);  	//hair
		g.drawOval(110,270,50,70);	//ear
		g.drawOval(385,270,50,70);
		g.setColor(n);
		g.fillOval(110,270,50,70);
		g.fillOval(385,270,50,70);

		g.setColor(Color.BLACK);
		g.drawArc(120,-80,300,600,180,180);	//face
		g.drawArc(120,170,300,100,-180,-180);
		
		g.setColor(n);
		g.fillArc(120,-80,300,600,180,180);
		g.fillArc(120,170,300,100,-180,-180);
		
		
		g.setColor(Color.BLACK);
		g.drawOval(160,290,80,20);		//eye
		g.drawOval(290,290,80,20);
		g.drawLine(265,300,250,400);	//nose
		g.drawLine(250,400,265,400);
		
		//g.fillPolygon();
		g.setColor(Color.WHITE);
		g.fillOval(160,290,80,20);
		g.fillOval(290,290,80,20);
		g.setColor(Color.BLACK);
		g.fillOval(190,290,20,20);
		g.fillOval(320,290,20,20);
		g.drawArc(200,400,150,50,180,180);	//Mouth
		
		int [] x1 = {155,235,236,156};
		int [] y1 = {270,275,280,275};
		g.fillPolygon(x1,y1,4);
		
		int [] x2 = {295,375,370,290};
		int [] y2 = {275,270,275,280};
		g.fillPolygon(x2,y2,4);
		g.setColor(Color.BLACK);
		g.drawString("METHUS",d.width-80,d.height-20);
	}
}
