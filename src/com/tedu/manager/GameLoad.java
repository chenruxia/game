package com.tedu.manager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;
import com.tedu.element.MapObj;
import com.tedu.element.Play;
import com.tedu.show.GameMainJPanel.ShipType;
//import com.tedu.element.Play;

/*
 * @说明 加载器（工具：用户读取配置文件的工具）工具类，大多提供的是static方法
 * */
public class GameLoad {
	private static ElementManager em = ElementManager.getManager();
	public static Map<String,ImageIcon> imgMap =new HashMap<>(); 
	
	
	//图片集合
//	public static Map<String,List<ImageIcon>> imgMaps;
//	static {
//		imgMap =new HashMap<>();
////		imgMap.put("left", new ImageIcon("image/tank/play1/player1_left.png"));
////		imgMap.put("down", new ImageIcon("image/tank/play1/player1_down.png"));
////		imgMap.put("right", new ImageIcon("image/tank/play1/player1_right.png"));
////		imgMap.put("up", new ImageIcon("image/tank/play1/player1_up.png"));
//	}
//	用于读取文件的类
	private static Properties pro = new Properties();
	
	/**
	 * 加载玩家战机图片（根据实际文件名）
	 */
	private static void loadPlayerImages() {
	    // 1. 标准战机（player_1 -> 1.gif）
	    imgMap.put("player_1", new ImageIcon("image/play/1.gif"));

	    // 2. 闪电战机（player_2 -> 2.png）
	    imgMap.put("player_2", new ImageIcon("image/play/2.png"));

	    // 3. 重装战机（player_3 -> 3.png）
	    imgMap.put("player_3", new ImageIcon("image/play/3.png"));

	    // 4. 均衡战机（player_4 -> 4.png）
	    imgMap.put("player_4", new ImageIcon("image/play/4.png"));

	    // 5. 幽灵战机（player_19 -> 19.gif）
	    imgMap.put("player_19", new ImageIcon("image/play/19.gif"));

	    // 检查图片是否加载成功
	    for (String key : imgMap.keySet()) {
	        if (key.startsWith("player_") && imgMap.get(key).getImage() == null) {
	            System.err.println("玩家战机图片加载失败: " + key);
	        }
	    }
	}
	
	public static void loadImg() {
	    // 动态加载背景图（10张）
	    for(int i=1; i<=10; i++) {
	        imgMap.put("bg"+i, new ImageIcon("image/background/background"+i+".png"));
	    }
	    loadPlayerImages();
//	 // 在GameLoad.loadImg()中添加：
//	    for(int i=1; i<=21; i++) {
//	        // 自动识别.gif或.png后缀
//	        String extension = (i == 1 || i == 19) ? ".gif" : ".png";
//	        String path = "image/play/" + i + extension;
//	        try {
//	        	ImageIcon icon = new ImageIcon(path);
//	        	if(icon.getImage() != null) {
//	        		imgMap.put("player_"+i, icon);
//	        	}
//	        }catch(Exception e) {
//	        	System.out.println("加载玩家图片失败："+path);
//	        }
//	    }
//	    
	    // 加载敌机（13张）
	    for(int i=1; i<=13; i++) {
	    	
	    		try {
	    			String path = "image/enemy/"+i+".png";
	    			ImageIcon icon = new ImageIcon(path);
	    			if(icon.getImage() == null) {
	    				System.out.println("敌机图片加载失败：enemy"+i+".png");
	    			}else {
	    				imgMap.put("enemy"+i, icon);
	    			}
	    		}catch(Exception e) {
	    			System.out.println("加载敌机图片异常："+e.getMessage());
	    		}
	    	
//	        imgMap.put("enemy"+i, new ImageIcon("image/enemy/enemy"+i+".png"));
	    }
	    
	    // 加载Boss（18张，分三个阶段）
	    for(int i=1; i<=18; i++) {
	        imgMap.put("boss_phase"+((i-1)/6+1)+"_"+i, 
	                  new ImageIcon("image/boss/boss"+i+".png"));
	    }
	    
	    // 加载子弹效果（前16张为子弹，后16张为爆炸）
	    for(int i=1; i<=32; i++) {
	       String path = "image/fire/"+i+".png";
	       try {
	    	   ImageIcon icon = new ImageIcon(path);
	    	   if(icon.getImage() != null) {
	    		   imgMap.put("bullet"+i, icon);
	    	   }
	       }catch(Exception e) {
	    	   System.out.println("子弹图片加载失败："+path);
	       }
	    }
	    
	    // 加载道具（7张）
	    for(int i=1; i<=7; i++) {
	        String path = "image/prop/"+i+".png";
	        imgMap.put("prop"+i, new ImageIcon(path));
	    }
	    
	    // 特殊爆炸效果
	    imgMap.put("big_explosion", new ImageIcon("image/bang1.png"));
	    
	    
//	 // 在 GameLoad.loadImg() 中添加：
//	    for (ShipType shipType : ShipType.values()) {
//	        String path = "image/play/" + shipType.getImageKey() + ".png";
//	        imgMap.put(shipType.getImageKey(), new ImageIcon(path));
//	    }
	}
    
    public static void loadPlay() {
        loadObj();
        String playStr = "400,550";
        Class<?> class1 = objMap.get("play");
        ElementObj obj = null;
        try {
            obj = (ElementObj)class1.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ElementObj play = obj.createElement(playStr);
        em.addElement(play, GameElement.PLAY);
    }
    
    public static void loadSounds() {
    	
    	
    	
    	
    	 SoundManager soundManager = SoundManager.getInstance();
    	 
    	 
    	 soundManager.setBGMVolume(0.8f);
    	 soundManager.setSFXVolume(0.7f);
    	    
    	    // 调试输出，验证资源路径
    	    System.out.println("当前工作目录: " + System.getProperty("user.dir"));
    	    printResourcePath("/sound/music/shoot.wav");
    	    printResourcePath("/sound/music/explosion.wav");
    	    
    	    // 使用ClassLoader获取资源
    	    soundManager.loadSound("shoot", "/sound/music/shoot.wav");
    	    soundManager.loadSound("explosion", "/sound/music/explosion.wav");
    	    soundManager.loadSound("powerup", "/sound/music/powerup.wav");
    	    soundManager.loadSound("gameover", "/sound/music/gameOver.wav");
    	    soundManager.playBGM("/sound/music/background.wav");  // 正确路径
    	    
    	    
//    	    
//    	 // 在GameLoad.loadSounds()方法末尾添加
//    	    soundManager.debugSoundStatus();
//
//    	    // 测试播放所有音效
//    	    soundManager.playSound("shoot");
//    	    soundManager.playSound("explosion");
//    	    soundManager.playSound("powerup");
//    	    soundManager.playSound("gameover");
    }
    
    
    private static void printResourcePath(String path) {
        URL url = GameLoad.class.getResource("/" + path);
        System.out.println("查找资源: " + path + " => " + (url != null ? "找到" : "未找到"));
        if (url != null) {
            System.out.println("资源路径: " + url.getPath());
        }
    }

	private static Map<String, Class<?>> objMap = new HashMap<>();
    public static void loadObj() {
        objMap.put("play", Play.class);
    }
	
}