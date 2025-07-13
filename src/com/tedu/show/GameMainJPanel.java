package com.tedu.show;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.tedu.controller.GameThread;
import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class GameMainJPanel extends JPanel implements Runnable {
    private ElementManager em;
    private Font scoreFont = new Font("微软雅黑", Font.BOLD, 20);
    private Font menuFont = new Font("微软雅黑", Font.BOLD, 90);
    private Font menuFont1 = new Font("微软雅黑", Font.BOLD, 50);
    private Font infoFont = new Font("微软雅黑", Font.PLAIN, 20);
    private GameThread gameThread;
    
    private Font levelFont = new Font("微软雅黑",Font.BOLD,40);
    private int selectedLevel = 1;
    
    private int selectedShip = 0; // 当前选中的飞机索引
    private Font shipAttrFont = new Font("微软雅黑", Font.PLAIN, 18); // 飞机属性字体
    private Font shipNameFont = new Font("微软雅黑", Font.BOLD, 24); // 战机名称字体 

    public GameMainJPanel(GameThread gameThread) {
        this.gameThread = gameThread;
        init();
    }

    public void init() {
        em = ElementManager.getManager();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g);

        switch (gameThread.getGameState()) {
            case MENU:
                drawMenu(g);
                break;
            case LEVEL_SELECT:
            	drawLevelSelect(g);
            	break;
            case SHIP_SELECT:
            	drawShipSelect(g);
            	break;
            case RUNNING:
                drawGame(g);
                break;
            case GAME_OVER:
                drawGameOver(g);
                break;
        }
    }
    
    
 // 添加关卡选择界面绘制方法
    private void drawLevelSelect(Graphics g) {
        // 绘制背景
        ImageIcon bgIcon = new ImageIcon("image/background/8.png");
        if (bgIcon.getImage() != null) {
            g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // 半透明遮罩
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        // 标题
        g.setColor(Color.WHITE);
        g.setFont(menuFont);
        String title = "选择关卡";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (getWidth() - titleWidth) / 2, 150);

        // 关卡选项
        g.setFont(levelFont);
        for (int i = 1; i <= 3; i++) {
            if (i == selectedLevel) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            String levelText = "第 " + i + " 关";
            int levelWidth = g.getFontMetrics().stringWidth(levelText);
            g.drawString(levelText, (getWidth() - levelWidth) / 2, 250 + i * 60);
        }
    }
        
        
     // 添加关卡选择方法
        public void selectLevelUp() {
            if (selectedLevel > 1) {
                selectedLevel--;
            }
        }

        public void selectLevelDown() {
            if (selectedLevel < 3) {
                selectedLevel++;
            }
        }

        public int getSelectedLevel() {
            return selectedLevel;
        }
        
        public enum ShipType {
            // 基础机型
            DEFAULT("标准战机", "player_1", 5, 1, 100, 1),
            
            // 高速机型（使用流线型外观的图片）
            FAST("闪电战机", "player_2", 8, 1, 80, 2),
            
            // 重型机型（使用厚重外观的图片）
            HEAVY("重装战机", "player_3", 3, 2, 150, 1),
            
            // 平衡机型
            BALANCED("均衡战机", "player_4", 6, 1, 120, 2),
            
            // 特殊机型（选择你认为最特别的图片）
            SPECIAL("幽灵战机", "player_19", 7, 3, 90, 3);

            private final String name;
            private final String imageKey;
            private final int speed;      // 移动速度
            private final int power;      // 攻击力
            private final int health;     // 生命值
            private final int fireRate;   // 射击速度等级（1-慢，2-中，3-快）

            ShipType(String name, String imageKey, int speed, int power, int health, int fireRate) {
                this.name = name;
                this.imageKey = imageKey;
                this.speed = speed;
                this.power = power;
                this.health = health;
                this.fireRate = fireRate;
            }

            // Getter方法
            public String getName() { return name; }
            public String getImageKey() { return imageKey; }
            public int getSpeed() { return speed; }
            public int getPower() { return power; }
            public int getHealth() { return health; }
            public int getFireRate() { return fireRate; }

            // 应用到玩家飞机
            public void applyToPlayer(Play player) {
                player.setSpeed(this.speed);
                player.setAttack(this.power);
                player.setMaxHealth(this.health);
                player.setFireRate(this.fireRate);
                
                // 设置对应的飞机图片
                ImageIcon icon = GameLoad.imgMap.get(this.imageKey);
                if(icon != null) {
                    player.setIcon(icon);
                    player.setW(icon.getIconWidth());
                    player.setH(icon.getIconHeight());
                }
            }
        }
     // 2. 修改drawShipSelect方法（关键调整点）
        private void drawShipSelect(Graphics g) {
            // 背景绘制（保持不变）
            ImageIcon bgIcon = new ImageIcon("image/background/10.jpg");
            if (bgIcon.getImage() != null) {
                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
            
            // 半透明遮罩（调整为只覆盖主要内容区域）
            int contentHeight = 650; // 固定内容高度
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), contentHeight);

            // 标题（位置上调）
            g.setColor(Color.WHITE);
            g.setFont(menuFont);
            String title = "选择战机";
            int titleWidth = g.getFontMetrics().stringWidth(title);
            g.drawString(title, (getWidth() - titleWidth)/2, 100); // Y坐标从150改为100

            // 战机选项（调整间距）
            ShipType[] ships = ShipType.values();
            int startY = 150; // 起始Y坐标
            int optionSpacing = 120; // 选项间距
            
            for (int i = 0; i < ships.length; i++) {
                int yPos = startY + i * optionSpacing;
                
                // 绘制选中状态
                g.setColor(i == selectedShip ? Color.YELLOW : Color.WHITE);
                
                // 战机图片（大小调整为80x80）
                ImageIcon shipIcon = GameLoad.imgMap.get(ships[i].getImageKey());
                if (shipIcon != null) {
                    g.drawImage(shipIcon.getImage(), 
                               getWidth()/2 - 40, 
                               yPos - 60,  // 图片位置上调
                               80, 80, null);
                }
                
                // 战机名称（使用新字体）
                g.setFont(shipNameFont);
                String shipText = ships[i].name;
                int textWidth = g.getFontMetrics().stringWidth(shipText);
                g.drawString(shipText, (getWidth() - textWidth)/2, yPos);
            }

            // 属性表格（位置下移）
            int tableY = startY + ships.length * optionSpacing;
            drawAttributeTable(g, tableY);
            
            // 操作提示
            g.setColor(Color.WHITE);
            g.setFont(shipAttrFont);
            String hint = "方向键选择，回车键确认";
            int hintWidth = g.getFontMetrics().stringWidth(hint);
            g.drawString(hint, (getWidth() - hintWidth)/2, tableY + 120);
        }
     // 3. 属性表格绘制方法
        private void drawAttributeTable(Graphics g, int startY) {
            ShipType currentShip = ShipType.values()[selectedShip];
            g.setFont(shipAttrFont);
            
            String[] attrs = {
                "速度: " + currentShip.getSpeed(),
                "火力: " + currentShip.getPower(),
                "耐久: " + currentShip.getHealth(),
                "射速: " + getFireRateText(currentShip.getFireRate())
            };
            
            // 简化表格绘制
            int rowHeight = 30;
            int tableWidth = 300;
            int tableX = (getWidth() - tableWidth)/2;
            
            // 绘制表格背景
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRoundRect(tableX, startY, tableWidth, rowHeight*3, 10, 10);
            
            // 绘制属性
            g.setColor(Color.WHITE);
            for (int i = 0; i < attrs.length; i++) {
                g.drawString(attrs[i], tableX + 20, startY + 20 + i*rowHeight);
            }
        }
        
     // 辅助方法：绘制属性表格行
        private void drawAttrRow(Graphics g, String text, int y) {
            int textWidth = g.getFontMetrics().stringWidth(text);
            g.drawString(text, (getWidth() - textWidth) / 2, y);
        }

        private String getFireRateText(int rate) {
            switch(rate) {
                case 1: return "慢速";
                case 2: return "中速";
                case 3: return "快速";
                default: return "标准";
            }
        }
        
     // 在GameMainJPanel类中添加
        public void selectShipUp() {
            if (selectedShip > 0) {
                selectedShip--;
            }
        }

        public void selectShipDown() {
            if (selectedShip < ShipType.values().length - 1) {
                selectedShip++;
            }
        }

        public int getSelectedShipIndex() {
            return selectedShip;
        }
        
        
    private void drawMenu(Graphics g) {
        ImageIcon bgIcon = new ImageIcon("image/background/8.png");
        if (bgIcon.getImage() != null) {
            g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            System.err.println("背景图片加载失败");
        }

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(menuFont);
        String title = "飞机大战";
        FontMetrics titleFm = g.getFontMetrics();
        int titleX = (getWidth() - titleFm.stringWidth(title)) / 2;
        int titleY = getHeight() / 2;
        g.setColor(Color.WHITE);
        g.drawString(title, titleX, titleY);

        g.setFont(infoFont);
        String startInfo = "按空格键开始游戏";
        FontMetrics infoFm = g.getFontMetrics();
        int infoX = (getWidth() - infoFm.stringWidth(startInfo)) / 2;
        int infoY = titleY + 100;
        g.drawString(startInfo, infoX, infoY);
    }

    private void drawGame(Graphics g) {
        // 使用线程安全的集合副本进行绘制
        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        
        // 绘制背景（背景通常不会被修改，可以直接使用）
        List<ElementObj> backgrounds = all.get(GameElement.BACKGROUND);
        if (!backgrounds.isEmpty()) {
            backgrounds.get(0).showElement(g);
        }

        // 绘制其他元素（使用副本避免并发修改）
        for (GameElement ge : GameElement.values()) {
            if (ge == GameElement.BACKGROUND) continue;
            
            // 创建当前帧的副本
            List<ElementObj> elementsCopy = new CopyOnWriteArrayList<>(all.get(ge));
            for (ElementObj obj : elementsCopy) {
                if (obj.isLive()) {  // 只绘制存活的元素
                    obj.showElement(g);
                }
            }
        }

        drawHUD(g);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.RED);
        g.setFont(menuFont);
        String gameOver = "游戏结束";
        int gameOverWidth = g.getFontMetrics().stringWidth(gameOver);
        g.drawString(gameOver, (getWidth() - gameOverWidth) / 2, 200);

        g.setColor(Color.WHITE);
        g.setFont(infoFont);
        String scoreText = "最终得分: " + gameThread.getScore();
        int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
        g.drawString(scoreText, (getWidth() - scoreWidth) / 2, 300);

        String restartInfo = "按空格键返回主菜单";
        int restartWidth = g.getFontMetrics().stringWidth(restartInfo);
        g.drawString(restartInfo, (getWidth() - restartWidth) / 2, 400);
    }

    private void drawHUD(Graphics g) {
        // 获取玩家列表副本
        List<ElementObj> players = new CopyOnWriteArrayList<>(
            em.getElementsByKey(GameElement.PLAY));
        
        if (!players.isEmpty()) {
            Play player = (Play) players.get(0);
            g.setFont(scoreFont);
            g.setColor(Color.WHITE);
            g.drawString("Score: " + gameThread.getScore(), 20, 30);
            g.drawString("Life: " + player.getLife(), 20, 60);
        }
    }

    @Override
    public void run() {
        while (true) {
            this.repaint();
            try {
                Thread.sleep(50); // 控制帧率约20FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}