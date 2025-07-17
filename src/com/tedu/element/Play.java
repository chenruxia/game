

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
    private String bulletMode = "straight";
    public void setBulletMode(String mode) { this.bulletMode = mode; }

    // 飞机属性
    private int currentFrame = 1;
    private int animationSpeed = 8;
    private int life = 10;
    private int power = 1;
    private long filetime = 0;
    private Random random = new Random();

    private boolean isInvincible = false;
    private boolean isDoubleScore = false;
    private boolean isAutoFire = false;
    private boolean isShield = false;
    private boolean isSlowTime = false;
    private long powerUpEndTime = 0;
    private int originalSpeed;
    private int superWeaponCount = 0;

    private int maxHealth;
    private int currentHealth;
    private int attack;
    private int fireRate;
    
    private int maxLife = 10;   // 最大生命值，初始值可根据你的游戏设定调整
    private int maxPower = 5;  // 最大能量值，初始值可根据你的游戏设定调整

    private int playerId = 0; // 支持双人
    
    public int getMaxLife() {
        return maxLife;
    }

    public int getMaxPower() {
        return maxPower;
    }
    
    public void setPlayerId(int id) { this.playerId = id; }
    public int getPlayerId() { return playerId; }

    public void setMaxHealth(int health) {
        this.maxHealth = health;
        this.currentHealth = health;
    }
    public void setAttack(int attack) { this.attack = attack; }
    public void setFireRate(int rate) { this.fireRate = rate; }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        if (split.length > 2) {
            String shipType = split[2];
            this.setIcon(GameLoad.imgMap.get(shipType));
        } else {
            this.setIcon(GameLoad.imgMap.get("player_1"));
        }
        this.setW(this.getIcon().getIconWidth());
        this.setH(this.getIcon().getIconHeight());
        this.setSpeed(3);
        return this;
    }

    @Override
    public void showElement(Graphics g) {
        // 血条
        g.setColor(Color.red);
        for (int i = 0; i < life; i++) {
            g.fillRect(getX() + i * 15, getY() - 20, 10, 5);
        }
        // 绘制飞机
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(), null);
        // 护盾特效
        if (isShield) {
            g.setColor(new Color(0, 255, 255, 128));
            g.drawOval(getX() - 5, getY() - 5, getW() + 10, getH() + 10);
        }
    }

    @Override
    public void keyClick(boolean bl, int key) {
        if (bl) {
            switch (key) {
                case 37: left = true; break;
                case 38: up = true; break;
                case 39: right = true; break;
                case 40: down = true; break;
                case 32: pkType = true; break;
            }
        } else {
            switch (key) {
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
        int speed = getSpeed();
        if (ElementManager.getManager().isGlobalSlow()) speed = Math.max(1, speed / 2);
        if (left && getX() > 0) setX(getX() - speed);
        if (up && getY() > 0) setY(getY() - speed);
        if (right && getX() < 800 - getW()) setX(getX() + speed);
        if (down && getY() < 600 - getH()) setY(getY() + speed);
    }

    @Override
    protected void updateImage(long gameTime) {
        currentFrame = (int) (gameTime / animationSpeed) % 21 + 1;
        String imgKey = "player_" + currentFrame;
        ImageIcon icon = GameLoad.imgMap.get(imgKey);
        if (icon == null) icon = GameLoad.imgMap.get("player_1");
        this.setIcon(icon);
    }


    @Override
    public void add(long gameTime) {
        if(!isAutoFire && !pkType) return;
        if(gameTime - filetime < (isAutoFire ? 5:10)) return;
        filetime = gameTime;
        SoundManager.getInstance().playSound("shoot");
        fireWeapon();
    }
    
    private void fireWeapon() {
        switch(bulletMode) {
            case "straight":
                fireStraight();
                break;
            case "fan":
                for(int i = -1; i <= 1; i++) fireAngle(i * 15);
                break;
            case "circle":
                for(int i = 0; i < 12; i++) fireAngle(i * 30);
                break;
            case "curve":
                fireCurve();
                break;
            case "bomb":
                fireBomb();
                break;
        }
    }
    
    private void fireStraight() {
        int offsetX = getW()/2-5;
        String bulletStr = "x:"+(getX()+offsetX)+",y:"+(getY()-10)+",f:up,mode:straight";
        ElementObj bullet = new PlayFile().createElement(bulletStr);
        ElementManager.getManager().addElement(bullet, GameElement.PLAYFILE);
    }
    private void fireAngle(int angle) {
        int offsetX = getW()/2-5;
        String bulletStr = "x:"+(getX()+offsetX)+",y:"+(getY()-10)+",angle:"+(90-angle)+",mode:fan";
        ElementObj bullet = new PlayFile().createElement(bulletStr);
        ElementManager.getManager().addElement(bullet, GameElement.PLAYFILE);
    }
    private void fireCurve() {
        int offsetX = getW()/2-5;
        String bulletStr = "x:"+(getX()+offsetX)+",y:"+(getY()-10)+",mode:curve";
        ElementObj bullet = new PlayFile().createElement(bulletStr);
        ElementManager.getManager().addElement(bullet, GameElement.PLAYFILE);
    }
    
    private void fireBomb() {
        int offsetX = getW()/2-5;
        String bulletStr = "x:"+(getX()+offsetX)+",y:"+(getY()-10)+",mode:bomb,radius:80";
        ElementObj bullet = new PlayFile().createElement(bulletStr);
        ElementManager.getManager().addElement(bullet, GameElement.PLAYFILE);
    }

    private void fireNormalWeapon() {
        for (int i = 0; i < power; i++) {
            int offsetX = (i - (power - 1) / 2) * 20;
            String bulletStr = "x:" + (this.getX() + this.getW() / 2 - 5 + offsetX) +
                    ",y:" + (this.getY() - 10) + ",f:up";
            ElementObj bullet = new PlayFile().createElement(bulletStr);
            ElementManager.getManager().addElement(bullet, GameElement.PLAYFILE);
        }
    }

    private void fireSuperWeapon() {
        for (int i = 0; i < 36; i++) {
            double angle = Math.toRadians(i * 10);
            int offsetX = (int) (Math.cos(angle) * 20);
            int offsetY = (int) (Math.sin(angle) * 20);
            String bulletStr = "x:" + (getX() + getW() / 2 - 5 + offsetX) +
                    ",y:" + (getY() + getH() / 2 + offsetY) + ",type:16";
            ElementObj bullet = new PlayFile().createElement(bulletStr);
            ElementManager.getManager().addElement(bullet, GameElement.PLAYFILE);
        }
    }

    public void applyItemEffect(int itemType) {
        switch (itemType) {
            case 1: increasePower(); break;
            case 2: life = Math.min(life + 1, 3); break;
            case 3: startInvincible(3000); break;
            case 4: clearAllEnemies(); break;
            case 5: activateDoubleScore(10000); break;
            case 6: activateAutoFire(5000); break;
            case 7: activateSuperWeapon(); break;
            case 8: activateShield(5000); break;
            case 9: clearAllEnemyBullets(); break;
            case 10: activateSlowTime(5000); break;
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
        setSpeed(originalSpeed + 2);
        new Thread(() -> {
            try {
                Thread.sleep(duration);
                isAutoFire = false;
                setSpeed(originalSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void activateDoubleScore(long duration) {
        isDoubleScore = true;
        new Thread(() -> {
            try {
                Thread.sleep(duration);
                isDoubleScore = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void clearAllEnemies() {
        ElementManager em = ElementManager.getManager();
        em.getElementsByKey(GameElement.ENEMY).clear();
        em.getElementsByKey(GameElement.ENEMYFILE).clear();
        for (int i = 0; i < 10; i++) {
            ElementObj explosion = createExplosionEffect();
            em.addElement(explosion, GameElement.DIE);
        }
    }

    private void clearAllEnemyBullets() {
        ElementManager.getManager().getElementsByKey(GameElement.ENEMYFILE).clear();
    }

    private void activateShield(long duration) {
        isShield = true;
        new Thread(() -> {
            try { Thread.sleep(duration); } catch (Exception e) {}
            isShield = false;
        }).start();
    }

    public void activateSlowTime(long duration) {
        isSlowTime = true;
        ElementManager.getManager().setGlobalSlow(true);
        new Thread(() -> {
            try { Thread.sleep(duration); } catch (Exception e) {}
            isSlowTime = false;
            ElementManager.getManager().setGlobalSlow(false);
        }).start();
    }

    private ElementObj createExplosionEffect() {
        Random rand = new Random();
        int x = rand.nextInt(800);
        int y = rand.nextInt(400);
        return new Explosion().createElement(x + "," + y);
    }

    public void startInvincible(long duration) {
        if (isInvincible) return;
        isInvincible = true;
        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            long endTime = startTime + duration;
            while (System.currentTimeMillis() < endTime) {
                setVisible(!isVisible());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            setVisible(true);
            isInvincible = false;
        }).start();
    }

    private boolean isVisible() { return true; }
    private void setVisible(boolean b) {}

    @Override
    public boolean pk(ElementObj obj) {
        if (isInvincible || isShield) return false;
        return super.pk(obj);
    }

    public boolean isDoubleScore() { return isDoubleScore; }

    public void decreaseLife() {
        life--;
        if (life <= 0) {
            setLive(false);
        }
    }

    public void increasePower() {
        if (power < 3) {
            power++;
            currentFrame = power;
            updateImage(0);
        }
    }

    public int getLife() { return life; }
    public void setLife(int life) { this.life = life; }
    public int getPower() { return power; }
    public void setPower(int power) { this.power = power; }

    public void restoreLife() {
        life = Math.min(life + 1, 3);
    }
}
