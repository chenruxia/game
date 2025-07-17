
package com.tedu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.tedu.element.Background;
import com.tedu.element.Boss;
import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.Explosion;
import com.tedu.element.Item;
import com.tedu.element.Play;
import com.tedu.element.PlayFile;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.manager.LevelData;
import com.tedu.manager.SoundManager;
import com.tedu.show.GameMainJPanel;
import com.tedu.show.GameMainJPanel.ShipType;
import com.tedu.manager.AchievementsManager;

public class GameThread extends Thread {

    public enum GameState {
        MENU, RUNNING, GAME_OVER, PAUSED, LEVEL_SELECT, SHIP_SELECT
    }

    private GameState gameState = GameState.MENU;
    private boolean running = true;
    private ElementManager em;
    private Random random = new Random();
    private long gameTime = 0L;
    private int score = 0;
    private int enemyCount = 0;
    private boolean bossSpawned = false;
    private boolean doubleScoreActive = false;
    private long doubleScoreEndTime = 0;
    private List<LevelData> levels = new ArrayList<>();
    private int currentLevel = 1;
    private int enemiesDefeated = 0;
    private ShipType selectedShip = ShipType.DEFAULT;
    private int selectedLevel = 1;
    private GameMainJPanel gamePanel;
    
    private int enemiesKilled = 0;
    public int getEnemiesKilled() { return enemiesKilled; }
    public void addEnemiesKilled() { enemiesKilled++; }

    public GameThread() {
        em = ElementManager.getManager();
    }

    public void setSelectedShip(int index) {
        this.selectedShip = ShipType.values()[index];
    }

    public ShipType getSelectedShip() {
        return selectedShip;
    }

    public void setSelectedLevel(int selectedLevel) {
        this.selectedLevel = selectedLevel;
    }

    public int getSelectedLevel() {
        return selectedLevel;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setPanel(GameMainJPanel panel) {
        this.gamePanel = panel;
    }

    public GameMainJPanel getPanel() {
        return gamePanel;
    }

    public void enterLevelSelect() {
        gameState = GameState.LEVEL_SELECT;
    }

    public void exitGame() {
        running = false;
    }

    public void restartGame() {
        em.init();
        setScore(0);
        currentLevel = 1;
        gameState = GameState.MENU;
    }

    public void pauseGame() {
        if (gameState == GameState.RUNNING) {
            gameState = GameState.PAUSED;
            SoundManager.getInstance().pauseBGM();
        }
    }

    public void resumeGame() {
        if (gameState == GameState.PAUSED) {
            gameState = GameState.RUNNING;
            SoundManager.getInstance().resumeBGM();
        }
    }



    
    public void startGame(int selectedLevel, int selectedShipIndex) {
        this.selectedLevel = selectedLevel;
        this.selectedShip = GameMainJPanel.ShipType.values()[selectedShipIndex];
        gameState = GameState.RUNNING;
        currentLevel = selectedLevel;
        em.init();
        createPlayers();
    }
    
    // 剧情显示方法
    private void showStory(String text, int durationMillis) {
        if (gamePanel != null) {
            gamePanel.setStoryText(text);
            try {
                Thread.sleep(durationMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gamePanel.setStoryText(null);
        }
    }
    
    private void createPlayers() {
        // 玩家1
        Play player1 = (Play) new Play().createElement("400,550," + selectedShip.getImageKey());
        selectedShip.applyToPlayer(player1);
        player1.setPlayerId(0);
        em.addElement(player1, GameElement.PLAY);
        // 玩家2（如需双人，取消注释）
        // Play player2 = (Play) new Play().createElement("200,550,player_2");
        // ShipType.FAST.applyToPlayer(player2);
        // player2.setPlayerId(1);
        // em.addElement(player2, GameElement.PLAY);
    }

    @Override
    public void run() {
        initLevels();
        while (running) {
            switch (gameState) {
                case MENU:
                    showMenu();
                    break;
                case RUNNING:
                    // 每关开始剧情
                    showStory("第 " + currentLevel + " 关开始！", 2000);
                    gameLoad();
                    gameRun();
                    // 每关结束剧情（如果不是最后一关）
                    if (currentLevel < levels.size()) {
                        showStory("第 " + currentLevel + " 关通过！", 2000);
                    }
                    break;
                case GAME_OVER:
                    showStory("游戏结束！", 2000);
                    gameOver();
                    break;
                default:
                    break;
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    

    private void showMenu() {
        // 可加剧情/动画
    }

    private void initLevels() {
        levels.add(new LevelData(1, 20, 100, 50, "1.png", 20, 5, 5));
        levels.add(new LevelData(2, 30, 150, 40, "2.jpg", 30, 8, 10));
        levels.add(new LevelData(3, 40, 200, 30, "3.jpg", 40, 13 ,20));
    }

    private void addScore(int baseScore) {
        int actualScore = baseScore * (isDoubleScoreActive() ? 2 : 1);
        setScore(getScore() + actualScore);
    }

    private boolean isDoubleScoreActive() {
        if (doubleScoreActive && System.currentTimeMillis() > doubleScoreEndTime) {
            doubleScoreActive = false;
        }
        return doubleScoreActive;
    }

    // 激活双倍分数
    public void activateDoubleScore(long duration) {
        doubleScoreActive = true;
        doubleScoreEndTime = System.currentTimeMillis() + duration;
    }

    private void gameLoad() {
        GameLoad.loadImg();
        GameLoad.loadSounds();

        if (levels.isEmpty()) {
            initLevels();
        }

        LevelData currentLevelData = levels.get(currentLevel - 1);

        ElementObj bg = new Background().createElement(currentLevelData.getBgImage());
        em.addElement(bg, GameElement.BACKGROUND);

        // 加载玩家
        createPlayers();

        enemiesDefeated = 0;
        bossSpawned = false;
        enemyCount = 0;
        gameTime = 0;
    }

    private void gameRun() {
        System.out.println("游戏主循环开始");
        System.out.println("当前关卡：" + currentLevel);
        while (true) {
            Map<GameElement, List<ElementObj>> all = em.getGameElements();

            spawnEnemies();
            moveAndUpdate(all, gameTime);

            checkCollisions();

            if (checkGameState()) {
                break;
            }

            if (gameTime % 500 == 0) {
                System.out.println("测试音效播放...");
                SoundManager.getInstance().playBGM("shoot");
            }
            if (checkLevelComplete()) {
                nextLevel();
                break;
            }
            
            if (checkLevelComplete()) {
                // 关卡胜利提示
                if (gamePanel != null) {
                    gamePanel.setStoryText("第 " + currentLevel + " 关通过！");
                }
                nextLevel();
                break;
            }
            
            if (gameState == GameState.PAUSED) {
                try { sleep(10); } catch (InterruptedException e) {}
                continue;
            }
            
            gameTime++;
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        
        
        
    }

    private void spawnEnemies() {
        LevelData levelData = levels.get(currentLevel - 1);
        if (gameTime == 0) {
            for (int i = 0; i < 3; i++) {
                createRandomEnemy(levelData.getEnemyTypes());
            }
        } else if (gameTime % levelData.getEnemySpawnRate() == 0) {
            createRandomEnemy(levelData.getEnemyTypes());
        }
        // Boss生成
        if (!bossSpawned && enemiesDefeated >= levelData.getEnemyCount() / 2) {
            spawnBoss(levelData.getBossHp());
        }
    }

    private void spawnBoss(int hp) {
        ElementObj boss = new Boss().createElement("");
        ((Boss) boss).setHp(hp);
        em.addElement(boss, GameElement.BOSS);
        bossSpawned = true;
    }

    private void createRandomEnemy(int maxType) {
        int type = random.nextInt(maxType) + 1;
        int x = random.nextInt(700) + 50;
        String enemyStr = type + "," + x + ",-50";
        ElementObj enemy = new Enemy().createElement(enemyStr);
        // 设置当前关卡的生命值
        ((Enemy)enemy).setHp(levels.get(currentLevel-1).getEnemyHp());
        em.addElement(enemy, GameElement.ENEMY);
        enemyCount++;
    }

    public void moveAndUpdate(Map<GameElement, List<ElementObj>> all, long gameTime) {
        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for (int i = list.size() - 1; i >= 0; i--) {
                ElementObj obj = list.get(i);
                if (!obj.isLive()) {
                    obj.die();
                    list.remove(i);
                    continue;
                }
                obj.model(gameTime);
            }
        }
    }

    private void checkCollisions() {
        List<ElementObj> playerBullets = em.getElementsByKey(GameElement.PLAYFILE);
        List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
        List<ElementObj> bosses = em.getElementsByKey(GameElement.BOSS);

        // 玩家子弹与敌人
        for (ElementObj bullet : playerBullets) {
            for (ElementObj enemy : enemies) {
                if (bullet.pk(enemy)) {
                    ((Enemy) enemy).takeDamage(((PlayFile) bullet).getAttack());
                    bullet.setLive(false);
                    addScore(10);
                    if (!((Enemy) enemy).isLive()) {
                        enemiesDefeated++;
                        trySpawnPowerUp(levels.get(currentLevel - 1).getPowerUpRate());
                    }
                    break;
                }
            }
            // 玩家子弹与Boss
            for (ElementObj boss : bosses) {
                if (bullet.pk(boss)) {
                    ((Boss) boss).takeDamage(((PlayFile) bullet).getAttack());
                    bullet.setLive(false);
                    addScore(50);
                    break;
                }
            }
        }

        // 敌机子弹与玩家
        List<ElementObj> enemyBullets = em.getElementsByKey(GameElement.ENEMYFILE);
        List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);

        for (ElementObj bullet : enemyBullets) {
            for (ElementObj player : players) {
                if (bullet.pk(player)) {
                    ((Play) player).decreaseLife();
                    bullet.setLive(false);
                    break;
                }
            }
        }

        // 道具与玩家
        List<ElementObj> items = em.getElementsByKey(GameElement.ITEM);
        for (ElementObj item : items) {
            for (ElementObj player : players) {
                if (item.pk(player)) {
                    ((Play) player).applyItemEffect(((Item) item).getType());
                    item.setLive(false);
                    break;
                }
            }
        }
    }

    private void trySpawnPowerUp(int powerUpRate) {
        if (random.nextInt(100) < powerUpRate) {
            int type = random.nextInt(10) + 1; // 支持10种道具
            int x = random.nextInt(700) + 50;
            String itemStr = "type:" + type + ",x:" + x + ",y:-30";
            em.addElement(new Item().createElement(itemStr), GameElement.ITEM);
        }
    }

    
    private boolean checkLevelComplete() {
        LevelData levelData = levels.get(currentLevel - 1);
        // 1. 敌人已全部生成
        boolean allEnemiesSpawned = enemyCount >= levelData.getEnemyCount();
        // 2. 所有敌机和Boss都被消灭
        boolean allEnemiesDead = em.getElementsByKey(GameElement.ENEMY).isEmpty()
                              && em.getElementsByKey(GameElement.BOSS).isEmpty();
        // 只有两者都满足才算通关
        return allEnemiesSpawned && allEnemiesDead;
    }

    private void nextLevel() {
        if (currentLevel < levels.size()) {
            currentLevel++;
            addLevelTransitionEffect();
            gameLoad();
            System.out.println("进入第 " + currentLevel + " 关!");
        } else {
            gameState = GameState.GAME_OVER;
            System.out.println("恭喜通关所有关卡!");
        }
    }

    private void addLevelTransitionEffect() {
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(800);
            int y = random.nextInt(600);
            ElementObj explosion = new Explosion().createElement(x + "," + y);
            em.addElement(explosion, GameElement.DIE);
        }
        SoundManager.getInstance().playSound("level_complete");
    }

    private boolean checkGameState() {
        List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);
        if (players.isEmpty()) {
            // 玩家全部死亡，游戏结束
            gameState = GameState.GAME_OVER;
            // 显示Game Over提示
            if (gamePanel != null) {
                gamePanel.setStoryText("游戏结束！");
            }
            return true;
        }
        return false;
    }

    private void gameOver() {
        SoundManager.getInstance().stopBGM();
        SoundManager.getInstance().playSound("gameover");
        
        // 保存分数到排行榜
        AchievementsManager.saveScore(getScore());
        
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        em.init();
        currentLevel = 1;
        enemyCount = 0;
        enemiesDefeated = 0;
        bossSpawned = false;
        setScore(0);
        gameTime = 0;
        gameState = GameState.MENU;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
