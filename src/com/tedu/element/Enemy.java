//package com.tedu.element;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.util.Random;
//
//import javax.swing.ImageIcon;
//
//import com.tedu.manager.ElementManager;
//import com.tedu.manager.GameElement;
//import com.tedu.manager.GameLoad;
//import com.tedu.manager.SoundManager;
//
//public class Enemy extends ElementObj{
//	
//	private int type;//敌机类型
//	private int hp;//敌机血量
//	private long lastShotTime = 0;//上次射击时间
//	private Random random = new Random();
//	
//	// 敌机类型1-5：小型机 | 6-10：中型机 | 11-13：精英机
//    private int enemyType; 
//    
//    
//    @Override
//    public ElementObj createElement(String str) {
//        String[] split = str.split(",");
//        this.enemyType = Integer.parseInt(split[0]);
//        this.setX(Integer.parseInt(split[1]));
//        this.setY(Integer.parseInt(split[2]));
//        
//        // 根据类型设置属性
//        if(enemyType <= 5) {
//            initSmallEnemy();
//        } else if(enemyType <= 10) {
//            initMediumEnemy();
//        } else {
//            initEliteEnemy();
//        }
//        
//        String imgKey = "enemy"+enemyType;
//        this.setIcon(GameLoad.imgMap.get(imgKey));
//        
////        this.setIcon(GameLoad.imgMap.get("enemy"+enemyType));
//        if(this.getIcon() == null) {
//        	System.out.println("敌机图片加载失败："+imgKey);
//        	this.setW(40);
//        	this.setH(40);
//        }
//        return this;
//    }
//    
//    private void initEliteEnemy() {
//    	 this.setW(60); this.setH(60);
//         this.setSpeed(3); this.hp = 5;
//		
//	}
//
//	private void initMediumEnemy() {
//    	 this.setW(50); this.setH(50);
//         this.setSpeed(3); this.hp = 3;
//		
//	}
//
//	private void initSmallEnemy() {
//        this.setW(40); this.setH(40);
//        this.setSpeed(3); this.hp = 1;
//    }
//
//	@Override
//	public void showElement(Graphics g) {
//	    if(this.getIcon() == null || this.getIcon().getImage() == null) {
//	        // 图像加载失败时绘制红色矩形
//	        g.setColor(Color.RED);
//	        g.fillRect(this.getX(), this.getY(), this.getW(), this.getH());
//	        System.err.println("敌机图像未加载: " + enemyType);
//	    } else {
//	        g.drawImage(this.getIcon().getImage(),
//	                   this.getX(), this.getY(), 
//	                   this.getW(), this.getH(), null);
//	    }
//	}
//	@Override
//	protected void move() {
//		this.setY(this.getY()+this.getSpeed());
//		//随机左右移动
//		if(random.nextInt(100)<5) {
//			int move = random.nextInt(3)-1;
//			this.setX(this.getX()+move*this.getSpeed());
//		}
//		//检查是否超出屏幕
//		if(this.getY()>600 || this.getX()<0 || this.getX()>800) {
//			this.setLive(false);
//		}
//	}
//	
//	@Override
//	public void add(long gameTime) {
//		//所有类型敌机都会发射子弹，频率和概率可调整
//		if(gameTime-lastShotTime > getShootInterval()) {
//			lastShotTime = gameTime;
//			
//			
//			int bulletType = getBulletTypeByEnemy();
////			从敌机底部中央发射子弹
//			String bulletStr = "x:"+(getX()+getW()/2-5)+",y:"+(getY()+getH())+
//					",type:"+bulletType;
//			ElementObj bullet = new EnemyFile().createElement(bulletStr);
//			ElementManager.getManager().addElement(bullet, GameElement.ENEMYFILE);
////			//50%概率发射子弹
////			if(random.nextInt(100)<50) {
////				String bulletStr = "x:"+(this.getX()+this.getW()/2-5)+
////						",y:"+(this.getH())+",f:down"; 
////				ElementObj bullet = new EnemyFile().createElement(bulletStr);
////				ElementManager.getManager().addElement(bullet, GameElement.ENEMYFILE);
////			}
//		}
//	}
//	
//	private int getBulletTypeByEnemy() {
//		if(enemyType <= 5) {
//			return 1+(enemyType % 3);
//		}else if(enemyType <= 10) {
//			return 4 + (enemyType % 4);
//		}else {
//			return 8 + (enemyType % 5);
//		}
//	}
//	
//	private int getShootInterval() {
//		return enemyType <= 5 ? 50:
//			enemyType <= 10 ? 30 : 15;
//	}
//	
//	
//	public void takeDamage(int damage) {
//        hp -= damage;
//        
//        SoundManager.getInstance().playSound("explosion");
//        
//        if(hp <= 0) {
//            this.setLive(false);
//            // 死亡时有几率掉落道具
//            if(random.nextInt(100) < (enemyType <= 10 ? 30:100)) { // 20%几率掉落道具
//            	int propType = random.nextInt(7)+1;
//            	
//                String itemStr = "type:" + propType + ",x:" + this.getX()+",y:"+this.getY();
////                ElementObj item = new Item().createElement(itemStr);
//                ElementManager.getManager().addElement(new Item().createElement(itemStr), GameElement.ITEM);
//            }
//        }
//    }
//
////	@Override
////	public void showElement(Graphics g) {
////		// TODO Auto-generated method stub
////		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
////	}
////	@Override
////	public ElementObj createElement(String str) {
////		Random ran = new Random();
////		int x=ran.nextInt(800);
////		int y=ran.nextInt(500);
////		this.setX(x);
////		this.setY(y);
////		this.setW(50);
////		this.setH(50);
////		this.setIcon(new ImageIcon("image/tank/bot/bot_up.png"));
////		return this;
////}
//	
//
//
//}


package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.manager.SoundManager;

public class Enemy extends ElementObj {

    private int type; // 敌机类型
    private int hp;   // 敌机血量
    private long lastShotTime = 0; // 上次射击时间
    private Random random = new Random();

    // 敌机类型1-5：小型机 | 6-10：中型机 | 11-13：精英机
    private int enemyType;
    private int moveType = 0; // 0直线 1S型 2Z型 3螺旋

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        this.enemyType = Integer.parseInt(split[0]);
        this.setX(Integer.parseInt(split[1]));
        this.setY(Integer.parseInt(split[2]));

        // 随机分配移动轨迹
        this.moveType = random.nextInt(4);

        // 根据类型设置属性
        if (enemyType <= 5) {
            initSmallEnemy();
        } else if (enemyType <= 10) {
            initMediumEnemy();
        } else {
            initEliteEnemy();
        }

        String imgKey = "enemy" + enemyType;
        this.setIcon(GameLoad.imgMap.get(imgKey));
        if (this.getIcon() == null) {
            System.out.println("敌机图片加载失败：" + imgKey);
            this.setW(40);
            this.setH(40);
        }
        return this;
    }
    
    public void setHp(int hp) { this.hp = hp; }
    
    private void initEliteEnemy() {
        this.setW(60);
        this.setH(60);
        this.setSpeed(1);
        this.hp = 5;
    }

    private void initMediumEnemy() {
        this.setW(50);
        this.setH(50);
        this.setSpeed(1);
        this.hp = 3;
    }

    private void initSmallEnemy() {
        this.setW(40);
        this.setH(40);
        this.setSpeed(1);
        this.hp = 1;
    }

    @Override
    public void showElement(Graphics g) {
        if (this.getIcon() == null || this.getIcon().getImage() == null) {
            g.setColor(Color.RED);
            g.fillRect(this.getX(), this.getY(), this.getW(), this.getH());
            System.err.println("敌机图像未加载: " + enemyType);
        } else {
            g.drawImage(this.getIcon().getImage(),
                    this.getX(), this.getY(),
                    this.getW(), this.getH(), null);
        }
    }

    @Override
    protected void move() {
        int speed = getSpeed();
        if (ElementManager.getManager().isGlobalSlow()) {
            speed = Math.max(1, speed / 2);
        }
        switch (moveType) {
            case 0: // 直线
                this.setY(this.getY() + speed);
                break;
            case 1: // S型
                this.setY(this.getY() + speed);
                this.setX(this.getX() + (int) (10 * Math.sin(this.getY() / 20.0)));
                break;
            case 2: // Z型
                this.setY(this.getY() + speed);
                this.setX(this.getX() + ((this.getY() / 30) % 2 == 0 ? 5 : -5));
                break;
            case 3: // 螺旋
                this.setY(this.getY() + speed);
                this.setX(this.getX() + (int) (10 * Math.cos(this.getY() / 10.0)));
                break;
        }
        // 检查是否超出屏幕
        if (this.getY() > 600 || this.getX() < 0 || this.getX() > 800) {
            this.setLive(false);
        }
    }


    
    @Override
    public void add(long gameTime) {
        if(gameTime - lastShotTime > getShootInterval()) {
            lastShotTime = gameTime;
            int bulletType = getBulletTypeByEnemy();
            int modeType = enemyType % 2; // 0直线 1曲线
            switch(modeType) {
                case 0: // 直线
                    fireBullet(bulletType, 0, "straight",true);
                    break;
                case 1: // 曲线
                    fireBullet(bulletType, 0, "curve",true);
                    break;
            }
        }
    }

    private void fireBullet(int bulletType, int angle, String mode, boolean tracking) {
        int x = getX() + getW() / 2 - 5;
        int y = getY() + getH();
        String bulletStr = "x:" + x + ",y:" + y + ",type:" + bulletType + ",angle:" + angle + ",mode:" + mode+ ",tracking:"+tracking;
        if (tracking) bulletStr += ",tracking:true";
        ElementObj bullet = new EnemyFile().createElement(bulletStr);
        ElementManager.getManager().addElement(bullet, GameElement.ENEMYFILE);
    }

    private int getBulletTypeByEnemy() {
        if (enemyType <= 5) {
            return 1 + (enemyType % 3);
        } else if (enemyType <= 10) {
            return 4 + (enemyType % 4);
        } else {
            return 8 + (enemyType % 5);
        }
    }

    private int getShootInterval() {
        return enemyType <= 5 ? 120 :
                enemyType <= 10 ? 90 : 60;
    }

    public void takeDamage(int damage) {
        hp -= damage;
        SoundManager.getInstance().playSound("explosion");
        if (hp <= 0) {
            this.setLive(false);
            // 死亡时有几率掉落道具
            if (random.nextInt(100) < (enemyType <= 10 ? 30 : 100)) {
                int propType = random.nextInt(10) + 1; // 支持10种道具
                String itemStr = "type:" + propType + ",x:" + this.getX() + ",y:" + this.getY();
                ElementManager.getManager().addElement(new Item().createElement(itemStr), GameElement.ITEM);
            }
        }
    }
}
