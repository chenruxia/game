package com.tedu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tedu.element.ElementObj;

public class ElementManager {
	
	private Map<GameElement,List<ElementObj>> gameElements;	
	
	private boolean globalSlow = false;

	public void setGlobalSlow(boolean slow) {
	    this.globalSlow = slow;
	}

	public boolean isGlobalSlow() {
	    return globalSlow;
	}
	
	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}

	public void addElement(ElementObj obj,GameElement ge) {

		gameElements.get(ge).add(obj);
	}

	public List<ElementObj> getElementsByKey(GameElement ge){
		
		return gameElements.get(ge);
	}	
	
	private static ElementManager EM=null; 
	public static synchronized ElementManager getManager() {
		if(EM == null) {
			EM=new ElementManager();
		}
		return EM;
	}
	private ElementManager() {
		init(); 
	}

	public void init() {
		gameElements=new HashMap<GameElement,List<ElementObj>>();
				//		gameElements.put(GameElement.PLAY, new ArrayList<ElementObj>());
				//		gameElements.put(GameElement.MAPS, new ArrayList<ElementObj>());
				//		gameElements.put(GameElement.ENEMY, new ArrayList<ElementObj>());
				//		gameElements.put(GameElement.BOSS, new ArrayList<ElementObj>());
		for(GameElement ge:GameElement.values()) {
			gameElements.put(ge, new ArrayList<ElementObj>());
		}
	}
	
}







