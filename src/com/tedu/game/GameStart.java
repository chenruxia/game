package com.tedu.game;

import com.tedu.controller.GameListener;

import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

public class GameStart {
	public static void main(String[] args) {
		GameJFrame gj=new GameJFrame();//窗口
		//实例化主线程
		GameThread th=new GameThread();//线程
		
		
		
		GameMainJPanel jp=new GameMainJPanel(th);//画布
		GameListener listener = new GameListener(th,jp);//监听器
		
		th.setPanel(jp);
		
		gj.setjPanel(jp);
		gj.setKeyListener(listener);
		gj.setThead(th);
		gj.start();
		
	
		
	}
}
