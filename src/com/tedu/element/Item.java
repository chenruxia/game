package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.manager.GameLoad;
import com.tedu.manager.SoundManager;

public class Item extends ElementObj{
	private int type; // 道具类型:1-火力升级,2-生命恢复

	public ElementObj createElement(String str) {
		String[] split = str.split(",");
        for(String str1 : split) {
            String[] split2 = str1.split(":");
            switch(split2[0]) {
                case "type": this.type = Integer.parseInt(split2[1]); break;
                case "x": this.setX(Integer.parseInt(split2[1])); break;
                case "y": this.setY(Integer.parseInt(split2[1])); break;
            }
        }
        
        switch(type) {
            case 1: 
                this.setIcon(GameLoad.imgMap.get("prop1"));
                break;
            case 2:
                this.setIcon(GameLoad.imgMap.get("prop2"));
                break;
            case 3:
                this.setIcon(GameLoad.imgMap.get("prop3"));
                break;
            case 4:
                this.setIcon(GameLoad.imgMap.get("prop4"));
                break;
            case 5:
                this.setIcon(GameLoad.imgMap.get("prop5"));
                break;
            case 6:
                this.setIcon(GameLoad.imgMap.get("prop6"));
                break;
            case 7:
                this.setIcon(GameLoad.imgMap.get("prop7"));
                break;
            default:
            	this.setIcon(GameLoad.imgMap.get("prop1"));
        }
        
        this.setW(30);
        this.setH(30);
        this.setSpeed(2);
        return this;
	}

	@Override
	public void showElement(Graphics g) {
	    ImageIcon icon = this.getIcon();
	    if(icon != null && icon.getImage() != null) {
	        g.drawImage(icon.getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	    } else {
	        // 图像加载失败时绘制一个默认图形（可选）
	        g.setColor(Color.BLUE);
	        g.fillRect(this.getX(), this.getY(), this.getW(), this.getH());
	        System.err.println("道具图像未正确加载");
	    }
	}
	
	@Override
    protected void move() {
        this.setY(this.getY() + this.getSpeed());
        if(this.getY() > 600) {
            this.setLive(false);
        }
    }
	
//	添加碰撞检测方法
	@Override
	public boolean pk(ElementObj obj) {
		if(super.pk(obj)) {
//			如果是与玩家碰撞
			if(obj instanceof Play) {
				Play player = (Play)obj;
				player.applyItemEffect(this.type);
//				播放道具获取音效（根据不同类型可以播放不同音效）
				SoundManager.getInstance().playSound("powerup");
				this.setLive(false);
				return true;
			}
		}
		return false;
	}
	

    public int getType() {
        return type;
    }
    
    public enum ItemEffect{
    	POWER_UP,
    	LIFE_UP,
    	SHIELD,
    	SPEED_UP,
    	BOMB,
    	LASER,
    	SCATTER
    	
    }

}
