package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

public class EnemyFile extends ElementObj{

	
	private String mode = "straight";
    private double angle = 90; // 90度向下
    private int tick = 0;
    
    private boolean isTracking = false; // 是否追踪
    private int trackCounter = 0;       // 追踪计数器
    private double vx = 0, vy = 0;      // 速度分量
	
	public EnemyFile() {
        this.setSpeed(2); // 敌机子弹速度
    }
	
	
	@Override
	public ElementObj createElement(String str) {
	    // 默认值
	    int x = 0, y = 0;
	    for (String param : str.split(",")) {
	        String[] kv = param.split(":");
	        if (kv[0].equals("x")) x = Integer.parseInt(kv[1]);
	        if (kv[0].equals("y")) y = Integer.parseInt(kv[1]);
	        if (kv[0].equals("tracking")) isTracking = Boolean.parseBoolean(kv[1]);
	        if (kv[0].equals("angle")) angle = Double.parseDouble(kv[1]);
	        if (kv[0].equals("mode")) mode = kv[1];
	    }
	    this.setX(x);
	    this.setY(y);
	    this.setW(8);
	    this.setH(15);
	    this.setSpeed(2);
	    
	    // 初始速度（默认向下）
	    vx = 0;
	    vy = this.getSpeed();
	    return this;
	}
	
	
	@Override
	public void showElement(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(this.getX(), this.getY(), this.getW(), this.getH());
		
	}
	
	@Override
	protected void move() {
	    tick++;
	    double rad = Math.toRadians(angle);
	    int speed = getSpeed();
	    int dx = 0, dy = 0;

	    switch(mode) {
	        case "straight":
	        case "fan":
	        case "circle":
	            dx = (int)(Math.cos(rad) * speed);
	            dy = (int)(Math.sin(rad) * speed);
	            break;
	        case "curve":
	            dx = (int)(Math.sin(tick / 10.0) * 5);
	            dy = speed;
	            break;
	        case "tracking":
	            // 每隔若干帧调整一次方向
	            if (trackCounter % 5 == 0) {
	                List<ElementObj> players = ElementManager.getManager().getElementsByKey(GameElement.PLAY);
	                if (!players.isEmpty()) {
	                    ElementObj player = players.get(0);
	                    double px = player.getX() + player.getW()/2;
	                    double py = player.getY() + player.getH()/2;
	                    double bx = this.getX() + this.getW()/2;
	                    double by = this.getY() + this.getH()/2;
	                    double ddx = px - bx;
	                    double ddy = py - by;
	                    double len = Math.sqrt(ddx*ddx + ddy*ddy);
	                    if (len != 0) {
	                        vx = speed * ddx / len;
	                        vy = speed * ddy / len;
	                    }
	                }
	            }
	            trackCounter++;
	            dx = (int)vx;
	            dy = (int)vy;
	            break;
	    }
	    setX(getX() + dx);
	    setY(getY() + dy);
	    if(getY() > 600 || getX() < 0 || getX() > 800) setLive(false);
	}

}
