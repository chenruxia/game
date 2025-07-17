
package com.tedu.element;

import java.awt.Graphics;
import java.util.Random;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.manager.SoundManager;

import javax.swing.ImageIcon;

public class Boss extends ElementObj {

    private int hp = 50; // Boss血量
    private long lastShotTime = 0;
    private Random random = new Random();
    private int moveDirection = 1; // 1向右，-1向左
    private int phase = 1; // Boss阶段

    @Override
    public ElementObj createElement(String str) {
        updateImage(0);
        this.setW(200);
        this.setH(200);
        this.hp = 100 * phase;
        this.setX(300);
        this.setY(50);
        this.setSpeed(1);
        return this;
    }

    @Override
    protected void updateImage(long gameTime) {
        // 每阶段6种变体循环
        int frame = (int) (gameTime / 15) % 6 + 1;
        String imgKey = "boss_phase" + phase + "_" + (6 * (phase - 1) + frame);
        ImageIcon icon = GameLoad.imgMap.get(imgKey);
        if (icon != null) {
            this.setIcon(icon);
        }
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    protected void move() {
        int speed = getSpeed();
        if (ElementManager.getManager().isGlobalSlow()) {
            speed = Math.max(1, speed / 2);
        }
        // 左右移动
        this.setX(this.getX() + moveDirection * speed);

        // 到达边界改变方向
        if (this.getX() <= 0 || this.getX() >= 800 - this.getW()) {
            moveDirection *= -1;
        }

        // 偶尔上下移动
        if (random.nextInt(100) < 5) {
            int move = random.nextInt(3) - 1; // -1, 0, 1
            this.setY(this.getY() + move * speed);
        }
    }

    
    @Override
    public void add(long gameTime) {
        if(gameTime - lastShotTime > 60) {
            lastShotTime = gameTime;
            int bulletType = 17 + (int)(gameTime/50) % 5;
            switch(phase) {
                case 1: // 单发直线
                    fireBullet(bulletType, 0, "straight",true);
                    break;
                case 2: // 扇形（可改为多发直线或多发曲线）
                    fireBullet(bulletType, 0, "straight",true);
                    fireBullet(bulletType, 0, "curve",true);
                    break;
                case 3: // 只用曲线弹
                    fireBullet(bulletType, 0, "curve",true);
                    break;
            }
        }
    }

    private void fireBullet(int bulletType, int angle, String mode, boolean tracking) {
        int x = getX() + getW() / 2 - 5;
        int y = getY() + getH();
        String bulletStr = "x:" + x + ",y:" + y + ",type:" + bulletType + ",angle:" + angle + ",mode:" + mode;
        if (tracking) bulletStr += ",tracking:true";
        ElementObj bullet = new EnemyFile().createElement(bulletStr);
        ElementManager.getManager().addElement(bullet, GameElement.ENEMYFILE);
    }
    

    public void takeDamage(int damage) {
        hp -= damage;
        SoundManager.getInstance().playSound("explosion");
        if (hp <= 66 && phase == 1) {
            phase = 2; // 进入第二阶段
        } else if (hp <= 33 && phase == 2) {
            phase = 3; // 进入第三阶段
        }
        if (hp <= 0) {
            this.setLive(false);
        }
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
