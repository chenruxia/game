package com.tedu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

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

public class GameThread extends Thread{
	
//	游戏状态量
	public enum GameState{
		MENU,RUNNING,GAME_OVER, PAUSED, LEVEL_SELECT,SHIP_SELECT
	}
	private GameState gameState = GameState.MENU;
	private boolean running = true;
	
	private ElementManager em;
	
	//新增加
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
	
	public void setSelectedShip(int index) {
	    this.selectedShip = ShipType.values()[index];
	}

	public ShipType getSelectedShip() {
	    return selectedShip;
	}
	
	public GameThread() {
		em = ElementManager.getManager();
	}
	
	public void createPlayer() {
	    ShipType shipType = getSelectedShip(); // 获取当前选择的战机类型
	    String shipImageKey = shipType.getImageKey();
	    
	    // 创建玩家实例
	    Play player = (Play) new Play().createElement("400,550," + shipImageKey);
	    
	    // 应用战机属性（速度、火力、生命值等）
	    shipType.applyToPlayer(player);
	    
	    // 添加到游戏元素管理器
	    em.addElement(player, GameElement.PLAY);
	}
	
	@Override
	public void run() {//游戏的run方法 主线程
		initLevels();
		while(running) {
			
			//扩展，可以将true变为一个变量用于控制结束
			//游戏开始前
			//游戏进行时
			//游戏场景结束
			switch(gameState) {
			case MENU:
				showMenu();
				break;
			case RUNNING:
				gameLoad();
				gameRun();
				break;
			case GAME_OVER:
				gameOver();
				break;
			}
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void enterLevelSelect() {
	    gameState = GameState.LEVEL_SELECT;
	}
	
	private void initLevels() {
		levels.add(new LevelData(1,20,100,50,"1.png",20,5));
		levels.add(new LevelData(2,30,150,40,"2.jpg",30,8));
		levels.add(new LevelData(3,40,200,30,"3.jpg",40,13));
		
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
    } void showMenu() {
		// TODO Auto-generated method stub
		
	}
	
	public void startGame() {
		gameState = GameState.RUNNING;
		currentLevel = selectedLevel;
	}
	private int selectedLevel = 1;
	
	public int getSelectedLevel() {
		return selectedLevel;
	}
	public void setSelectedLevel(int selectedLevel) {
		this.selectedLevel = selectedLevel;
	}
	
	public void exitGame() {
		running = false;
	}
	public GameState getGameState() {
		return gameState;
	}

	private void gameLoad() {
		
		GameLoad.loadImg();
		GameLoad.loadSounds();
		
		if(levels.isEmpty()) {
			initLevels();
		}
		
		LevelData currentLevelData = levels.get(currentLevel-1);
//		em.getElementsByKey(GameElement.ENEMY).clear();
//		em.getElementsByKey(GameElement.BOSS).clear();
//		em.getElementsByKey(GameElement.BACKGROUND).clear();
		
		ElementObj bg = new Background().createElement(currentLevelData.getBgImage());
		em.addElement(bg, GameElement.BACKGROUND);
		
		GameLoad.loadPlay();
		
		enemiesDefeated =0;
		bossSpawned =false;
		enemyCount = 0;
		gameTime = 0;
//	
		// 添加背景
//        ElementObj bg = new Background().createElement("");
//        em.addElement(bg, GameElement.BACKGROUND);
////		load();
//		加载主角
		//也可以带参数，单机还是两个人
//		加载npc等
//		全部加载完，游戏启动
		
//		GameLoad.loadEnemy();
		
	}
	//游戏进行时
	/*
	 * @任务说明 游戏过程中需要做的事情：1.自动化玩家的移动，碰撞，死亡
	 * 						2.新元素的增加
	 * 						
	 * */
	
	
	private void gameRun() {
		System.out.println("游戏主循环开始");
		System.out.println("当前关卡："+currentLevel);
//		long gameTime=0L;
		while(true) {
			//预留扩展true可以变为变量，用于控制管关卡结束等
			
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
//			List<ElementObj> enemys = em.getElementsByKey(GameElement.ENEMY);
//			List<ElementObj> files = em.getElementsByKey(GameElement.PLAYFILE);
//			List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
//			生成敌人
			spawnEnemies();
			moveAndUpdate(all,gameTime);//游戏自动化方法
			
//			ElementPK(enemys,files);
//			ElementPK(files,maps);
			
			//碰撞监测
			checkCollisions();
			//检查游戏状态
			if(checkGameState()) {
				break;
			}
			
			if(gameTime % 500 == 0) {
				System.out.println("测试音效播放...");
				SoundManager.getInstance().playBGM("shoot");
			}
			if(checkLevelComplete()) {
	            nextLevel();
	            break; // 退出当前游戏循环，进入新关卡
	        }
			
			
			gameTime++;
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	private void spawnEnemies() {
		// 初始快速生成几个敌机，然后按固定频率生成
//        if(gameTime < 50 || gameTime % 50 == 0) {
//            int type = random.nextInt(2) + 1; // 1或2
//            int x = random.nextInt(700) + 50;
//            String enemyStr = type + "," + x + ",-50";
//            ElementObj enemy = new Enemy().createElement(enemyStr);
//            em.addElement(enemy, GameElement.ENEMY);
//            enemyCount++;
//        }
		
		LevelData levelData = levels.get(currentLevel-1);
		//游戏开始后立即生成3个敌机
		if(gameTime == 0) {
			for(int i=0;i<3;i++) {
				createRandomEnemy(levelData.getEnemyTypes());
			}
		}else if(gameTime % levelData.getEnemySpawnRate() == 0) {
			createRandomEnemy(levelData.getEnemyTypes());
		}
        
		// 根据关卡配置生成Boss
	    if(!bossSpawned && enemiesDefeated >= levelData.getEnemyCount() / 2) {
	        spawnBoss(levelData.getBossHp());
	    }
		
//        // 每击败20个敌机生成Boss
//        if(enemyCount >= 20 && !bossSpawned) {
//            ElementObj boss = new Boss().createElement("");
//            em.addElement(boss, GameElement.BOSS);
//            bossSpawned = true;
//            enemyCount = 0;
//        }
	}
	
	private void spawnBoss(int hp) {
	    ElementObj boss = new Boss().createElement("");
	    ((Boss)boss).setHp(hp); // 设置Boss血量
	    em.addElement(boss, GameElement.BOSS);
	    bossSpawned = true;
	}
	
	private void createRandomEnemy(int maxType) {
	    int type = random.nextInt(maxType) + 1; // 根据关卡限制敌机类型
	    int x = random.nextInt(700) + 50;
	    String enemyStr = type + "," + x + ",-50";
	    ElementObj enemy = new Enemy().createElement(enemyStr);
	    em.addElement(enemy, GameElement.ENEMY);
	    enemyCount++;
	}
	
	

	
//	游戏元素自动化方法
	public void moveAndUpdate(Map<GameElement, List<ElementObj>> all,long gameTime) {
//		GameElement.values();//隐藏方法，返回值是一个数组，数组的顺序就是定义枚举的类型
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
//			for(int i=0;i<list.size();i++) {
			for(int i=list.size()-1;i>=0;i--){
				ElementObj obj=list.get(i);//读取为基类
				if(!obj.isLive()) {
//					启动一个死亡方法（方法中可以做事情例如：死亡动画，掉装备）
					obj.die();//需要自己补充
					list.remove(i);
//					list.remove(i--);
					continue;
				}
				obj.model(gameTime);
			}
		}
	}

	private void checkCollisions() {
		// 玩家子弹与敌人碰撞
        List<ElementObj> playerBullets = em.getElementsByKey(GameElement.PLAYFILE);
        List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
        List<ElementObj> bosses = em.getElementsByKey(GameElement.BOSS);
        
        //玩家子弹与敌机碰撞
        for(ElementObj bullet : playerBullets) {
            for(ElementObj enemy : enemies) {
            	if(bullet.pk(enemy)) {
            	    ((Enemy)enemy).takeDamage(((PlayFile)bullet).getAttack());
            	    bullet.setLive(false);
            	    addScore(10);
            	    
            	    // 检查敌机是否被击败
            	    if(!((Enemy)enemy).isLive()) {
            	        enemiesDefeated++;
            	        trySpawnPowerUp(levels.get(currentLevel - 1).getPowerUpRate());
            	    }
            	    break;
            	}
            }
            // 玩家子弹与Boss碰撞
            for(ElementObj boss : bosses) {
                if(bullet.pk(boss)) {
                    ((Boss)boss).takeDamage(((PlayFile)bullet).getAttack());
                    bullet.setLive(false);
                    addScore(50);
                    break;
                }
            }
        }
        
     // 敌机子弹与玩家碰撞
        List<ElementObj> enemyBullets = em.getElementsByKey(GameElement.ENEMYFILE);
        List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);
        
        for(ElementObj bullet : enemyBullets) {
            for(ElementObj player : players) {
                if(bullet.pk(player)) {
                    ((Play)player).decreaseLife();
                    bullet.setLive(false);
                    break;
                }
            }
        }
        
     // 道具与玩家碰撞
        List<ElementObj> items = em.getElementsByKey(GameElement.ITEM);
        for(ElementObj item : items) {
            for(ElementObj player : players) {
                if(item.pk(player)) {
                    switch(((Item)item).getType()) {
                        case 1: // 火力升级
                            ((Play)player).increasePower();
                            break;
                        case 2: // 生命恢复
                            // 可以添加生命恢复逻辑
                            break;
                    }
                    item.setLive(false);
                    break;
                }
            }
        }
	}
	
	
	private void trySpawnPowerUp(int powerUpRate) {
	    if(random.nextInt(100) < powerUpRate) {
	        int type = random.nextInt(7) + 1; // 1-7种道具类型
	        int x = random.nextInt(700) + 50;
	        String itemStr = "type:" + type + ",x:" + x + ",y:-30";
	        em.addElement(new Item().createElement(itemStr), GameElement.ITEM);
	    }
	}
	
	private boolean checkLevelComplete() {
	    LevelData levelData = levels.get(currentLevel - 1);
	    
	    // 条件1: 击败足够数量的敌机
	    // 条件2: Boss已被击败(如果生成了Boss)
	    return enemiesDefeated >= levelData.getEnemyCount() && 
	           (!bossSpawned || em.getElementsByKey(GameElement.BOSS).isEmpty());
	}
	
	private void nextLevel() {
	    if(currentLevel < levels.size()) {
	        currentLevel++;
	        addLevelTransitionEffect();
	        gameLoad(); // 加载新关卡
	        
	        // 可以添加关卡过渡效果或提示
	        System.out.println("进入第 " + currentLevel + " 关!");
	    } else {
	        // 通关所有关卡
	        gameState = GameState.GAME_OVER;
	        System.out.println("恭喜通关所有关卡!");
	    }
	}
	
	private void addLevelTransitionEffect() {
		 // 添加全屏爆炸效果
	    for (int i = 0; i < 20; i++) {
	        int x = random.nextInt(800);
	        int y = random.nextInt(600);
	        ElementObj explosion = new Explosion().createElement(x + "," + y);
	        em.addElement(explosion, GameElement.DIE);
	    }
	    
	    // 播放音效
	    SoundManager.getInstance().playSound("level_complete");
	}

	private void checkItemCollisions() {
        List<ElementObj> items = em.getElementsByKey(GameElement.ITEM);
        List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);
        
        for(ElementObj item : items) {
            for(ElementObj player : players) {
                if(item.pk(player)) {
                    int itemType = ((Item)item).getType();
                    switch(itemType) {
                        case 1: // 火力升级
                            ((Play)player).increasePower();
                            break;
                        case 2: // 生命恢复
                            ((Play)player).restoreLife();
                            break;
                        case 3: // 临时无敌
                            ((Play)player).startInvincible(3000);
                            break;
                        case 4: // 全屏清敌
                            clearAllEnemies();
                            break;
                        case 5: // 分数加倍
                            activateDoubleScore(10000); // 10秒双倍分数
                            break;
                        case 6: // 自动射击
                            ((Play)player).activateAutoFire(5000);
                            break;
                        case 7: // 终极武器
                            ((Play)player).activateSuperWeapon();
                            break;
                    }
                    item.setLive(false);
                    break;
                }
            }
        }
    }

	
	
	
	// 添加清敌方法
    private void clearAllEnemies() {
        // 清除普通敌机
        em.getElementsByKey(GameElement.ENEMY).clear();
        // 清除敌机子弹
        em.getElementsByKey(GameElement.ENEMYFILE).clear();
        // 如果Boss存在也清除
        if (!em.getElementsByKey(GameElement.BOSS).isEmpty()) {
            bossSpawned = false;
            em.getElementsByKey(GameElement.BOSS).clear();
        }
        
        // 添加爆炸特效
        for (int i = 0; i < 15; i++) {
            int x = random.nextInt(800);
            int y = random.nextInt(400) + 100;
            ElementObj explosion = new Explosion().createElement(x + "," + y);
            em.addElement(explosion, GameElement.DIE);
        }
    }

	private boolean checkGameState() {
        List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);
        if(players.isEmpty()) {
        	gameState = GameState.GAME_OVER;
            // 玩家死亡，游戏结束
            return true;
        }
        return false;
    }
	
	private void gameOver() {
		SoundManager.getInstance().stopBGM();
		SoundManager.getInstance().playSound("gameover");
		
		try {
			sleep(3000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		
		// 重置游戏状态
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

//	public void load() {
//		ImageIcon icon=new ImageIcon("image/tank/play1/player1_up.png");
//		ElementObj obj=new Play(100,100,50,50,icon);
//		em.addElement(obj,GameElement.PLAY);
////		ElementObj obj1=new Play(0,0,400,400,icon);
////		em.addElement(obj1,GameElement.MAPS );
////		ElementObj obj2=new Play(300,300,50,50,icon);
////		em.addElement(obj2,GameElement.BOSS );
//		
//		//添加一个敌人类，仿照玩家类编写，注意：不需要时间、键盘监听
//		//实现敌人的显示，同时实现最简单的移动
//		//实现子弹的发射和子弹移动，元素死亡
////		创建敌人
//		for(int i=0;i<10;i++) {
//			em.addElement(new Enemy().createElement(""), GameElement.ENEMY);
//		}
//		
//	}
	
	public void pauseGame() {
		if(gameState == GameState.RUNNING) {
			gameState = GameState.PAUSED;
			SoundManager.getInstance().pauseBGM();
		}
	}
	
	public void resumeGame() {
		if(gameState == GameState.PAUSED) {
			gameState = GameState.RUNNING;
			SoundManager.getInstance().resumeBGM();
		}
	}
	
	private GameMainJPanel gamePanel;

	public void setGameState(GameState state) {
		this.gameState = state;
		
	}
	
	
	
	public GameMainJPanel getPanel() {
		return gamePanel;
	}
	

}