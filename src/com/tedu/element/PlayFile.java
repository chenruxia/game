//package com.tedu.element;
//
//import java.awt.Color;
//import java.awt.Graphics;
//
//import javax.swing.ImageIcon;
//
//import com.tedu.manager.ElementManager;
//import com.tedu.manager.GameElement;
//
///*
// * 玩家子弹类 本类的实体对象是由玩家对象调用和创建
// * 
// * */
//
//public class PlayFile extends ElementObj{
//	private int attack = 1;//攻击力
////	private int moveNum=30;//移动速度值
//	private String fx;
//	//可以扩展出多种子弹，玩家类就需要要有子弹类型
////	private PlayFile(int x,int y,int w,int h,ImageIcon icon, String fx) {
////		super(x,y,w,h,icon);
////		this.attack=1;
////		this.moveNum=3;
////		this.fx=fx;
////	}
//	public PlayFile() {
//		this.setSpeed(10);
//	}//一个空的构造方法
//	
//	@Override
//	public ElementObj createElement(String str) {
//		String[] split=str.split(",");
//		for(String str1:split) {
//			String[] split2 = str1.split(":");
//			switch(split2[0]) {
//			case "x":this.setX(Integer.parseInt(split2[1]));break;
//			case "y":this.setY(Integer.parseInt(split2[1]));break;
//			case "f":this.fx = split2[1];break;
//			}
//		}
//		this.setW(10);
//		this.setH(20);
//		return this;
//	}
//	@Override
//	public void showElement(Graphics g) {
//		g.setColor(Color.yellow);
////		int x=this.getX();
////		switch(this.fx) {
////		case"up": x+=20;break;
////		}
//		g.fillOval(this.getX(), this.getY(),this.getW(), this.getH());
//		
//	}
//	
//	@Override
//	protected void move() {
//		if(this.getY()<0) {
//			this.setLive(false);
//			return;
//		}
//		this.setY(this.getY()-this.getSpeed());
//	}
//	public int getAttack() {
//		return attack;
//	}
//	
////	@Override
////	protected void move() {
////		if(this.getX()<0 || this.getX()>755 ||
////				this.getY()<0 || this.getY()>600) {
////			this.setLive(false);
////			return;
////		}
////		
////		switch(this.fx) {
////		case "up":this.setY(this.getY()-this.moveNum);break;
////		case "left":this.setX(this.getX()-this.moveNum);break;
////		case "right":this.setX(this.getX()+this.moveNum);break;
////		case "down":this.setY(this.getY()+this.moveNum);break;
////		}
////	}
//	/*
//	 * 对于子弹来说：1.出边界 2.碰撞 3.玩家放保险
//	 * 处理方式就是，当达到死亡条件时，只进行修改死亡状态的操作。
//	 * */
////	@Override
////	public void die() {
////		ElementManager em=ElementManager.getManager();
////		ImageIcon icon=new ImageIcon("image/tank/play2/player2_up.png");
////		ElementObj obj=new Play(this.getX(),this.getY(),50,50,icon);
////		em.addElement(obj,GameElement.PLAY);
////	}
//	
////	/*
////	 * 子弹变装*/
////	private long time=0;
////	protected void updateImage(long gameTime) {
////		if(gameTime-time>20) {
////			time=gameTime;//为下一次便装做准备
////			this.setW(this.getW()+2);
////			this.setH(this.getH()+2);
////		}
////	}
//}



package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class PlayFile extends ElementObj {
    private int attack;      // 攻击力
    private String fx;       // 飞行方向
    private int bulletType;  // 子弹类型（1-32）
    private Color bulletColor; // 子弹颜色（备用）
    private Random random = new Random();

    public PlayFile() {
        this.setSpeed(10); // 默认速度
        this.attack = 1;   // 默认攻击力
    }

    @Override
    public ElementObj createElement(String str) {
    	//默认方向为“up”
    	this.fx = "up";
    	
        // 解析参数（格式示例："x:100,y:200,f:up,type:3"）
        String[] split = str.split(",");
        for(String param : split) {
            String[] keyValue = param.split(":");
            switch(keyValue[0]) {
                case "x": this.setX(Integer.parseInt(keyValue[1])); break;
                case "y": this.setY(Integer.parseInt(keyValue[1])); break;
                case "f": this.fx = keyValue[1]; break;
                case "type": 
                    this.bulletType = Integer.parseInt(keyValue[1]);
                    break;
            }
        }

        // 如果未指定类型，随机选择1-16
        if(bulletType < 1 || bulletType > 32) {
            bulletType = random.nextInt(16) + 1;
        }

        // 配置子弹属性
        configureBullet();
        
        this.setW(10);
        this.setH(20);
        return this;
    }

    private void configureBullet() {
    	
//    	根据子弹类型选择图片（1-32号）
    	String imgKey = "bullet" + bulletType;//对应fire文件夹中的图片
    	ImageIcon icon = GameLoad.imgMap.get(imgKey);
    	
    	if(icon != null) {
    		this.setIcon(icon);
    		this.setW(icon.getIconWidth());
    		this.setH(icon.getIconHeight());
    	}else {
//    		默认图形
    		setDefaultAppearance();
    	}
    	
    	switch(bulletType % 8) {
    	case 1:
    		this.attack = 1;
    		this.setSpeed(10);
    		break;
    	case 2:
    		this.attack = 1;
    		this.setSpeed(15);
    		break;
    	case 3:
    		this.attack = 1;
    		this.setSpeed(8);
    		break;
    	case 4:
    		this.attack = 2;
    		this.setSpeed(7);
    		break;
    	case 5:
    		this.attack = 1;
    		this.setSpeed(12);
    		break;
    	case 6:
    		this.attack = 3;
    		this.setSpeed(5);
    		break;
    	case 7:
    		this.attack = 1;
    		this.setSpeed(9);
    		break;
    	case 0:
    		this.attack = 2;
    		this.setSpeed(6);
    		break;
    	}
    	
    	
    	
//        // 尝试从资源加载图片
//        ImageIcon icon = GameLoad.imgMap.get("fire"+bulletType);
//        
//        if(icon != null) {
//            // 使用图片资源
//            this.setIcon(icon);
//            this.setW(icon.getIconWidth());
//            this.setH(icon.getIconHeight());
//        } else {
//            // 无图片资源时使用彩色图形
//            setDefaultAppearance();
//        }
//
//        // 根据类型设置特殊属性
//        switch(bulletType) {
//            case 1: case 2: case 3:   // 普通子弹
//                this.attack = 1;
//                this.setSpeed(10);
//                break;
//                
//            case 4: case 5: case 6:   // 快速子弹
//                this.attack = 1;
//                this.setSpeed(15);
//                break;
//                
//            case 7: case 8: case 9:   // 强力子弹
//                this.attack = 2;
//                this.setSpeed(8);
//                break;
//                
//            case 10: case 11: case 12: // 散射子弹
//                this.attack = 1;
//                this.setSpeed(12);
//                break;
//                
//            case 13: case 14: case 15: // 穿透子弹
//                this.attack = 1;
//                this.setSpeed(9);
//                break;
//                
//            case 16:                  // 特殊子弹
//                this.attack = 3;
//                this.setSpeed(7);
//                break;
//                
//            default:                  // 17-32号子弹
//                this.attack = 1 + (bulletType % 3);
//                this.setSpeed(8 + (bulletType % 5));
//        }
    }

    private void setDefaultAppearance() {
        // 无图片时使用彩色图形
        switch(bulletType % 7) {
            case 0: bulletColor = Color.YELLOW; break;
            case 1: bulletColor = Color.RED; break;
            case 2: bulletColor = Color.BLUE; break;
            case 3: bulletColor = Color.GREEN; break;
            case 4: bulletColor = Color.CYAN; break;
            case 5: bulletColor = Color.MAGENTA; break;
            case 6: bulletColor = Color.ORANGE; break;
        }
        
        // 设置默认大小
        if(bulletType <= 16) {
            this.setW(10); 
            this.setH(20);
        } else {
            this.setW(15);
            this.setH(25);
        }
    }

    @Override
    public void showElement(Graphics g) {
        if(this.getIcon() != null) {
            // 有图片资源时绘制图片
            g.drawImage(this.getIcon().getImage(), 
                       this.getX(), this.getY(), 
                       this.getW(), this.getH(), null);
        } else {
            // 无图片时绘制彩色图形
            g.setColor(bulletColor);
            
            // 根据类型绘制不同形状
            if(bulletType % 3 == 0) {
                g.fillRect(this.getX(), this.getY(), this.getW(), this.getH());
            } else if(bulletType % 3 == 1) {
                g.fillOval(this.getX(), this.getY(), this.getW(), this.getH());
            } else {
                int[] xPoints = {getX(), getX()+getW()/2, getX()+getW()};
                int[] yPoints = {getY()+getH(), getY(), getY()+getH()};
                g.fillPolygon(xPoints, yPoints, 3);
            }
        }
    }

    @Override
    protected void move() {
    	System.out.println("子弹方向: " + fx + ", 坐标: " + getX() + "," + getY());
    	if(fx==null) {
    		this.setLive(false);
    		return;
    	}
        // 根据方向移动
        switch(fx) {
            case "up":
                this.setY(this.getY() - this.getSpeed());
                if(this.getY() < 0) this.setLive(false);
                break;
                
            case "down":
                this.setY(this.getY() + this.getSpeed());
                if(this.getY() > 600) this.setLive(false);
                break;
                
            case "left":
                this.setX(this.getX() - this.getSpeed());
                if(this.getX() < 0) this.setLive(false);
                break;
                
            case "right":
                this.setX(this.getX() + this.getSpeed());
                if(this.getX() > 800) this.setLive(false);
                break;
        }
    }

    public int getAttack() {
        return attack;
    }
    
    // 子弹碰撞后是否消失（穿透弹不消失）
    public boolean isDisappearAfterHit() {
        return bulletType != 13 && bulletType != 14 && bulletType != 15;
    }
}