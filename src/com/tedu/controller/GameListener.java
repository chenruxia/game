package com.tedu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tedu.controller.GameThread.GameState;
import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.SoundManager;
import com.tedu.show.GameMainJPanel;

/*
 * @说明 监听类，用于监听用户的操作keyListener
 * **/

public class GameListener implements KeyListener{
	private GameThread gameThread;
	private GameMainJPanel gamePanel;
	
	public GameListener(GameThread gameThread,GameMainJPanel gamePanel) {
		this.gameThread = gameThread;
		this.gamePanel = gamePanel;
	}
	
	
	
	private ElementManager em = ElementManager.getManager();
	private Set<Integer> set =new HashSet<Integer>();
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
	    switch (gameThread.getGameState()) {
	        case MENU:
	            handleMenuInput(e);
	            break;
	        case LEVEL_SELECT:
	            handleLevelSelectInput(e);
	            break;
	        case SHIP_SELECT:
	        	handleShipSelectInput(e);
	        case RUNNING:
	            handleGameInput(e);
	            break;
	        case GAME_OVER:
	            handleGameOverInput(e);
	            break;
	    }
	}
	
	private void handleMenuInput(KeyEvent e) {
	    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
	        gameThread.enterLevelSelect(); // 进入关卡选择界面
	    }
	}
	
	private void handleLevelSelectInput(KeyEvent e) {
	    switch (e.getKeyCode()) {
	        case KeyEvent.VK_UP:
	            if(gamePanel != null) {  // 添加判空保护
	                gamePanel.selectLevelUp();
	                gameThread.setSelectedLevel(gamePanel.getSelectedLevel());
	            }
//	            SoundManager.getInstance().playSound("menu_select");
	            break;
	        case KeyEvent.VK_DOWN:
	            if(gamePanel != null) {  // 添加判空保护
	                gamePanel.selectLevelDown();
	                gameThread.setSelectedLevel(gamePanel.getSelectedLevel());
	            }
//	            SoundManager.getInstance().playSound("menu_select");
	            break;
	        case KeyEvent.VK_ENTER:
//	            gameThread.startGame();
	            gameThread.setGameState(GameState.SHIP_SELECT);
//	            SoundManager.getInstance().playSound("menu_confirm");
	            break;
	        case KeyEvent.VK_ESCAPE:
	            gameThread.setGameState(GameState.MENU); // 这个调用现在是正确的
//	            SoundManager.getInstance().playSound("menu_cancel");
	            break;
	    }
	}
	
	private void handleShipSelectInput(KeyEvent e) {
	    switch (e.getKeyCode()) {
	        case KeyEvent.VK_UP:
	            gamePanel.selectShipUp();
//	            SoundManager.getInstance().playSound("menu_select");
	            break;
	        case KeyEvent.VK_DOWN:
	            gamePanel.selectShipDown();
//	            SoundManager.getInstance().playSound("menu_select");
	            break;
	        case KeyEvent.VK_ENTER:
	            gameThread.setSelectedShip(gamePanel.getSelectedShipIndex());
	            gameThread.startGame();
	            gameThread.setGameState(GameState.RUNNING);
//	            SoundManager.getInstance().playSound("menu_confirm");
	            break;
	        case KeyEvent.VK_ESCAPE:
	            gameThread.setGameState(GameState.LEVEL_SELECT);
//	            SoundManager.getInstance().playSound("menu_cancel");
	            break;
	    }
	}
	
	private void handleGameInput(KeyEvent e) {
	    List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
	    for (ElementObj obj : play) {
	        obj.keyClick(true, e.getKeyCode());
	    }
	}

	private void handleGameOverInput(KeyEvent e) {
	    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
	        gameThread.setGameState(GameState.MENU);
	    }
	}
	
	//松开
	@Override
	public void keyReleased(KeyEvent e) {
//		if(!set.contains(e.getKeyCode())){
//			return;
//		}
//		set.remove(e.getKeyCode());
		if(gameThread.getGameState() == GameState.RUNNING) {
			List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
			for(ElementObj obj:play) {
				obj.keyClick(false,e.getKeyCode());
			}
		}
//		System.out.println("keyPeleased"+e.getKeyCode());
		
	}
	
	

}