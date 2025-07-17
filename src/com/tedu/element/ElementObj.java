package com.tedu.element;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public abstract class ElementObj {

	private int x;
	private int y;
	private int w;
	private int h;
	private ImageIcon icon;
	
	private boolean live=true;//生存状态true代表存在，false代表死亡
	//可以采用枚举值来定义这个（生存，死亡，隐身，无敌）
	
	private int speed = 3;

	public ElementObj() {}
	public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.icon = icon;
	}
	public abstract void showElement(Graphics g);
	
	
	//使用父类定义接收方法(第一种方式)
	//第二种方式 使用接口的方式，需要在监听类进行类型转换
	//@Param bl点击的类型 true代表按下，false代表松开
	//@Param key代表触发的键盘的code值
	public void keyClick(boolean bl, int key) {
		System.out.println("测试使用");
	}
	/*
	 *移动方法：需要移动的子类，请重写这个方法
	 * */
	protected void move() {
		
	}
	
	/*
	 * @设计模式 模板模式；在模板模式中定义 对象执行方法的先后顺序，由子类选择性重写方法
	 * 		1.移动 2.换装 3.子弹发射
	 * */
	public final void model(long gameTime) {
//		先换装
		updateImage();
//		再移动
		move();
//		再发射子弹
		add(gameTime);
	}
	
	
	protected void updateImage() {}
	protected void add(long gameTime) {}
	
	//死亡方法 给子类继承的
	public void die() {
		
	}
	
	public ElementObj createElement(String str) {
		System.out.println("创建玩家战机，类型: " + str);
		
		return null;
	}
	
	/*
	 * @说明 本方法返回元素的碰撞矩形对象（实时返回）
	 * */
	public Rectangle getRectangle() {
		//可以对这个数据进行处理
		return new Rectangle(x,y,w,h);
	}
	/*
	 * @说明碰撞方法
	 * 一个this对象 一个是传入值obj
	 * @param obj
	 * @return boolean 返回true说明有碰撞，返回false说明没有碰撞*/
	public boolean pk(ElementObj obj) {
		return this.getRectangle().intersects(obj.getRectangle());
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	protected void updateImage(long gameTime) {
		// TODO Auto-generated method stub
		
	}
	

	
	
	
	
}

