package com.tedu.element;

import java.awt.Graphics;
import javax.swing.ImageIcon;

import com.tedu.manager.GameLoad;
import com.tedu.manager.SoundManager;

public class Explosion extends ElementObj {
    private long createTime;
    
    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        this.setW(60);
        this.setH(60);
        this.createTime = System.currentTimeMillis();
        
        SoundManager.getInstance().playSound("explosion");
        return this;
    }
    
    @Override
    public void showElement(Graphics g) {
        long elapsed = System.currentTimeMillis() - createTime;
        if (elapsed > 500) { // 0.5秒后消失
            this.setLive(false);
            return;
        }
        
        ImageIcon icon = GameLoad.imgMap.get("explosion");
        if (icon != null) {
            g.drawImage(icon.getImage(), getX(), getY(), getW(), getH(), null);
        }
    }
}