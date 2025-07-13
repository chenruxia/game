package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class MapObj extends ElementObj{
//	墙需要血量
	private int hp;
	private String name;//墙的type

	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	}
	
	@Override
	public ElementObj createElement(String str) {
		String []arr = str.split(",");
		ImageIcon icon = null;
		switch(arr[0]) {
		case "GRASS":icon = new ImageIcon("image/wall/grass.png");break;
		case "BRICK":icon = new ImageIcon("image/wall/brick.png");break;
		case "RIVER":icon = new ImageIcon("image/wall/ricer.png");break;
		case "IRON":icon = new ImageIcon("image/wall/iron.png");
					this.hp=4;//设置血量
					name = "IRON";
					break;
		}
		int x=Integer.parseInt(arr[1]);
		int y=Integer.parseInt(arr[2]);
		int w = icon.getIconWidth();
		int h = icon.getIconHeight();
		this.setH(h);
		this.setW(w);
		this.setX(x);
		this.setY(y);
		this.setIcon(icon);
		return this;
	}
	@Override //说明这个设置扣血等的方法，需要自己思考修改重新编写
		public void setLive(boolean live) {
			if("IRON".equals(name)) {//水泥墙需要四枪
				this.hp--;
				if(this.hp>0) {
					return;
				}
			}
			super.setLive(live);
		}

}
