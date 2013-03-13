import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ScribbleShop extends JFrame{
	
	JDesktopPane desktop;
	JMenuBar menuBar;
	JMenu file,edit,about;
	JMenuItem newMenu,close,closeAll,exit,help,author,undo;
	
	public static void main(String [] args){
		JFrame mainFrame = new ScribbleShop();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
	}
	
	public ScribbleShop(){
		int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,screenSize.width  - inset*2, screenSize.height - inset*2);
		desktop = new JDesktopPane();
	    setContentPane(desktop);
	    desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
	    createFrame();
	    menu();
	}
	
	public void menu(){
		menuBar = new JMenuBar();
		file = new JMenu("File");
		edit = new JMenu("Edit");
		about = new JMenu("About");
		
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(about);
		
		newMenu = new JMenuItem("New",'N');
		newMenu.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,KeyEvent.CTRL_MASK));
		close = new JMenuItem("Close");
		close.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W,KeyEvent.CTRL_MASK));
		closeAll = new JMenuItem("Close All");
		closeAll.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A,KeyEvent.CTRL_MASK));
		exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q,KeyEvent.CTRL_MASK));
		
		file.add(newMenu);
		file.add(close);
		file.add(closeAll);
		file.addSeparator();
		file.add(exit);
		
		help = new JMenuItem("Help");
		help.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1,0));
		author = new JMenuItem("Author");
		about.add(help);
		about.add(author);
		
		newMenu.addActionListener(new NewFile());
		close.addActionListener(new CloseWindow());
		closeAll.addActionListener(new CloseAllWindows());
		exit.addActionListener(new ExitProgram());
		author.addActionListener(new FindAuthor());
		help.addActionListener(new Help());
		
		undo = new JMenuItem("Undo");
		undo.addActionListener(new Undo());
		undo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z,KeyEvent.CTRL_MASK));
		edit.add(undo);
		
		setJMenuBar(menuBar);
		
	}
	
	protected void createFrame() {
	    MyInternalFrame frame = new MyInternalFrame();
	    frame.setVisible(true);
	    desktop.add(frame);
	    try {
	        frame.setSelected(true);
	    } catch (java.beans.PropertyVetoException e) {}
	}
	
	protected void showFace(){
		MyFaceFrame frame = new MyFaceFrame();
		frame.setVisible(true);
		desktop.add(frame);
	}
	
	protected void helpWindow(){
		MyHelpFrame frame = new MyHelpFrame();
		frame.setVisible(true);
		desktop.add(frame);
	}
	
	class NewFile implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("newfile called");
			createFrame();
		}
	}
	
	class CloseWindow implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("window closed");
			desktop.getSelectedFrame().setVisible(false);
		}
	}

	class CloseAllWindows implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("close all windows");
			JInternalFrame[] temp = desktop.getAllFrames();
			MyInternalFrame.openFrameCount = 0 ;
			for(int i=0 ; i<temp.length ; i++){
				temp[i].setVisible(false);
			}
		}
	}
	
	class ExitProgram implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Exit program");
			System.exit(1);
		}
	}
	
	class FindAuthor implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Find author");
			showFace();
		}
	}
	
	class Help implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Help called");
			helpWindow();
		}
	}
	
	class Undo implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			((MyInternalFrame)(desktop.getSelectedFrame())).a.undo();
		}
		
	}

	static class MyInternalFrame extends JInternalFrame{
		static int openFrameCount = 0;
		static final int xOffset = 30, yOffset = 30;
		ScribbleApplet a;

		public MyInternalFrame() {
		    super("ScribbleApplet",true, true,true,true);
		    ++openFrameCount;
		    a = new ScribbleApplet();
		    this.setSize(650,650);
		    this.add(a.getContentPane());
		    setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
		}

	}
	
	static class MyFaceFrame extends JDialog{
		public MyFaceFrame(){
			super(new JFrame(),"Author",true);
			JLabel name = new JLabel("I'm Methus Detchusananart!!");
			name.setFont(new Font("Serif", Font.PLAIN, 26));
			setLayout(new BorderLayout());
			this.setSize(600,650);
			this.add(new MyAvatar(),BorderLayout.CENTER);
			this.add(name,BorderLayout.SOUTH);
			setLocation(150,50);
		}
	}
	
	static class MyHelpFrame extends JDialog{
		public MyHelpFrame(){
			super(new JFrame(),"Help",true);
			JLabel helpMsg = new JLabel("Keep Calm and Continue Your Work ! =)");
			helpMsg.setFont(new Font("Serif", Font.PLAIN, 30));
			this.setSize(520,200);
			this.add(helpMsg);
			setLocation(200,200);
		}
	}
}
