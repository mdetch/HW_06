import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
 
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

public class ScribbleApplet extends JFrame{
	
	JSlider rSlider, gSlider, bSlider;
	JLabel rLabel, gLabel, bLabel, brushLabel;
	JButton inc, dec, clear;
	DrawingPad drawPad;
	JPanel colorSample;
	LinkedList<Image> drawing = new LinkedList<Image>();

	public static void main(String [] args){
		ScribbleApplet mainGUI = new ScribbleApplet();
		mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGUI.setSize(600,600); 
        mainGUI.setVisible(true);
	}
	
	public ScribbleApplet(){
		setLayout(null);
		
		Container pane=this.getContentPane();
		drawPad = new DrawingPad();
		drawPad.setBounds(0,130,600,470);
		pane.add(drawPad);
		
		//initialize color Labels
		rLabel = new JLabel("R:"+drawPad.red);
		gLabel = new JLabel("G:"+drawPad.green);
		bLabel = new JLabel("B:"+drawPad.blue);
		brushLabel = new JLabel("Brush Size: "+drawPad.sizeBrush);
		
		rLabel.setBounds(5,10,50,30);
		gLabel.setBounds(5,50,50,30);
		bLabel.setBounds(5,90,50,30);
		brushLabel.setBounds(420,10,100,20);
		
		//initialize color Sliders
		rSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
		gSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
		bSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
		
		rSlider.addChangeListener(new SliderChanged());
		gSlider.addChangeListener(new SliderChanged());
		bSlider.addChangeListener(new SliderChanged());
		
		rSlider.setBounds(40,10,300,30);
		gSlider.setBounds(40,50,300,30);
		bSlider.setBounds(40,90,300,30);
		
		add(rLabel);
		add(gLabel);
		add(bLabel);
		add(brushLabel);
		
		add(rSlider);
		add(gSlider);
		add(bSlider);
		
		//initialize buttons
		inc = new JButton("+");
		dec = new JButton("-");
		clear = new JButton("Erase");
		
		inc.setBounds(360,10,50,50);
		dec.setBounds(360,70,50,50);
		clear.setBounds(530,10,60,110);
		
		inc.addActionListener(new IncBrushSize());
		dec.addActionListener(new DecBrushSize());
		clear.addActionListener(new ClearScreen());
		
		add(inc);
		add(dec);
		add(clear);	
		colorSample = new JPanel();
		updateColorSample(0,0,0);
	}
	public void undo(){
		if(drawing.size()>1){
			drawing.removeLast();
			System.out.println(drawing.size());
			drawPad.image.getGraphics().drawImage(drawing.getLast(),0,0,null);
			drawPad.repaint();
		}else if(drawing.size()==1){
			drawPad.clear();
			drawing.removeLast();
		}
	}
	
	public void updateColorSample(int r,int g,int b){
		colorSample.setBounds(420,40,100,80);
		colorSample.setBackground(new Color(r,g,b));
		add(colorSample);
	}
	
	public void updateRGB(int r,int g,int b){
		drawPad.red = r;
		drawPad.green = g;
		drawPad.blue = b;
		
		rLabel.setText("R:"+r);
		gLabel.setText("G:"+g);
		bLabel.setText("B:"+b);
		
		updateColorSample(r,g,b);
	}
	
	
class SliderChanged implements ChangeListener{

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		updateRGB(rSlider.getValue(),gSlider.getValue(),bSlider.getValue());
	}
	
}

class IncBrushSize implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(drawPad.sizeBrush<30){
			drawPad.sizeBrush++;
			brushLabel.setText("Brush Size: "+drawPad.sizeBrush);
		}
	}
}

class DecBrushSize implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(drawPad.sizeBrush>1){
			drawPad.sizeBrush--;
			brushLabel.setText("Brush Size: "+drawPad.sizeBrush);
		}
	}
}

class ClearScreen implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		drawPad.addImageToLinkedList();
		drawPad.clear();
	}
}
	
class DrawingPad extends JComponent{
	Image image;
    Graphics2D graphics2D;
    int oldX, oldY, currentX, currentY;
    int red,green,blue;
    int sizeBrush=10;
    public DrawingPad() {
      setDoubleBuffered(false);
      
      addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          oldX = e.getX();
          oldY = e.getY();
         
        }
        public void mouseReleased(MouseEvent e) {
  		 // 	System.out.println("Released");
        	addImageToLinkedList();
        }
      });
      addMouseMotionListener(new MouseMotionAdapter() {
        public void mouseDragged(MouseEvent e) {
          currentX = e.getX();
          currentY = e.getY();
         graphics2D.setColor(new Color(red,green,blue));
         graphics2D.setStroke(new BasicStroke(sizeBrush));
         graphics2D.drawLine(oldX, oldY, currentX, currentY);
         repaint();
         oldX = currentX;
         oldY = currentY;
        }
      });
    }  
    public void addImageToLinkedList(){
    	Image i = createImage(getSize().width, getSize().height);
    	Graphics g = i.getGraphics();
    	g.drawImage(image, 0, 0, null);
    	drawing.add(i);
    	System.out.println(drawing.size());
    }
        public void paintComponent(Graphics g) {
                 if (image == null) {
                	 image = createImage(getSize().width, getSize().height);
                	 graphics2D = (Graphics2D) image.getGraphics();
                	 graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                	 clear();
                 }
                 g.drawImage(image, 0, 0, null);
                 System.out.println("paint component called");
        }
 
      public void clear() {
        graphics2D.setPaint(Color.WHITE);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setPaint(Color.BLACK);
        repaint();
        System.out.println("clear");
      }
}
}
