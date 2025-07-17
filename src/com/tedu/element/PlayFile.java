

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
    private String mode = "straight"; // straight, fan, circle, curve, bomb
    private double angle = 90; // 90度向上
    private int tick = 0;
    private int bombRadius = 60; // 爆炸半径

    public PlayFile() {
        this.setSpeed(10); // 默认速度
        this.attack = 1;   // 默认攻击力
    }

    @Override
    public ElementObj createElement(String str) {
        this.fx = "up";
        this.mode = "straight";
        this.angle = 90;
        this.bombRadius = 60;
        // 解析参数
        String[] split = str.split(",");
        for(String param : split) {
            String[] kv = param.split(":");
            if(kv[0].equals("angle")) angle = Double.parseDouble(kv[1]);
            if(kv[0].equals("mode")) mode = kv[1];
            if(kv[0].equals("radius")) bombRadius = Integer.parseInt(kv[1]);
            switch(kv[0]) {
                case "x": this.setX(Integer.parseInt(kv[1])); break;
                case "y": this.setY(Integer.parseInt(kv[1])); break;
                case "f": this.fx = kv[1]; break;
                case "type": this.bulletType = Integer.parseInt(kv[1]); break;
            }
        }
        if(bulletType < 1 || bulletType > 32) {
            bulletType = random.nextInt(16) + 1;
        }
        configureBullet();
        this.setW(10);
        this.setH(20);
        return this;
    }

    private void configureBullet() {
        String imgKey = "bullet" + bulletType;
        ImageIcon icon = GameLoad.imgMap.get(imgKey);
        if(icon != null) {
            this.setIcon(icon);
            this.setW(icon.getIconWidth());
            this.setH(icon.getIconHeight());
        } else {
            setDefaultAppearance();
        }
        switch(bulletType % 8) {
            case 1: this.attack = 1; this.setSpeed(10); break;
            case 2: this.attack = 1; this.setSpeed(8); break;
            case 3: this.attack = 1; this.setSpeed(6); break;
            case 4: this.attack = 2; this.setSpeed(7); break;
            case 5: this.attack = 1; this.setSpeed(12); break;
            case 6: this.attack = 3; this.setSpeed(5); break;
            case 7: this.attack = 1; this.setSpeed(9); break;
            case 0: this.attack = 2; this.setSpeed(6); break;
        }
        // 爆炸弹可适当降低速度
        if(mode.equals("bomb")) this.setSpeed(4);
    }

    private void setDefaultAppearance() {
        switch(bulletType % 7) {
            case 0: bulletColor = Color.YELLOW; break;
            case 1: bulletColor = Color.RED; break;
            case 2: bulletColor = Color.BLUE; break;
            case 3: bulletColor = Color.GREEN; break;
            case 4: bulletColor = Color.CYAN; break;
            case 5: bulletColor = Color.MAGENTA; break;
            case 6: bulletColor = Color.ORANGE; break;
        }
        if(bulletType <= 16) {
            this.setW(10); this.setH(20);
        } else {
            this.setW(15); this.setH(25);
        }
    }

    @Override
    public void showElement(Graphics g) {
        if(this.getIcon() != null) {
            g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
        } else {
            g.setColor(bulletColor);
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
        tick++;
        int speed = getSpeed();
        double rad = Math.toRadians(angle);
        int dx = 0, dy = 0;
        switch(mode) {
            case "straight":
                dx = (int)(Math.cos(rad) * speed);
                dy = (int)(-Math.sin(rad) * speed);
                break;
            case "fan":
                dx = (int)(Math.cos(rad) * speed);
                dy = (int)(-Math.sin(rad) * speed);
                break;
            case "circle":
                dx = (int)(Math.cos(rad) * speed);
                dy = (int)(-Math.sin(rad) * speed);
                break;
            case "curve":
                dx = (int)(Math.sin(tick / 10.0) * 5);
                dy = -speed;
                break;
            case "bomb":
                dx = 0;
                dy = -speed;
                break;
        }
        setX(getX() + dx);
        setY(getY() + dy);
        if(getY() < 0 || getX() < 0 || getX() > 800) setLive(false);
    }

    public int getAttack() {
        return attack;
    }

    public String getMode() {
        return mode;
    }

    public int getBombRadius() {
        return bombRadius;
    }

    // 子弹碰撞后是否消失（穿透弹不消失）
    public boolean isDisappearAfterHit() {
        return bulletType != 13 && bulletType != 14 && bulletType != 15;
    }
}
