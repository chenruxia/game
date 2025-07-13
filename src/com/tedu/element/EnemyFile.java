package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

public class EnemyFile extends ElementObj{
	private String fx;
	private boolean isTracking = false;
	private int trackCounter = 0;
	
	
	public EnemyFile() {
        this.setSpeed(5); // 敌机子弹速度
    }
	
	
	@Override
    public ElementObj createElement(String str) {
//		默认值
		this.setX(0);
		this.setY(0);
		this.fx = "down";
		
        String[] split = str.split(",");
        for(String str1 : split) {
            String[] split2 = str1.split(":");
            switch(split2[0]) {
                case "x": this.setX(Integer.parseInt(split2[1])); break;
                case "y": this.setY(Integer.parseInt(split2[1])); break;
                case "f": this.fx = split2[1];break; // 敌机子弹固定向下
                case "tracking":
                	this.isTracking = Boolean.parseBoolean(split2[1]);
                	break;
            }
        }
        this.setW(8);
        this.setH(15);
        this.setSpeed(5);
        return this;
    }
	
	
	@Override
	public void showElement(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(this.getX(), this.getY(), this.getW(), this.getH());
		
	}
	
	 @Override
	    protected void move() {
		 
		 if(isTracking) {
			 trackCounter++;
			 if(trackCounter % 10 == 0) {
				 List<ElementObj> players = ElementManager.getManager().getElementsByKey(GameElement.PLAY);
				 if(!players.isEmpty()) {
					 ElementObj player = players.get(0);
//					 简单追踪逻辑，向玩家方向微调
					 if(player.getX() > this.getX()) {
						 this.setX(this.getX()+1);
					 }else {
						 this.setX(this.getX()-1);
					 }
				 }
			 }
		 }
		 
		 this.setY(this.getY()+this.getSpeed());
		 
		 
		 this.setY(this.getY() + this.getSpeed());
		 
	       
	     if(this.getY() > 600) {
	          this.setLive(false);
	       }
	   }

}
