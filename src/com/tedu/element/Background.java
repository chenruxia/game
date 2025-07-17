package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Background extends ElementObj{

	private Image bgImage;
    private int y1 = 0;
    private int y2 = -600;
    
    private String bgPath;//存储背景图片路径
    private static final Random random = new Random();
    

    @Override
    public ElementObj createElement(String str) {
    	//随机选择一张背景图片（1-10）
    	int bgNumber = random.nextInt(10)+1;
    	String extension = (bgNumber == 1 || bgNumber == 3 || bgNumber == 4 || bgNumber == 5 ||bgNumber ==8)?".png":".jpg";
    	this.bgPath = "image/background/"+bgNumber+extension;
    	
    	
    	
    	
        this.bgImage = new ImageIcon(bgPath).getImage();
        System.out.print("加载背景图片："+bgPath);
        this.setX(0);
        this.setY(0);
        this.setW(800);
        this.setH(600);
        this.setSpeed(1); // 背景滚动速度
        return this;
    }
    
    
	
    @Override
    public void showElement(Graphics g) {
        int w = g.getClipBounds().width;
        int h = g.getClipBounds().height;
        if(bgImage != null) {
            g.drawImage(bgImage, 0, y1, w, h, null);
            g.drawImage(bgImage, 0, y2, w, h, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(0, 0, w, h);
            System.out.println("背景图片加载失败：" + bgPath);
        }
    }

	@Override
    protected void move() {
        y1 += this.getSpeed();
        y2 += this.getSpeed();
        
        if(y1 >= getH()) {
            y1 = -getH();
        }
        if(y2 >= getH()) {
            y2 = -getH();
        }
    }
	
}
