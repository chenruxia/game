////package com.tedu.show;
////
////import java.awt.Color;
////
////import java.awt.Font;
////import java.awt.FontMetrics;
////import java.awt.GradientPaint;
////import java.awt.Graphics;
////import java.awt.Graphics2D;
////import java.awt.RenderingHints;
////import java.util.List;
////import java.util.Map;
////import java.util.concurrent.CopyOnWriteArrayList;
////
////import javax.swing.ImageIcon;
////import javax.swing.JPanel;
////import com.tedu.manager.AchievementsManager;
////import java.util.List;
////import com.tedu.controller.GameThread;
////import com.tedu.element.ElementObj;
////import com.tedu.element.Play;
////import com.tedu.manager.ElementManager;
////import com.tedu.manager.GameElement;
////import com.tedu.manager.GameLoad;
////
////public class GameMainJPanel extends JPanel implements Runnable {
////    private ElementManager em;
////    private Font scoreFont = new Font("微软雅黑", Font.BOLD, 20);
////    private Font menuFont = new Font("微软雅黑", Font.BOLD, 90);
////    private Font menuFont1 = new Font("微软雅黑", Font.BOLD, 50);
////    private Font infoFont = new Font("微软雅黑", Font.PLAIN, 20);
////    private GameThread gameThread;
////    
////    private Font levelFont = new Font("微软雅黑",Font.BOLD,40);
////    private int selectedLevel = 1;
////    
////    private int selectedShip = 0; // 当前选中的飞机索引
////    private Font shipAttrFont = new Font("微软雅黑", Font.PLAIN, 18); // 飞机属性字体
////    private Font shipNameFont = new Font("微软雅黑", Font.BOLD, 24); // 战机名称字体 
////    private String storyText = null;
////
////    public GameMainJPanel(GameThread gameThread) {
////        this.gameThread = gameThread;
////        init();
////    }
////
////    public void init() {
////        em = ElementManager.getManager();
////    }
////    
////    public void setStoryText(String text) {
////        this.storyText = text;
////        this.repaint();
////    }
////
////    @Override
////    public void paint(Graphics g) {
////        Graphics2D g2d = (Graphics2D) g;
////        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
////        super.paint(g);
////
////        switch (gameThread.getGameState()) {
////            case MENU:
////                drawMenu(g);
////                break;
////            case LEVEL_SELECT:
////            	drawLevelSelect(g);
////            	break;
////            case SHIP_SELECT:
////            	drawShipSelect(g);
////            	break;
////            case RUNNING:
////                drawGame(g);
////                break;
////            case GAME_OVER:
////                drawGameOver(g);
////                break;
////        }
////        
////     // 剧情文本绘制（覆盖在所有界面上）
////        if (storyText != null) {
////            g.setColor(new Color(0, 0, 0, 180));
////            g.fillRect(0, getHeight() / 2 - 60, getWidth(), 120);
////            g.setColor(Color.YELLOW);
////            g.setFont(new Font("微软雅黑", Font.BOLD, 36));
////            FontMetrics fm = g.getFontMetrics();
////            int textWidth = fm.stringWidth(storyText);
////            g.drawString(storyText, (getWidth() - textWidth) / 2, getHeight() / 2 + 15);
////        }
////        
////        if (gameThread.getGameState() == GameThread.GameState.PAUSED) {
////            g.setColor(new Color(0, 0, 0, 150));
////            g.fillRect(0, 0, getWidth(), getHeight());
////            g.setColor(Color.YELLOW);
////            g.setFont(menuFont);
////            String pauseText = "暂停";
////            int pauseWidth = g.getFontMetrics().stringWidth(pauseText);
////            g.drawString(pauseText, (getWidth() - pauseWidth) / 2, getHeight() / 2);
////        }
////    }
////    
////    
//// // 添加关卡选择界面绘制方法
////    private void drawLevelSelect(Graphics g) {
////        // 绘制背景
////        ImageIcon bgIcon = new ImageIcon("image/background/8.png");
////        if (bgIcon.getImage() != null) {
////            g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
////        } else {
////            g.setColor(Color.BLACK);
////            g.fillRect(0, 0, getWidth(), getHeight());
////        }
////
////        // 半透明遮罩
////        g.setColor(new Color(0, 0, 0, 150));
////        g.fillRect(0, 0, getWidth(), getHeight());
////
////        // 标题
////        g.setColor(Color.WHITE);
////        g.setFont(menuFont);
////        String title = "选择关卡";
////        int titleWidth = g.getFontMetrics().stringWidth(title);
////        g.drawString(title, (getWidth() - titleWidth) / 2, 150);
////
////        // 关卡选项
////        g.setFont(levelFont);
////        for (int i = 1; i <= 3; i++) {
////            if (i == selectedLevel) {
////                g.setColor(Color.YELLOW);
////            } else {
////                g.setColor(Color.WHITE);
////            }
////            String levelText = "第 " + i + " 关";
////            int levelWidth = g.getFontMetrics().stringWidth(levelText);
////            g.drawString(levelText, (getWidth() - levelWidth) / 2, 250 + i * 60);
////        }
////    }
////        
////        
////     // 添加关卡选择方法
////        public void selectLevelUp() {
////            if (selectedLevel > 1) {
////                selectedLevel--;
////            }
////        }
////
////        public void selectLevelDown() {
////            if (selectedLevel < 3) {
////                selectedLevel++;
////            }
////        }
////
////        public int getSelectedLevel() {
////            return selectedLevel;
////        }
////        
////        public enum ShipType {
////            // 基础机型
////            DEFAULT("标准战机", "player_1", 5, 1, 100, 1),
////            
////            // 高速机型（使用流线型外观的图片）
////            FAST("闪电战机", "player_2", 8, 1, 80, 2),
////            
////            // 重型机型（使用厚重外观的图片）
////            HEAVY("重装战机", "player_3", 3, 2, 150, 1),
////            
////            // 平衡机型
////            BALANCED("均衡战机", "player_4", 6, 1, 120, 2),
////            
////            // 特殊机型（选择你认为最特别的图片）
////            SPECIAL("幽灵战机", "player_19", 7, 3, 90, 3);
////
////            private final String name;
////            private final String imageKey;
////            private final int speed;      // 移动速度
////            private final int power;      // 攻击力
////            private final int health;     // 生命值
////            private final int fireRate;   // 射击速度等级（1-慢，2-中，3-快）
////
////            ShipType(String name, String imageKey, int speed, int power, int health, int fireRate) {
////                this.name = name;
////                this.imageKey = imageKey;
////                this.speed = speed;
////                this.power = power;
////                this.health = health;
////                this.fireRate = fireRate;
////            }
////
////            // Getter方法
////            public String getName() { return name; }
////            public String getImageKey() { return imageKey; }
////            public int getSpeed() { return speed; }
////            public int getPower() { return power; }
////            public int getHealth() { return health; }
////            public int getFireRate() { return fireRate; }
////
////            // 应用到玩家飞机
////            public void applyToPlayer(Play player) {
////                player.setSpeed(this.speed);
////                player.setAttack(this.power);
////                player.setMaxHealth(this.health);
////                player.setFireRate(this.fireRate);
////                
////                // 设置对应的飞机图片
////                ImageIcon icon = GameLoad.imgMap.get(this.imageKey);
////                if(icon != null) {
////                    player.setIcon(icon);
////                    player.setW(icon.getIconWidth());
////                    player.setH(icon.getIconHeight());
////                }
////            }
////        }
////     // 2. 修改drawShipSelect方法（关键调整点）
////        private void drawShipSelect(Graphics g) {
////            // 背景绘制（保持不变）
////            ImageIcon bgIcon = new ImageIcon("image/background/10.jpg");
////            if (bgIcon.getImage() != null) {
////                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
////            }
////            
////            // 半透明遮罩（调整为只覆盖主要内容区域）
////            int contentHeight = 650; // 固定内容高度
////            g.setColor(new Color(0, 0, 0, 150));
////            g.fillRect(0, 0, getWidth(), contentHeight);
////
////            // 标题（位置上调）
////            g.setColor(Color.WHITE);
////            g.setFont(menuFont);
////            String title = "选择战机";
////            int titleWidth = g.getFontMetrics().stringWidth(title);
////            g.drawString(title, (getWidth() - titleWidth)/2, 100); // Y坐标从150改为100
////
////            // 战机选项（调整间距）
////            ShipType[] ships = ShipType.values();
////            int startY = 150; // 起始Y坐标
////            int optionSpacing = 120; // 选项间距
////            
////            for (int i = 0; i < ships.length; i++) {
////                int yPos = startY + i * optionSpacing;
////                
////                // 绘制选中状态
////                g.setColor(i == selectedShip ? Color.YELLOW : Color.WHITE);
////                
////                // 战机图片（大小调整为80x80）
////                ImageIcon shipIcon = GameLoad.imgMap.get(ships[i].getImageKey());
////                if (shipIcon != null) {
////                    g.drawImage(shipIcon.getImage(), 
////                               getWidth()/2 - 40, 
////                               yPos - 60,  // 图片位置上调
////                               80, 80, null);
////                }
////                
////                // 战机名称（使用新字体）
////                g.setFont(shipNameFont);
////                String shipText = ships[i].name;
////                int textWidth = g.getFontMetrics().stringWidth(shipText);
////                g.drawString(shipText, (getWidth() - textWidth)/2, yPos);
////            }
////
////            // 属性表格（位置下移）
////            int tableY = startY + ships.length * optionSpacing;
////            drawAttributeTable(g, tableY);
////            
////            // 操作提示
////            g.setColor(Color.WHITE);
////            g.setFont(shipAttrFont);
////            String hint = "方向键选择，回车键确认";
////            int hintWidth = g.getFontMetrics().stringWidth(hint);
////            g.drawString(hint, (getWidth() - hintWidth)/2, tableY + 120);
////        }
////     // 3. 属性表格绘制方法
////        private void drawAttributeTable(Graphics g, int startY) {
////            ShipType currentShip = ShipType.values()[selectedShip];
////            g.setFont(shipAttrFont);
////            
////            String[] attrs = {
////                "速度: " + currentShip.getSpeed(),
////                "火力: " + currentShip.getPower(),
////                "耐久: " + currentShip.getHealth(),
////                "射速: " + getFireRateText(currentShip.getFireRate())
////            };
////            
////            // 简化表格绘制
////            int rowHeight = 30;
////            int tableWidth = 300;
////            int tableX = (getWidth() - tableWidth)/2;
////            
////            // 绘制表格背景
////            g.setColor(new Color(0, 0, 0, 200));
////            g.fillRoundRect(tableX, startY, tableWidth, rowHeight*3, 10, 10);
////            
////            // 绘制属性
////            g.setColor(Color.WHITE);
////            for (int i = 0; i < attrs.length; i++) {
////                g.drawString(attrs[i], tableX + 20, startY + 20 + i*rowHeight);
////            }
////        }
////        
////     // 辅助方法：绘制属性表格行
////        private void drawAttrRow(Graphics g, String text, int y) {
////            int textWidth = g.getFontMetrics().stringWidth(text);
////            g.drawString(text, (getWidth() - textWidth) / 2, y);
////        }
////
////        private String getFireRateText(int rate) {
////            switch(rate) {
////                case 1: return "慢速";
////                case 2: return "中速";
////                case 3: return "快速";
////                default: return "标准";
////            }
////        }
////        
////     // 在GameMainJPanel类中添加
////        public void selectShipUp() {
////            if (selectedShip > 0) {
////                selectedShip--;
////            }
////        }
////
////        public void selectShipDown() {
////            if (selectedShip < ShipType.values().length - 1) {
////                selectedShip++;
////            }
////        }
////
////        public int getSelectedShipIndex() {
////            return selectedShip;
////        }
////        
////        
////    private void drawMenu(Graphics g) {
////        ImageIcon bgIcon = new ImageIcon("image/background/8.png");
////        if (bgIcon.getImage() != null) {
////            g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
////        } else {
////            g.setColor(Color.BLACK);
////            g.fillRect(0, 0, getWidth(), getHeight());
////            System.err.println("背景图片加载失败");
////        }
////
////        g.setColor(new Color(0, 0, 0, 150));
////        g.fillRect(0, 0, getWidth(), getHeight());
////
////        g.setFont(menuFont);
////        String title = "飞机大战";
////        FontMetrics titleFm = g.getFontMetrics();
////        int titleX = (getWidth() - titleFm.stringWidth(title)) / 2;
////        int titleY = getHeight() / 2;
////        g.setColor(Color.WHITE);
////        g.drawString(title, titleX, titleY);
////
////        g.setFont(infoFont);
////        String startInfo = "按空格键开始游戏";
////        FontMetrics infoFm = g.getFontMetrics();
////        int infoX = (getWidth() - infoFm.stringWidth(startInfo)) / 2;
////        int infoY = titleY + 100;
////        g.drawString(startInfo, infoX, infoY);
////    }
////
////    private void drawGame(Graphics g) {
////        // 使用线程安全的集合副本进行绘制
////        Map<GameElement, List<ElementObj>> all = em.getGameElements();
////        
////        // 绘制背景（背景通常不会被修改，可以直接使用）
////        List<ElementObj> backgrounds = all.get(GameElement.BACKGROUND);
////        if (!backgrounds.isEmpty()) {
////            backgrounds.get(0).showElement(g);
////        }
////
////        // 绘制其他元素（使用副本避免并发修改）
////        for (GameElement ge : GameElement.values()) {
////            if (ge == GameElement.BACKGROUND) continue;
////            
////            // 创建当前帧的副本
////            List<ElementObj> elementsCopy = new CopyOnWriteArrayList<>(all.get(ge));
////            for (ElementObj obj : elementsCopy) {
////                if (obj.isLive()) {  // 只绘制存活的元素
////                    obj.showElement(g);
////                }
////            }
////        }
////
////        drawHUD(g);
////    }
////
////    private void drawGameOver(Graphics g) {
////        g.setColor(new java.awt.Color(0, 0, 0, 150));
////        g.fillRect(0, 0, getWidth(), getHeight());
////
////        g.setColor(java.awt.Color.RED);
////        g.setFont(menuFont);
////        String gameOver = "游戏结束";
////        int gameOverWidth = g.getFontMetrics().stringWidth(gameOver);
////        g.drawString(gameOver, (getWidth() - gameOverWidth) / 2, 200);
////
////        g.setColor(java.awt.Color.WHITE);
////        g.setFont(infoFont);
////        String scoreText = "最终得分: " + gameThread.getScore();
////        int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
////        g.drawString(scoreText, (getWidth() - scoreWidth) / 2, 300);
////
////        // 排行榜
////        g.setFont(infoFont);
////        List<Integer> topScores = AchievementsManager.getTopScores(5);
////        String rankTitle = "排行榜：";
////        int rankTitleWidth = g.getFontMetrics().stringWidth(rankTitle);
////        g.drawString(rankTitle, (getWidth() - rankTitleWidth) / 2, 350);
////
////        for (int i = 0; i < topScores.size(); i++) {
////            String rankLine = (i + 1) + ". " + topScores.get(i);
////            int rankLineWidth = g.getFontMetrics().stringWidth(rankLine);
////            g.drawString(rankLine, (getWidth() - rankLineWidth) / 2, 380 + i * 30);
////        }
////
////        String restartInfo = "按空格键返回主菜单";
////        int restartWidth = g.getFontMetrics().stringWidth(restartInfo);
////        g.drawString(restartInfo, (getWidth() - restartWidth) / 2, 550);
////    }
////    
////
////    private void drawHUD(Graphics g) {
////        List<ElementObj> players = new CopyOnWriteArrayList<>(em.getElementsByKey(GameElement.PLAY));
////        if (!players.isEmpty()) {
////            Play player = (Play) players.get(0);
////            g.setFont(scoreFont);
////            g.setColor(Color.WHITE);
////            g.drawString("Score: " + gameThread.getScore(), 20, 30);
////
////            // 血条渐变
////            Graphics2D g2d = (Graphics2D) g;
////            GradientPaint gp = new GradientPaint(20, 70, Color.RED, 120, 70, Color.ORANGE);
////            g2d.setPaint(gp);
////            g2d.fillRect(20, 70, player.getLife() * 30, 10);
////
////            // 能量条
////            g2d.setPaint(new GradientPaint(20, 90, Color.BLUE, 120, 90, Color.CYAN));
////            g2d.fillRect(20, 90, player.getPower() * 30, 10);
////
////            g.setColor(Color.WHITE);
////            g.drawString("Life: " + player.getLife(), 20, 60);
////        }
////    }
////
////    @Override
////    public void run() {
////        while (true) {
////            this.repaint();
////            try {
////                Thread.sleep(50); // 控制帧率约20FPS
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
////    }
////}
//
//
//package com.tedu.show;
//
//import java.awt.*;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CopyOnWriteArrayList;
//import javax.swing.ImageIcon;
//import javax.swing.JPanel;
//import com.tedu.manager.AchievementsManager;
//import com.tedu.controller.GameThread;
//import com.tedu.element.ElementObj;
//import com.tedu.element.Play;
//import com.tedu.manager.ElementManager;
//import com.tedu.manager.GameElement;
//import com.tedu.manager.GameLoad;
//
//public class GameMainJPanel extends JPanel implements Runnable {
//    private ElementManager em;
//    private Font scoreFont = new Font("微软雅黑", Font.BOLD, 20);
//    private Font menuFont = new Font("微软雅黑", Font.BOLD, 90);
//    private Font infoFont = new Font("微软雅黑", Font.PLAIN, 20);
//    private GameThread gameThread;
//
//    private Font levelFont = new Font("微软雅黑",Font.BOLD,40);
//    private int selectedLevel = 1;
//
//    private int selectedShip = 0; // 当前选中的飞机索引
//    private Font shipAttrFont = new Font("微软雅黑", Font.PLAIN, 18); // 飞机属性字体
//    private Font shipNameFont = new Font("微软雅黑", Font.BOLD, 24); // 战机名称字体 
//    private String storyText = null;
//    
//    
//    
//    // 原始分辨率
//    private static final int BASE_W = 800;
//    private static final int BASE_H = 600;
//
//    public GameMainJPanel(GameThread gameThread) {
//        this.gameThread = gameThread;
//        init();
//    }
//
//    public void init() {
//        em = ElementManager.getManager();
//    }
//
//    public void setStoryText(String text) {
//        this.storyText = text;
//        this.repaint();
//    }
//
//    // 缩放工具
//    private int sx(int x) { return (int)(x * getWidth() / (double)BASE_W); }
//    private int sy(int y) { return (int)(y * getHeight() / (double)BASE_H); }
//    private int sw(int w) { return (int)(w * getWidth() / (double)BASE_W); }
//    private int sh(int h) { return (int)(h * getHeight() / (double)BASE_H); }
//
//    @Override
//    public void paint(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        super.paint(g);
//
//        switch (gameThread.getGameState()) {
//            case MENU:
//                drawMenu(g);
//                break;
//            case LEVEL_SELECT:
//                drawLevelSelect(g);
//                break;
//            case SHIP_SELECT:
//                drawShipSelect(g);
//                break;
//            case RUNNING:
//                drawGame(g);
//                break;
//            case GAME_OVER:
//                drawGameOver(g);
//                break;
//        }
//
//        // 剧情文本绘制（覆盖在所有界面上）
//        if (storyText != null) {
//            g.setColor(new Color(0, 0, 0, 180));
//            g.fillRect(0, getHeight() / 2 - sh(60), getWidth(), sh(120));
//            g.setColor(Color.YELLOW);
//            g.setFont(new Font("微软雅黑", Font.BOLD, sw(36)));
//            FontMetrics fm = g.getFontMetrics();
//            int textWidth = fm.stringWidth(storyText);
//            g.drawString(storyText, (getWidth() - textWidth) / 2, getHeight() / 2 + sh(15));
//        }
//
//        if (gameThread.getGameState() == GameThread.GameState.PAUSED) {
//            g.setColor(new Color(0, 0, 0, 150));
//            g.fillRect(0, 0, getWidth(), getHeight());
//            g.setColor(Color.YELLOW);
//            g.setFont(menuFont.deriveFont((float)sw(90)));
//            String pauseText = "暂停";
//            int pauseWidth = g.getFontMetrics().stringWidth(pauseText);
//            g.drawString(pauseText, (getWidth() - pauseWidth) / 2, getHeight() / 2);
//        }
//        
//        if (gameThread.getGameState() == GameThread.GameState.PAUSED) {
//            drawPauseScreen(g);
//        }
//        if (gameThread.getGameState() == GameThread.GameState.MENU) {
//            drawMenuScreen(g);
//        }
//    }
//    
//    private void drawPauseScreen(Graphics g) {
//        g.setColor(new Color(0, 0, 0, 180));
//        g.fillRect(0, 0, getWidth(), getHeight());
//        g.setColor(Color.WHITE);
//        g.setFont(new Font("微软雅黑", Font.BOLD, 48));
//        g.drawString("游戏已暂停", getWidth() / 2 - 120, getHeight() / 2);
//        g.setFont(new Font("微软雅黑", Font.PLAIN, 24));
//        g.drawString("按P键继续", getWidth() / 2 - 60, getHeight() / 2 + 40);
//    }
//
//    private void drawMenuScreen(Graphics g) {
//        g.setColor(new Color(0, 0, 0, 180));
//        g.fillRect(0, 0, getWidth(), getHeight());
//        g.setColor(Color.WHITE);
//        g.setFont(new Font("微软雅黑", Font.BOLD, 48));
//        g.drawString("主菜单", getWidth() / 2 - 80, getHeight() / 2);
//        g.setFont(new Font("微软雅黑", Font.PLAIN, 24));
//        g.drawString("按空格键开始", getWidth() / 2 - 80, getHeight() / 2 + 40);
//    }
//    
//    // 关卡选择界面
//    private void drawLevelSelect(Graphics g) {
//        ImageIcon bgIcon = new ImageIcon("image/background/8.png");
//        if (bgIcon.getImage() != null) {
//            g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
//        } else {
//            g.setColor(Color.BLACK);
//            g.fillRect(0, 0, getWidth(), getHeight());
//        }
//        g.setColor(new Color(0, 0, 0, 150));
//        g.fillRect(0, 0, getWidth(), getHeight());
//
//        g.setColor(Color.WHITE);
//        g.setFont(menuFont.deriveFont((float)sw(90)));
//        String title = "选择关卡";
//        int titleWidth = g.getFontMetrics().stringWidth(title);
//        g.drawString(title, (getWidth() - titleWidth) / 2, sy(150));
//
//        g.setFont(levelFont.deriveFont((float)sw(40)));
//        for (int i = 1; i <= 3; i++) {
//            if (i == selectedLevel) {
//                g.setColor(Color.YELLOW);
//            } else {
//                g.setColor(Color.WHITE);
//            }
//            String levelText = "第 " + i + " 关";
//            int levelWidth = g.getFontMetrics().stringWidth(levelText);
//            g.drawString(levelText, (getWidth() - levelWidth) / 2, sy(250 + i * 60));
//        }
//    }
//
//    public void selectLevelUp() {
//        if (selectedLevel > 1) selectedLevel--;
//    }
//    public void selectLevelDown() {
//        if (selectedLevel < 3) selectedLevel++;
//    }
//    public int getSelectedLevel() { return selectedLevel; }
//
//    public enum ShipType {
//    	DEFAULT("标准战机", "player_1", 5, 1, 100, 1, "straight"),
//        FAST("闪电战机", "player_2", 8, 1, 80, 2, "fan"),
//        HEAVY("重装战机", "player_3", 3, 2, 150, 1, "circle"),
//        BALANCED("均衡战机", "player_4", 6, 1, 120, 2, "curve"),
//        SPECIAL("幽灵战机", "player_19", 7, 3, 90, 3, "circle");
//
//        private final String name;
//        private final String imageKey;
//        private final int speed;
//        private final int power;
//        private final int health;
//        private final int fireRate;
//        private final String bulletMode;
//
//        ShipType(String name, String imageKey, int speed, int power, int health, int fireRate, String bulletMode) {
//            this.name = name;
//            this.imageKey = imageKey;
//            this.speed = speed;
//            this.power = power;
//            this.health = health;
//            this.fireRate = fireRate;
//            this.bulletMode = bulletMode;
//        }
//
//        public String getName() { return name; }
//        public String getImageKey() { return imageKey; }
//        public int getSpeed() { return speed; }
//        public int getPower() { return power; }
//        public int getHealth() { return health; }
//        public int getFireRate() { return fireRate; }
//        public String getBulletMode() {
//			return bulletMode;
//		}
//
//        public void applyToPlayer(Play player) {
//            player.setSpeed(this.speed);
//            player.setAttack(this.power);
//            player.setMaxHealth(this.health);
//            player.setFireRate(this.fireRate);
//            player.setBulletMode(this.bulletMode);
//            ImageIcon icon = GameLoad.imgMap.get(this.imageKey);
//            if(icon != null) {
//                player.setIcon(icon);
//                player.setW(icon.getIconWidth());
//                player.setH(icon.getIconHeight());
//            }
//        }
//
//		
//    }
//
//    public void selectShipUp() {
//        if (selectedShip > 0) selectedShip--;
//    }
//    public void selectShipDown() {
//        if (selectedShip < ShipType.values().length - 1) selectedShip++;
//    }
//    public int getSelectedShipIndex() { return selectedShip; }
//
//    private void drawShipSelect(Graphics g) {
//        ImageIcon bgIcon = new ImageIcon("image/background/10.jpg");
//        if (bgIcon.getImage() != null) {
//            g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
//        }
//        int contentHeight = sh(650);
//        g.setColor(new Color(0, 0, 0, 150));
//        g.fillRect(0, 0, getWidth(), contentHeight);
//
//        g.setColor(Color.WHITE);
//        g.setFont(menuFont.deriveFont((float)sw(90)));
//        String title = "选择战机";
//        int titleWidth = g.getFontMetrics().stringWidth(title);
//        g.drawString(title, (getWidth() - titleWidth)/2, sy(100));
//
//        ShipType[] ships = ShipType.values();
//        int startY = sy(150);
//        int optionSpacing = sh(120);
//
//        for (int i = 0; i < ships.length; i++) {
//            int yPos = startY + i * optionSpacing;
//            g.setColor(i == selectedShip ? Color.YELLOW : Color.WHITE);
//            ImageIcon shipIcon = GameLoad.imgMap.get(ships[i].getImageKey());
//            if (shipIcon != null) {
//                g.drawImage(shipIcon.getImage(),
//                        getWidth()/2 - sw(40),
//                        yPos - sh(60),
//                        sw(80), sh(80), null);
//            }
//            g.setFont(shipNameFont.deriveFont((float)sw(24)));
//            String shipText = ships[i].name;
//            int textWidth = g.getFontMetrics().stringWidth(shipText);
//            g.drawString(shipText, (getWidth() - textWidth)/2, yPos);
//        }
//
//        int tableY = startY + ships.length * optionSpacing;
//        drawAttributeTable(g, tableY);
//
//        g.setColor(Color.WHITE);
//        g.setFont(shipAttrFont.deriveFont((float)sw(18)));
//        String hint = "方向键选择，回车键确认";
//        int hintWidth = g.getFontMetrics().stringWidth(hint);
//        g.drawString(hint, (getWidth() - hintWidth)/2, tableY + sh(120));
//    }
//
//    private void drawAttributeTable(Graphics g, int startY) {
//        ShipType currentShip = ShipType.values()[selectedShip];
//        g.setFont(shipAttrFont.deriveFont((float)sw(18)));
//        String[] attrs = {
//            "速度: " + currentShip.getSpeed(),
//            "火力: " + currentShip.getPower(),
//            "耐久: " + currentShip.getHealth(),
//            "射速: " + getFireRateText(currentShip.getFireRate())
//        };
//        int rowHeight = sh(30);
//        int tableWidth = sw(300);
//        int tableX = (getWidth() - tableWidth)/2;
//        g.setColor(new Color(0, 0, 0, 200));
//        g.fillRoundRect(tableX, startY, tableWidth, rowHeight*3, 10, 10);
//        g.setColor(Color.WHITE);
//        for (int i = 0; i < attrs.length; i++) {
//            g.drawString(attrs[i], tableX + sw(20), startY + sh(20) + i*rowHeight);
//        }
//    }
//
//    private String getFireRateText(int rate) {
//        switch(rate) {
//            case 1: return "慢速";
//            case 2: return "中速";
//            case 3: return "快速";
//            default: return "标准";
//        }
//    }
//
//    private void drawMenu(Graphics g) {
//        ImageIcon bgIcon = new ImageIcon("image/background/8.png");
//        if (bgIcon.getImage() != null) {
//            g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
//        } else {
//            g.setColor(Color.BLACK);
//            g.fillRect(0, 0, getWidth(), getHeight());
//        }
//        g.setColor(new Color(0, 0, 0, 150));
//        g.fillRect(0, 0, getWidth(), getHeight());
//
//        g.setFont(menuFont.deriveFont((float)sw(90)));
//        String title = "飞机大战";
//        FontMetrics titleFm = g.getFontMetrics();
//        int titleX = (getWidth() - titleFm.stringWidth(title)) / 2;
//        int titleY = getHeight() / 2;
//        g.setColor(Color.WHITE);
//        g.drawString(title, titleX, titleY);
//
//        g.setFont(infoFont.deriveFont((float)sw(20)));
//        String startInfo = "按空格键开始游戏";
//        FontMetrics infoFm = g.getFontMetrics();
//        int infoX = (getWidth() - infoFm.stringWidth(startInfo)) / 2;
//        int infoY = titleY + sh(100);
//        g.drawString(startInfo, infoX, infoY);
//    }
//
//    private void drawGame(Graphics g) {
//        Map<GameElement, List<ElementObj>> all = em.getGameElements();
//        List<ElementObj> backgrounds = all.get(GameElement.BACKGROUND);
//        if (!backgrounds.isEmpty()) {
//            ElementObj bg = backgrounds.get(0);
//            ImageIcon bgIcon = bg.getIcon();
//            if (bgIcon != null) {
//                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
//            } else {
//                bg.showElement(g);
//            }
//        }
//        for (GameElement ge : GameElement.values()) {
//            if (ge == GameElement.BACKGROUND) continue;
//            List<ElementObj> elementsCopy = new CopyOnWriteArrayList<>(all.get(ge));
//            for (ElementObj obj : elementsCopy) {
//                if (obj.isLive()) {
//                    drawElementScaled(obj, g);
//                }
//            }
//        }
//        drawHUD(g);
//    }
//
//    // 元素缩放绘制
//    private void drawElementScaled(ElementObj obj, Graphics g) {
//        ImageIcon icon = obj.getIcon();
//        if (icon != null && icon.getImage() != null) {
//            g.drawImage(icon.getImage(), sx(obj.getX()), sy(obj.getY()), sw(obj.getW()), sh(obj.getH()), null);
//        } else {
//            obj.showElement(g);
//        }
//    }
//
//    private void drawGameOver(Graphics g) {
//        g.setColor(new Color(0, 0, 0, 150));
//        g.fillRect(0, 0, getWidth(), getHeight());
//
//        // 游戏结束大标题
//        g.setColor(Color.RED);
//        g.setFont(menuFont.deriveFont((float)sw(90)));
//        String gameOver = "游戏结束";
//        int gameOverWidth = g.getFontMetrics().stringWidth(gameOver);
//        g.drawString(gameOver, (getWidth() - gameOverWidth) / 2, sy(180));
//
//        // 最终得分
//        g.setColor(Color.WHITE);
//        g.setFont(infoFont.deriveFont((float)sw(24)));
//        String scoreText = "最终得分: " + gameThread.getScore();
//        int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
//        g.drawString(scoreText, (getWidth() - scoreWidth) / 2, sy(250));
//
//        // 排行榜标题
//        g.setFont(new Font("微软雅黑", Font.BOLD, 28));
//        g.setColor(Color.YELLOW);
//        String rankTitle = "排行榜 TOP 5";
//        int rankTitleWidth = g.getFontMetrics().stringWidth(rankTitle);
//        g.drawString(rankTitle, (getWidth() - rankTitleWidth) / 2, sy(320));
//
//        // 排行榜内容
//        List<Integer> topScores = AchievementsManager.getTopScores(5);
//        String[] names = {"chen", "最强王者", "勇敢牛牛", "木子李", "强哥"};
//        while (topScores.size() < names.length) topScores.add(0);
//
//        g.setFont(new Font("微软雅黑", Font.PLAIN, 22));
//        g.setColor(Color.WHITE);
//
//        // 柱状图参数
//        int labelX = getWidth()/2 - 180; // 名字更靠左
//        int barStartX = getWidth()/2 - 60; // 柱状图更靠右
//        int barStartY = sy(360);
//        int barHeight = 24;
//        int barMaxWidth = 220;
//        int barGap = 38;
//
//        // 计算最大分数
//        int maxScore = 1;
//        for (int s : topScores) if (s > maxScore) maxScore = s;
//
//        for (int i = 0; i < names.length; i++) {
//            int y = barStartY + i * barGap;
//            // 名次和名字
//            String label = (i + 1) + ". " + names[i];
//            g.setColor(Color.WHITE);
//            g.setFont(new Font("微软雅黑", Font.PLAIN, 22));
//            g.drawString(label, labelX, y + barHeight - 6);
//
//            // 柱状图
//            int barWidth = (int)(barMaxWidth * (topScores.get(i) / (double)maxScore));
//            // 渐变色
//            Graphics2D g2d = (Graphics2D) g;
//            GradientPaint gp = new GradientPaint(barStartX, y, new Color(80, 180, 255),
//                                                 barStartX + barWidth, y, new Color(180, 220, 255));
//            g2d.setPaint(gp);
//            g2d.fillRect(barStartX, y, barWidth, barHeight);
//
//            // 分数
//            g.setColor(Color.BLACK);
//            g.setFont(new Font("微软雅黑", Font.BOLD, 18));
//            g.drawString(String.valueOf(topScores.get(i)), barStartX + barWidth + 16, y + barHeight - 6);
//        }
//
//        // 返回主菜单提示
//        g.setFont(infoFont.deriveFont((float)sw(20)));
//        String restartInfo = "按空格键返回主菜单";
//        int restartWidth = g.getFontMetrics().stringWidth(restartInfo);
//        g.setColor(Color.WHITE);
//        g.drawString(restartInfo, (getWidth() - restartWidth) / 2, sy(580));
//    }
//
////    private void drawHUD(Graphics g) {
////        List<ElementObj> players = new CopyOnWriteArrayList<>(em.getElementsByKey(GameElement.PLAY));
////        if (!players.isEmpty()) {
////            Play player = (Play) players.get(0);
////            g.setFont(scoreFont.deriveFont((float)sw(20)));
////            g.setColor(Color.WHITE);
////            g.drawString("Score: " + gameThread.getScore(), sx(20), sy(30));
////            g.drawString("Life: " + player.getLife(), sx(20), sy(60));
////            g.drawString("Kills: " + gameThread.getEnemiesKilled(), sx(20), sy(90));
////
////            // 血条渐变
////            Graphics2D g2d = (Graphics2D) g;
////            GradientPaint gp = new GradientPaint(sx(20), sy(70), Color.RED, sx(120), sy(70), Color.ORANGE);
////            g2d.setPaint(gp);
////            g2d.fillRect(sx(20), sy(70), sw(player.getLife() * 30), sh(10));
////
////            // 能量条
////            g2d.setPaint(new GradientPaint(sx(20), sy(90), Color.BLUE, sx(120), sy(90), Color.CYAN));
////            g2d.fillRect(sx(20), sy(90), sw(player.getPower() * 30), sh(10));
////
////            g.setColor(Color.WHITE);
////            g.drawString("Life: " + player.getLife(), sx(20), sy(60));
////        }
////    }
//    
//    private void drawHUD(Graphics g) {
//        List<ElementObj> players = new CopyOnWriteArrayList<>(em.getElementsByKey(GameElement.PLAY));
//        if (!players.isEmpty()) {
//            Play player = (Play) players.get(0);
//
//            int baseX = sx(20);
//            int baseY = sy(30);
//            int lineHeight = sh(28);
//            int barWidth = sw(120), barHeight = sh(14);
//
//            g.setFont(scoreFont.deriveFont((float)sw(20)));
//            g.setColor(Color.WHITE);
//            // Score
//            g.drawString("Score: " + gameThread.getScore(), baseX, baseY);
//
//            // Life
//            int lifeY = baseY + lineHeight;
//            g.drawString("Life: " + player.getLife() + "/" + player.getMaxLife(), baseX, lifeY);
//
//            // 血条（下移一些，比如+sh(14)）
//            int lifeBarY = lifeY + sh(14);
//            int lifeBarLen = (int)(barWidth * (player.getLife() / (float)player.getMaxLife()));
//            Graphics2D g2d = (Graphics2D) g;
//            GradientPaint gp = new GradientPaint(baseX, lifeBarY, Color.RED, baseX + barWidth, lifeBarY, Color.ORANGE);
//            g2d.setPaint(gp);
//            g2d.fillRect(baseX, lifeBarY, lifeBarLen, barHeight);
//            g2d.setColor(Color.WHITE);
//            g2d.drawRect(baseX, lifeBarY, barWidth, barHeight);
//
//            // Kills（也下移一些，比如+sh(18)）
//            int killsY = lifeBarY + barHeight + sh(18);
//            g.setColor(Color.WHITE);
//            g.drawString("Kills: " + gameThread.getEnemiesKilled(), baseX, killsY);
//
//            // Power
//            int powerY = killsY + lineHeight;
//            g.drawString("Power: " + player.getPower() + "/" + player.getMaxPower(), baseX, powerY);
//
//            // 能量条
//            int powerBarY = powerY + sh(6);
//            int powerBarLen = (int)(barWidth * (player.getPower() / (float)player.getMaxPower()));
//            GradientPaint gp2 = new GradientPaint(baseX, powerBarY, Color.BLUE, baseX + barWidth, powerBarY, Color.CYAN);
//            g2d.setPaint(gp2);
//            g2d.fillRect(baseX, powerBarY, powerBarLen, barHeight);
//            g2d.setColor(Color.WHITE);
//            g2d.drawRect(baseX, powerBarY, barWidth, barHeight);
//        }
//    }
//    @Override
//    public void run() {
//        while (true) {
//            this.repaint();
//            try {
//                Thread.sleep(50); // 控制帧率约20FPS
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

package com.tedu.show;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import com.tedu.manager.AchievementsManager;
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
    private Font infoFont = new Font("微软雅黑", Font.PLAIN, 20);
    private GameThread gameThread;

    private Font levelFont = new Font("微软雅黑",Font.BOLD,40);
    private int selectedLevel = 1;

    private int selectedShip = 0; // 当前选中的飞机索引
    private Font shipAttrFont = new Font("微软雅黑", Font.PLAIN, 18); // 飞机属性字体
    private Font shipNameFont = new Font("微软雅黑", Font.BOLD, 24); // 战机名称字体 
    private String storyText = null;

    // 主菜单选项
    private String[] menuItems = {"开始游戏", "排行榜", "退出游戏"};
    private int menuIndex = 0; // 当前高亮的菜单项
    private boolean inRank = false; // 是否在排行榜界面

    // 原始分辨率
    private static final int BASE_W = 800;
    private static final int BASE_H = 600;

    public GameMainJPanel(GameThread gameThread) {
        this.gameThread = gameThread;
        init();
    }

    public void init() {
        em = ElementManager.getManager();
    }

    public void setStoryText(String text) {
        this.storyText = text;
        this.repaint();
    }

    // 缩放工具
    private int sx(int x) { return (int)(x * getWidth() / (double)BASE_W); }
    private int sy(int y) { return (int)(y * getHeight() / (double)BASE_H); }
    private int sw(int w) { return (int)(w * getWidth() / (double)BASE_W); }
    private int sh(int h) { return (int)(h * getHeight() / (double)BASE_H); }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g);

        // 主菜单和排行榜
        if (gameThread.getGameState() == GameThread.GameState.MENU) {
            if (inRank) {
                drawRank(g);
            } else {
                drawMenu(g);
            }
            return;
        }

        switch (gameThread.getGameState()) {
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

        // 剧情文本绘制（覆盖在所有界面上）
        if (storyText != null) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, getHeight() / 2 - sh(60), getWidth(), sh(120));
            g.setColor(Color.YELLOW);
            g.setFont(new Font("微软雅黑", Font.BOLD, sw(36)));
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(storyText);
            g.drawString(storyText, (getWidth() - textWidth) / 2, getHeight() / 2 + sh(15));
        }

        if (gameThread.getGameState() == GameThread.GameState.PAUSED) {
            drawPauseScreen(g);
        }
    }

    // 主菜单绘制
    private void drawMenu(Graphics g) {
        ImageIcon bgIcon = new ImageIcon("image/background/8.png");
        if (bgIcon.getImage() != null) {
            g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        // 游戏大标题
        g.setFont(menuFont.deriveFont((float)sw(90)));
        String title = "飞机大战";
        FontMetrics titleFm = g.getFontMetrics();
        int titleX = (getWidth() - titleFm.stringWidth(title)) / 2;
        int titleY = sy(180);
        g.setColor(Color.WHITE);
        g.drawString(title, titleX, titleY);

        // 菜单选项
        g.setFont(new Font("微软雅黑", Font.BOLD, sw(40)));
        int menuStartY = titleY + sh(80);
        int menuGap = sh(60);
        for (int i = 0; i < menuItems.length; i++) {
            if (i == menuIndex) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            String item = menuItems[i];
            int itemWidth = g.getFontMetrics().stringWidth(item);
            g.drawString(item, (getWidth() - itemWidth) / 2, menuStartY + i * menuGap);
        }

        // 操作提示
        g.setFont(infoFont.deriveFont((float)sw(20)));
        g.setColor(Color.WHITE);
        String tip = "↑↓选择，回车/空格确认";
        int tipWidth = g.getFontMetrics().stringWidth(tip);
        g.drawString(tip, (getWidth() - tipWidth) / 2, menuStartY + menuItems.length * menuGap + sh(30));
    }

    // 排行榜界面
    private void drawRank(Graphics g) {
        ImageIcon bgIcon = new ImageIcon("image/background/8.png");
        if (bgIcon.getImage() != null) {
            g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(menuFont.deriveFont((float)sw(60)));
        g.setColor(Color.WHITE);
        String rankTitle = "排行榜";
        int titleWidth = g.getFontMetrics().stringWidth(rankTitle);
        g.drawString(rankTitle, (getWidth() - titleWidth) / 2, sy(120));

        // 排行榜内容
        List<Integer> topScores = AchievementsManager.getTopScores(5);
        String[] names = {"chen", "最强王者", "勇敢牛牛", "木子李", "强哥"};
        while (topScores.size() < names.length) topScores.add(0);

        g.setFont(new Font("微软雅黑", Font.PLAIN, sw(28)));
        g.setColor(Color.YELLOW);

        int startY = sy(200);
        int gap = sh(50);
        for (int i = 0; i < names.length; i++) {
            String line = String.format("%d. %-6s  %d", i + 1, names[i], topScores.get(i));
            int lineWidth = g.getFontMetrics().stringWidth(line);
            g.drawString(line, (getWidth() - lineWidth) / 2, startY + i * gap);
        }

        g.setFont(infoFont.deriveFont((float)sw(20)));
        g.setColor(Color.WHITE);
        String tip = "按ESC返回主菜单";
        int tipWidth = g.getFontMetrics().stringWidth(tip);
        g.drawString(tip, (getWidth() - tipWidth) / 2, sy(550));
    }

    // 关卡选择界面
    private void drawLevelSelect(Graphics g) {
        ImageIcon bgIcon = new ImageIcon("image/background/8.png");
        if (bgIcon.getImage() != null) {
            g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(menuFont.deriveFont((float)sw(90)));
        String title = "选择关卡";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (getWidth() - titleWidth) / 2, sy(150));

        g.setFont(levelFont.deriveFont((float)sw(40)));
        for (int i = 1; i <= 3; i++) {
            if (i == selectedLevel) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            String levelText = "第 " + i + " 关";
            int levelWidth = g.getFontMetrics().stringWidth(levelText);
            g.drawString(levelText, (getWidth() - levelWidth) / 2, sy(250 + i * 60));
        }
    }

    public void selectLevelUp() {
        if (selectedLevel > 1) selectedLevel--;
    }
    public void selectLevelDown() {
        if (selectedLevel < 3) selectedLevel++;
    }
    public int getSelectedLevel() { return selectedLevel; }

    public enum ShipType {
        DEFAULT("标准战机", "player_1", 5, 1, 100, 1, "straight"),
        FAST("闪电战机", "player_2", 8, 1, 80, 2, "fan"),
        HEAVY("重装战机", "player_3", 3, 2, 150, 1, "circle"),
        BALANCED("均衡战机", "player_4", 6, 1, 120, 2, "curve"),
        SPECIAL("幽灵战机", "player_19", 7, 3, 90, 3, "circle");

        private final String name;
        private final String imageKey;
        private final int speed;
        private final int power;
        private final int health;
        private final int fireRate;
        private final String bulletMode;

        ShipType(String name, String imageKey, int speed, int power, int health, int fireRate, String bulletMode) {
            this.name = name;
            this.imageKey = imageKey;
            this.speed = speed;
            this.power = power;
            this.health = health;
            this.fireRate = fireRate;
            this.bulletMode = bulletMode;
        }

        public String getName() { return name; }
        public String getImageKey() { return imageKey; }
        public int getSpeed() { return speed; }
        public int getPower() { return power; }
        public int getHealth() { return health; }
        public int getFireRate() { return fireRate; }
        public String getBulletMode() { return bulletMode; }

        public void applyToPlayer(Play player) {
            player.setSpeed(this.speed);
            player.setAttack(this.power);
            player.setMaxHealth(this.health);
            player.setFireRate(this.fireRate);
            player.setBulletMode(this.bulletMode);
            ImageIcon icon = GameLoad.imgMap.get(this.imageKey);
            if(icon != null) {
                player.setIcon(icon);
                player.setW(icon.getIconWidth());
                player.setH(icon.getIconHeight());
            }
        }
    }

    public void selectShipUp() {
        if (selectedShip > 0) selectedShip--;
    }
    public void selectShipDown() {
        if (selectedShip < ShipType.values().length - 1) selectedShip++;
    }
    public int getSelectedShipIndex() { return selectedShip; }

    private void drawShipSelect(Graphics g) {
        ImageIcon bgIcon = new ImageIcon("image/background/10.jpg");
        if (bgIcon.getImage() != null) {
            g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
        }
        int contentHeight = sh(650);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), contentHeight);

        g.setColor(Color.WHITE);
        g.setFont(menuFont.deriveFont((float)sw(90)));
        String title = "选择战机";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (getWidth() - titleWidth)/2, sy(100));

        ShipType[] ships = ShipType.values();
        int startY = sy(150);
        int optionSpacing = sh(120);

        for (int i = 0; i < ships.length; i++) {
            int yPos = startY + i * optionSpacing;
            g.setColor(i == selectedShip ? Color.YELLOW : Color.WHITE);
            ImageIcon shipIcon = GameLoad.imgMap.get(ships[i].getImageKey());
            if (shipIcon != null) {
                g.drawImage(shipIcon.getImage(),
                        getWidth()/2 - sw(40),
                        yPos - sh(60),
                        sw(80), sh(80), null);
            }
            g.setFont(shipNameFont.deriveFont((float)sw(24)));
            String shipText = ships[i].name;
            int textWidth = g.getFontMetrics().stringWidth(shipText);
            g.drawString(shipText, (getWidth() - textWidth)/2, yPos);
        }

        int tableY = startY + ships.length * optionSpacing;
        drawAttributeTable(g, tableY);

        g.setColor(Color.WHITE);
        g.setFont(shipAttrFont.deriveFont((float)sw(18)));
        String hint = "方向键选择，回车键确认";
        int hintWidth = g.getFontMetrics().stringWidth(hint);
        g.drawString(hint, (getWidth() - hintWidth)/2, tableY + sh(120));
    }

    private void drawAttributeTable(Graphics g, int startY) {
        ShipType currentShip = ShipType.values()[selectedShip];
        g.setFont(shipAttrFont.deriveFont((float)sw(18)));
        String[] attrs = {
            "速度: " + currentShip.getSpeed(),
            "火力: " + currentShip.getPower(),
            "耐久: " + currentShip.getHealth(),
            "射速: " + getFireRateText(currentShip.getFireRate())
        };
        int rowHeight = sh(30);
        int tableWidth = sw(300);
        int tableX = (getWidth() - tableWidth)/2;
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRoundRect(tableX, startY, tableWidth, rowHeight*3, 10, 10);
        g.setColor(Color.WHITE);
        for (int i = 0; i < attrs.length; i++) {
            g.drawString(attrs[i], tableX + sw(20), startY + sh(20) + i*rowHeight);
        }
    }

    private String getFireRateText(int rate) {
        switch(rate) {
            case 1: return "慢速";
            case 2: return "中速";
            case 3: return "快速";
            default: return "标准";
        }
    }

    private void drawGame(Graphics g) {
        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        List<ElementObj> backgrounds = all.get(GameElement.BACKGROUND);
        if (!backgrounds.isEmpty()) {
            ElementObj bg = backgrounds.get(0);
            ImageIcon bgIcon = bg.getIcon();
            if (bgIcon != null) {
                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
            } else {
                bg.showElement(g);
            }
        }
        for (GameElement ge : GameElement.values()) {
            if (ge == GameElement.BACKGROUND) continue;
            List<ElementObj> elementsCopy = new CopyOnWriteArrayList<>(all.get(ge));
            for (ElementObj obj : elementsCopy) {
                if (obj.isLive()) {
                    drawElementScaled(obj, g);
                }
            }
        }
        drawHUD(g);
    }

    // 元素缩放绘制
    private void drawElementScaled(ElementObj obj, Graphics g) {
        ImageIcon icon = obj.getIcon();
        if (icon != null && icon.getImage() != null) {
            g.drawImage(icon.getImage(), sx(obj.getX()), sy(obj.getY()), sw(obj.getW()), sh(obj.getH()), null);
        } else {
            obj.showElement(g);
        }
    }

    private void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        // 游戏结束大标题
        g.setColor(Color.RED);
        g.setFont(menuFont.deriveFont((float)sw(90)));
        String gameOver = "游戏结束";
        int gameOverWidth = g.getFontMetrics().stringWidth(gameOver);
        g.drawString(gameOver, (getWidth() - gameOverWidth) / 2, sy(180));

        // 最终得分
        g.setColor(Color.WHITE);
        g.setFont(infoFont.deriveFont((float)sw(24)));
        String scoreText = "最终得分: " + gameThread.getScore();
        int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
        g.drawString(scoreText, (getWidth() - scoreWidth) / 2, sy(250));

        // 排行榜标题
        g.setFont(new Font("微软雅黑", Font.BOLD, 28));
        g.setColor(Color.YELLOW);
        String rankTitle = "排行榜 TOP 5";
        int rankTitleWidth = g.getFontMetrics().stringWidth(rankTitle);
        g.drawString(rankTitle, (getWidth() - rankTitleWidth) / 2, sy(320));

        // 排行榜内容
        List<Integer> topScores = AchievementsManager.getTopScores(5);
        String[] names = {"chen", "最强王者", "勇敢牛牛", "木子李", "强哥"};
        while (topScores.size() < names.length) topScores.add(0);

        g.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        g.setColor(Color.WHITE);

        // 柱状图参数
        int labelX = getWidth()/2 - 180;
        int barStartX = getWidth()/2 - 60;
        int barStartY = sy(360);
        int barHeight = 24;
        int barMaxWidth = 220;
        int barGap = 38;

        int maxScore = 1;
        for (int s : topScores) if (s > maxScore) maxScore = s;

        for (int i = 0; i < names.length; i++) {
            int y = barStartY + i * barGap;
            String label = (i + 1) + ". " + names[i];
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑", Font.PLAIN, 22));
            g.drawString(label, labelX, y + barHeight - 6);

            int barWidth = (int)(barMaxWidth * (topScores.get(i) / (double)maxScore));
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(barStartX, y, new Color(80, 180, 255),
                                                 barStartX + barWidth, y, new Color(180, 220, 255));
            g2d.setPaint(gp);
            g2d.fillRect(barStartX, y, barWidth, barHeight);

            g.setColor(Color.BLACK);
            g.setFont(new Font("微软雅黑", Font.BOLD, 18));
            g.drawString(String.valueOf(topScores.get(i)), barStartX + barWidth + 16, y + barHeight - 6);
        }

        g.setFont(infoFont.deriveFont((float)sw(20)));
        String restartInfo = "按空格键返回主菜单";
        int restartWidth = g.getFontMetrics().stringWidth(restartInfo);
        g.setColor(Color.WHITE);
        g.drawString(restartInfo, (getWidth() - restartWidth) / 2, sy(580));
    }

    private void drawHUD(Graphics g) {
        List<ElementObj> players = new CopyOnWriteArrayList<>(em.getElementsByKey(GameElement.PLAY));
        if (!players.isEmpty()) {
            Play player = (Play) players.get(0);

            int baseX = sx(18);
            int baseY = sy(22);
            int lineHeight = sh(22);
            int barWidth = sw(110), barHeight = sh(10);

            g.setFont(scoreFont.deriveFont((float)sw(20)));
            g.setColor(Color.WHITE);
            g.drawString("Score: " + gameThread.getScore(), baseX, baseY);

            int lifeY = baseY + lineHeight;
            g.drawString("Life: " + player.getLife() + "/" + player.getMaxLife(), baseX, lifeY);

            int lifeBarY = lifeY + sh(14);
            int lifeBarLen = (int)(barWidth * (player.getLife() / (float)player.getMaxLife()));
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(baseX, lifeBarY, Color.RED, baseX + barWidth, lifeBarY, Color.ORANGE);
            g2d.setPaint(gp);
            g2d.fillRect(baseX, lifeBarY, lifeBarLen, barHeight);
            g2d.setColor(Color.WHITE);
            g2d.drawRect(baseX, lifeBarY, barWidth, barHeight);

            int killsY = lifeBarY + barHeight + sh(18);
            g.setColor(Color.WHITE);
            g.drawString("Kills: " + gameThread.getEnemiesKilled(), baseX, killsY);

            int powerY = killsY + lineHeight;
            g.drawString("Power: " + player.getPower() + "/" + player.getMaxPower(), baseX, powerY);

            int powerBarY = powerY + sh(6);
            int powerBarLen = (int)(barWidth * (player.getPower() / (float)player.getMaxPower()));
            GradientPaint gp2 = new GradientPaint(baseX, powerBarY, Color.BLUE, baseX + barWidth, powerBarY, Color.CYAN);
            g2d.setPaint(gp2);
            g2d.fillRect(baseX, powerBarY, powerBarLen, barHeight);
            g2d.setColor(Color.WHITE);
            g2d.drawRect(baseX, powerBarY, barWidth, barHeight);
        }
    }

    private void drawPauseScreen(Graphics g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑", Font.BOLD, 48));
        g.drawString("游戏已暂停", getWidth() / 2 - 120, getHeight() / 2);
        g.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        g.drawString("按P键继续", getWidth() / 2 - 60, getHeight() / 2 + 40);
    }

    // 菜单选项切换方法
    public void menuUp() {
        if (menuIndex > 0) menuIndex--;
        repaint();
    }
    public void menuDown() {
        if (menuIndex < menuItems.length - 1) menuIndex++;
        repaint();
    }
    public int getMenuIndex() {
        return menuIndex;
    }
    public void setMenuIndex(int idx) {
        menuIndex = idx;
        repaint();
    }
    public void setInRank(boolean inRank) {
        this.inRank = inRank;
        repaint();
    }
    public boolean isInRank() {
        return inRank;
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
