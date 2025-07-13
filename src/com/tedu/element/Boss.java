package com.tedu.element;

import java.awt.Graphics;
import java.util.Random;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.manager.SoundManager;

public class Boss extends ElementObj{
	
	private int hp = 50; // Boss血量
    private long lastShotTime = 0;
    private Random random = new Random();
    private int moveDirection = 1; // 1向右，-1向左
    private int phase = 1; // Boss阶段
    private int variant = 1;

    
    @Override
    public ElementObj createElement(String str) {
//        this.setIcon(GameLoad.imgMap.get("boss"));
//        this.setW(150);
//        this.setH(150);
//        this.setX(325);
//        this.setY(50);
//        this.setSpeed(2);
//        return this;
    	 updateImage(0);
         this.setW(200); this.setH(200);
         this.hp = 100 * phase;
         return this;
    }
    
    @Override
    protected void updateImage(long gameTime) {
        // 每阶段6种变体循环
        int frame = (int)(gameTime/15) % 6 + 1;
        String imgKey = "boss_phase"+phase+"_"+(6*(phase-1)+frame);
        this.setIcon(GameLoad.imgMap.get(imgKey));
    }
    
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), this.getX(),this.getY(),this.getW(),this.getH(), null);
	}

	
	@Override
    protected void move() {
        // 左右移动
        this.setX(this.getX() + moveDirection * this.getSpeed());
        
        // 到达边界改变方向
        if(this.getX() <= 0 || this.getX() >= 800 - this.getW()) {
            moveDirection *= -1;
        }
        
        // 偶尔上下移动
        if(random.nextInt(100) < 5) {
            int move = random.nextInt(3) - 1; // -1, 0, 1
            this.setY(this.getY() + move * this.getSpeed());
        }
    }
	
	@Override
    public void add(long gameTime) {
        // Boss射击逻辑
        if(gameTime - lastShotTime > 30) {
            lastShotTime = gameTime;
            
            
            int bulletType = 0;
            String pattern = null;
            
            switch(phase) {
            case 1:
            	bulletType = 17 + (int)(gameTime/50) % 5;
            	pattern = "single";
            	break;
            case 2:
            	bulletType = 22 +(int)(gameTime/50) % 5;
            	pattern = "triple";
            	break;
            case 3:
            	bulletType = 27 + (int)(gameTime/50) % 5;
            	pattern = "pentagon";
            	
            }
            
            switch(pattern) {
            case "single":
            	fireBullet(bulletType,0);
            	break;
            case "triple":
            	for(int i = -1;i<=1;i++) {
            		fireBullet(bulletType,i*30);
            	}
            	break;
            case "pentagon":
            	for(int i = -2;i<=2;i++) {
            		fireBullet(bulletType,i*15);
            	}
            	break;
            }
            
//            // 阶段1: 单发子弹
//            if(phase == 1) {
//                String bulletStr = "x:" + (this.getX() + this.getW()/2 - 5) + 
//                                  ",y:" + (this.getY() + this.getH()) + ",f:down";
//                ElementObj bullet = new EnemyFile().createElement(bulletStr);
//                ElementManager.getManager().addElement(bullet, GameElement.ENEMYFILE);
//            } 
//            // 阶段2: 三发子弹
//            else if(phase == 2) {
//                for(int i = -1; i <= 1; i++) {
//                    String bulletStr = "x:" + (this.getX() + this.getW()/2 - 5 + i*30) + 
//                                      ",y:" + (this.getY() + this.getH()) + ",f:down";
//                    ElementObj bullet = new EnemyFile().createElement(bulletStr);
//                    ElementManager.getManager().addElement(bullet, GameElement.ENEMYFILE);
//                }
//            }
        }
    }
	
	private void fireBullet(int bulletType, int offsetX) {
		String bulletStr = "x:"+(this.getX()+this.getW()/2-5+offsetX)+
				",y:"+(this.getY()+this.getH())+",type:"+bulletType;
//		如果是追踪弹，添加追踪标记
		if(bulletType >= 27) {
			bulletStr += ",tracking:true";
		}
		
		ElementObj bullet = new EnemyFile().createElement(bulletStr);
		ElementManager.getManager().addElement(bullet, GameElement.ENEMYFILE);
		
	}

	public void takeDamage(int damage) {
        hp -= damage;
        
        SoundManager.getInstance().playSound("explosion");
        
        if(hp <= 66 && phase == 1) {
            phase = 2; // 进入第二阶段
        } else if(hp <= 33 && phase == 2) {
            phase = 3; // 进入第三阶段
        }
    }

	public void setHp(int hp) {
		// TODO Auto-generated method stub
		this.hp = hp;
	}
}
