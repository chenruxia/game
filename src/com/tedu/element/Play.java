//package com.tedu.element;
//
//import java.awt.Graphics;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.swing.ImageIcon;
//
//import com.tedu.manager.ElementManager;
//import com.tedu.manager.GameElement;
//import com.tedu.manager.GameLoad;
//
//public class Play extends ElementObj{
//	/*
//	 * 移动属性：
//	 * 1.单属性 配合 方向枚举类型使用；一次只能移动一个方向
//	 * 2.双属性 上下 和 左右 配合布尔值使用 需要另一个变量来确定是否按下方向键
//	 * 
//	 * 
//	 * @问题1.图片要读取到内存中：加载器 临时处理方式，手动编写存储到内存中
//	 * 2.什么时候进行修改图片
//	 * 3.图片用什么集合进行存储
//	 * 							
//	 * */
//	
//	private boolean left = false;
//	private boolean up = false;
//	private boolean right = false;
//	private boolean down = false;
//	
//	
//	//变量专门用来记录当前主角面向的方向，默认是up
//	private String fx="up";
//	private boolean pkType=false;//攻击状态true攻击，false停止
//	
//	
//	private int life=3;//玩家生命值
//	private int power=1;//玩家火力等级
//	
//	
//	public Play() {
//		this.setSpeed(5);
//	}
////	public Play(int x, int y, int w, int h, ImageIcon icon) {
////		super(x, y, w, h, icon);	
////	}
//	
//	@Override
//	public ElementObj createElement(String str) {
//		String[] split = str.split(",");
//		this.setX( new Integer(split[0]));
//		this.setY( new Integer(split[1]));
//		ImageIcon icon2 = GameLoad.imgMap.get(split[2]);
//		this.setW(icon2.getIconWidth());
//		this.setH(icon2.getIconHeight());
//		this.setIcon(icon2);
//		return this;
//	}
//	
//	
//	@Override
//	public void showElement(Graphics g) {
//		g.drawImage(this.getIcon().getImage(), 
//				this.getX(), this.getY(), 
//				this.getW(), this.getH(), null);
//	}
//	
//	/*
//	 * 重写方法
//	 * 监听的数据需要改变状态值
//	 * */
//	@Override
//	public void keyClick(boolean bl, int key) {
////		System.out.println("测试："+key);
//		if(bl) {
//			switch(key) {
//			case 37:this.left = true; break;
//			case 38:this.up = true; break;
//			case 39:this.right = true; break;
//			case 40:this.down = true; break;
//			case 32:this.pkType = true; break;
//			}
//		}else {
//			switch(key) {
//			case 37:this.left = false; break;
//			case 38:this.up = false; break;
//			case 39:this.right = false; break;
//			case 40:this.down = false; break;
//			case 32:this.pkType = false; break;
//			}
//		}
////			//按下
////			switch(key) {
////			case 37:
////				this.down=false;this.up=false;
////				this.right=false;this.left=true;this.fx="left"; 
////				break;
////			case 38:
////				this.right=false;this.left=false;
////				this.down=false;this.up=true;this.fx="up";
////				break;
////			case 39:
////				this.down=false;this.up=false;
////				this.left=false;this.right=true;this.fx="right"; 
////				break;
////			case 40:
////				this.right=false;this.left=false;
////				this.up=false;this.down=true;this.fx="down";
////				break;
////			case 32:
////				this.pkType=true;break;//开启攻击状态
////			}
////		}else {
////			switch(key) {
////			case 37:this.left=false;break;
////			case 38:this.up=false; break;
////			case 39:this.right=false; break;
////			case 40:this.down=false; break;
////			case 32:this.pkType=false;break;
////			}
//		
//	}
////		
//
//		@Override
//		public void move() {
//			if (this.left && this.getX()>0) {
//				this.setX(this.getX() - this.getSpeed());
//			}
//			if (this.up && this.getY()>0) {
//				this.setY(this.getY() - this.getSpeed());
//			}
//			if (this.right && this.getX()<800-this.getW()) {//坐标的调整
//				this.setX(this.getX() + this.getSpeed());
//			}
//			if (this.down && this.getY()<600-this.getH()) {
//				this.setY(this.getY() + this.getSpeed());
//			}
//
//		}
//		
//		@Override
//		protected void updateImage() {
////			this.setIcon(GameLoad.imgMap.get(fx));
//		}
//		
//		/*
//		 * 重写规则：1.重写方法的方法名称和返回值必须和父类一样
//		 * 2.重写的方法的传入参数类型序列，必须和父类一样
//		 * 3.重写的方法访问修饰符只能比父类的更加宽泛
//		 * 4。重写的方法抛出的异常不可以比父类更加宽泛
//		 * */
//		private long filetime=0;
//		@Override//添加子弹
//		public void add(long gameTime) {
//			if(!this.pkType || gameTime-filetime<10) {
//				return;
//			}
//			filetime = gameTime;
////			this.pkType = false;//按一次，发射一个子弹，拼手速（也可以增加变量来控制）
////			ElementObj element = new PlayFile().createElement(this.toString());
////			ElementManager.getManager().addElement(element, GameElement.PLAYFILE);
//			for(int i=0;i<power;i++) {
//				int offsetX = (i-(power-1)/2)*20;
//				String bulletStr = "x:"+(this.getX()+this.getW()/2-5+offsetX)+
//						",y:"+(this.getY()-10)+",f:up";
//				ElementObj bullet = new PlayFile().createElement(bulletStr);
//				ElementManager.getManager().addElement(bullet, GameElement.PLAYFILE);
//			}
//			
//		}
////		@Override
////		public String toString() {
////			int x=this.getX();
////			int y=this.getY();
////			switch(this.fx) {//子弹再发射时候就已经给予固定的·1轨迹。可以加上目标，修改json格式
////			case "up":x+=20;break;//一般情况下可以使用图片大小参与运算
////			case "left":y+=20;break;
////			case "right":x+=50;y+=20;break;
////			case "down":y+=50;x+=20;break;
////			}
////			return "x:"+x+",y:"+y+",f:"+this.fx;
////		}
//		public void increasePower() {
//			if(power<3) {
//				power++;
//			}
//		}
//		
//		public void decreaseLife() {
//			life--;
//			if(life<=0) {
//				this.setLive(false);
//			}
//		}
//		public int getLife() {
//			return life;
//		}
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

public class Play extends ElementObj {
    // 控制状态
    private boolean left = false;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;
    private boolean pkType = false;
    
    // 飞机属性
    private int currentFrame = 1;    // 当前显示帧（对应图片编号）
    private int animationSpeed = 8;  // 动画速度（数值越大切换越慢）
    private int life = 10;            // 生命值
    private int power = 1;           // 火力等级
    private long filetime = 0;       // 射击计时
    private Random random = new Random();
    
    private boolean isInvincible = false;
    private boolean isDoubleScore = false;
    private boolean isAutoFire = false;
    private long powerUpEndTime = 0;
    private int originalSpeed;
    private int superWeaponCount = 0;
    
    
    private int maxHealth;
    private int currentHealth;
    private int attack;
    private int fireRate; // 射击速度等级
    
 // 添加这些setter方法
    public void setMaxHealth(int health) {
        this.maxHealth = health;
        this.currentHealth = health;
    }
    
    public void setAttack(int attack) {
        this.attack = attack;
    }
    
    public void setFireRate(int rate) {
        this.fireRate = rate;
    }
    

    
    
    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        
        if(split.length > 2) {
        	String shipType = split[2];
        	this.setIcon(GameLoad.imgMap.get(shipType));
        }else {
        	this.setIcon(GameLoad.imgMap.get("player_1"));
        }
        
        
        // 初始化使用第一张图片
//        updateImage(0);
        this.setW(this.getIcon().getIconWidth());
        this.setH(this.getIcon().getIconHeight());
        this.setSpeed(5);
        
        return this;
    }

    @Override
    public void showElement(Graphics g) {
    	
    	
    	g.setColor(Color.red);
    	for(int i=0;i<life;i++) {
    		g.fillRect(getX()+i*15, getY()-20, 10, 5);
    	}
        // 绘制当前帧图像
        g.drawImage(this.getIcon().getImage(), 
                  this.getX(), this.getY(), 
                  this.getW(), this.getH(), null);
    }

    @Override
    public void keyClick(boolean bl, int key) {
        if(bl) { // 按键按下
            switch(key) {
                case 37: left = true; break;
                case 38: up = true; break;
                case 39: right = true; break;
                case 40: down = true; break;
                case 32: pkType = true; break;
            }
        } else { // 按键释放
            switch(key) {
                case 37: left = false; break;
                case 38: up = false; break;
                case 39: right = false; break;
                case 40: down = false; break;
                case 32: pkType = false; break;
            }
        }
    }

    @Override
    protected void move() {
        // 移动逻辑保持不变
        if(left && getX() > 0) setX(getX() - getSpeed());
        if(up && getY() > 0) setY(getY() - getSpeed());
        if(right && getX() < 800-getW()) setX(getX() + getSpeed());
        if(down && getY() < 600-getH()) setY(getY() + getSpeed());
    }

    @Override
    protected void updateImage(long gameTime) {
        // 动态切换21张飞机图片
        currentFrame = (int)(gameTime/animationSpeed) % 21 + 1;
        
        // 根据编号获取对应图片（支持.gif和.png）
        String imgKey = "player_" + currentFrame;
        ImageIcon icon = GameLoad.imgMap.get(imgKey);
        
        // 如果找不到则使用默认图
        if(icon == null) {
            icon = GameLoad.imgMap.get("player_1");
        }
        this.setIcon(icon);
    }

    @Override
    public void add(long gameTime) {
    	
    	if(!isAutoFire && !pkType) return;
    	if(gameTime - filetime < (isAutoFire ? 5:10)) return;
    	
       
        filetime = gameTime;
        SoundManager.getInstance().playSound("shoot");
        
        if(superWeaponCount > 0) {
        	fireSuperWeapon();
        	superWeaponCount--;
        	if(superWeaponCount == 0) {
//        		恢复普通外观
        		currentFrame = power;
        		updateImage(0);
        	}
        }else {
        	fireNormalWeapon();
        }
        
//        // 根据当前飞机图片决定子弹类型（示例：奇数图用类型1，偶数用类型2）
//        int bulletType = (currentFrame % 2) + 1;
//        
//        for(int i=0; i<power; i++) {
//            int offsetX = (i - (power-1)/2) * 15;
//            String bulletStr = "x:" + (getX()+getW()/2-5+offsetX) + 
//                             ",y:" + (getY()-10) + 
//                             ",type:" + bulletType;
//            
//            ElementObj bullet = new PlayFile().createElement(bulletStr);
//            ElementManager.getManager().addElement(bullet, GameElement.PLAYFILE);
//        }
    }
    
    
    private void fireNormalWeapon() {
//		360度全向射击
    	for(int i = 0;i<36;i++) {
    		double angle = Math.toRadians(i*10);
    		int offsetX = (int)(Math.cos(angle)*20);
    		int offsetY = (int)(Math.sin(angle)*20);
    		
    		String bulletStr = "x:"+(getX() + getW()/2 - 5+offsetX)+
    				",y:"+(getY()+getH()/2+offsetY)+
    				",type:"+(currentFrame % 16 + 1);
    		ElementObj bullet = new PlayFile().createElement(bulletStr);
    		ElementManager.getManager().addElement(bullet, GameElement.PLAYFILE);
    		SoundManager.getInstance().playSound("shoot");
    	}
	}

	private void fireSuperWeapon() {
//		360度全向射击
		for(int i = 0;i<36;i++) {
			double angle = Math.toRadians(i*10);
			int offsetX = (int)(Math.cos(angle)*20);
			int offsetY = (int)(Math.sin(angle)*20);
			
			String bulletStr = "x:"+(getX()+getW()/2-5+offsetX)+
					",y:"+(getY()+getH()/2+offsetY)+",type:16";
			ElementObj bullet = new PlayFile().createElement(bulletStr);
            ElementManager.getManager().addElement(bullet, GameElement.PLAYFILE);
		}
		
	}

	//在Play类中添加以下方法
    public void applyItemEffect(int itemType) {
    	switch(itemType) {
    	case 1:
    		increasePower();
    		break;
    	case 2:
    		life = Math.min(life+1, 3);
    		break;
    	case 3:
    		startInvincible(3000);
    		break;
    	case 4:
    		clearAllEnemies();
    		break;
    	case 5:
    		activateDoubleScore(10000);
    		break;
    	case 6:
    		activateAutoFire(5000);
    		break;
    	case 7:
    		activateSuperWeapon();
    		break;
    	}
    }
    
    
    public void activateSuperWeapon() {
		superWeaponCount = 3;
		currentFrame = 20;
		updateImage(0);
		
	}

	public void activateAutoFire(long duration) {
		isAutoFire = true;
		originalSpeed = getSpeed();
		setSpeed(originalSpeed+2);
		new Thread(()->{
			try {
				Thread.sleep(duration);
				isAutoFire = false;
				setSpeed(originalSpeed);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		
	}

	private void activateDoubleScore(long duration) {
		isDoubleScore = true;
		new Thread(()->{
			try {
				Thread.sleep(duration);
				isDoubleScore = false;
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		
	}

	private void clearAllEnemies() {
		ElementManager em = ElementManager.getManager();
		//清除所有敌人和子弹
		em.getElementsByKey(GameElement.ENEMY).clear();
		em.getElementsByKey(GameElement.ENEMYFILE).clear();
		
		for(int i = 0;i<10;i++) {
			ElementObj explosion = createExplosionEffect();
			em.addElement(explosion, GameElement.DIE);
		}
		
	}

	private ElementObj createExplosionEffect() {
		Random rand = new Random();
		int x = rand.nextInt(800);
		int y = rand.nextInt(400);
		
		return new Explosion().createElement(x+","+y);
	}
	
	

	public void startInvincible(long duration) {
		if(isInvincible) return;
		isInvincible = true;
		new Thread(()->{
			long startTime = System.currentTimeMillis();
			long endTime = startTime + duration;
			
			//闪烁效果
			while(System.currentTimeMillis() < endTime) {
				setVisible(!isVisible());
				try {
					Thread.sleep(100);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			//恢复状态
			setVisible(true);
			isInvincible = false;
			
		}).start();
		
		
	}

	private boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	private void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	
	// 修改碰撞检测
    @Override
    public boolean pk(ElementObj obj) {
        if(isInvincible) return false; // 无敌状态下不受伤害
        return super.pk(obj);
    }

    // 获取当前是否是双倍分数状态
    public boolean isDoubleScore() {
        return isDoubleScore;
    }
	
	

	// 受伤时切换到特定帧（例如后三张作为受伤效果）
    public void decreaseLife() {
        life--;
        if(life <= 0) {
            setLive(false);
        }
//        } else {
//            // 使用19-21作为受伤效果图
//            currentFrame = 19 + random.nextInt(3);
//            updateImage(0);
//        }
    }

    // 增强火力时切换到特定帧（例如前3张作为强化效果）
    public void increasePower() {
        if(power < 3) {
            power++;
            currentFrame = power; // 1-3对应火力等级
            updateImage(0);
        }
    }

    public int getLife() { return life; }
    public int getPower() { return power; }

	public void restoreLife() {
		life = Math.min(life+1, 3);
		
	}
}