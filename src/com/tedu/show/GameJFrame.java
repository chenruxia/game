package com.tedu.show;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameJFrame extends JFrame{
	public static int GameX = 800; 
	public static int GameY = 600;
	private JPanel jPanel =null; 
	private KeyListener  keyListener=null;
	private MouseMotionListener mouseMotionListener=null; 
	private MouseListener mouseListener=null;
	private Thread thead=null; 
	
	public GameJFrame() {
		init();
	}
	public void init() {
		this.setSize(GameX, GameY); 
		this.setTitle("飞机大战");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
//		确保内容面板可见
		getContentPane().setBackground(Color.BLACK);
//		validate();

	}

	public void addButton() {

	}	
	
	public void start() {
		if(jPanel!=null) {
			this.add(jPanel);
		}
		if(keyListener !=null) {
			this.addKeyListener(keyListener);
		}
		if(thead !=null) {
			thead.start();
		}
//界面刷新
		this.setVisible(true);
		
		if(this.jPanel instanceof Runnable) {
			new Thread((Runnable)this.jPanel).start();
		}
	}
	
	public void setjPanel(JPanel jPanel) {
		this.jPanel = jPanel;
	}
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}
	public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.mouseMotionListener = mouseMotionListener;
	}
	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}
	public void setThead(Thread thead) {
		this.thead = thead;
	}
	
	// 添加获取面板的方法
	public JPanel getjPanel() {
	    return jPanel;
	}
	
	
}





