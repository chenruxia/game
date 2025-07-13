package com.tedu.manager;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static SoundManager instance;
    private Map<String, Clip> sounds = new HashMap<>();
    private Clip bgm;
    private FloatControl bgmVolumeControl;
    private float bgmVolume = 0.7f;
    private float sfxVolume = 0.7f;

    private SoundManager() {
        // 私有构造函数
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void loadSound(String key, String path) {
    	 try {
             System.out.println("尝试加载音效: " + path);
             URL url = getClass().getResource(path);
             if(url == null) {
                 System.out.println("音效文件未找到: " + path);
                 return;
             }
             
             // 添加详细的音频格式调试信息
             AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
             AudioFormat format = audioIn.getFormat();
             System.out.println("音频格式: " + format);
             
             // 确保Clip可以播放这种格式
             DataLine.Info info = new DataLine.Info(Clip.class, format);
             if (!AudioSystem.isLineSupported(info)) {
                 System.err.println("不支持的音频格式: " + format);
                 return;
             }
             
             Clip clip = (Clip) AudioSystem.getLine(info);
             clip.open(audioIn);
             
             // 预加载音频数据
             clip.addLineListener(event -> {
                 if (event.getType() == LineEvent.Type.STOP) {
                     clip.setFramePosition(0); // 播放完成后重置位置
                 }
             });
             
             
             
             sounds.put(key, clip);
             System.out.println("成功加载音效: " + key);
         } catch (Exception e) {
             System.err.println("加载音效失败: " + path);
             e.printStackTrace();
         }
    }

    public void playSound(String key) {
    	Clip clip = sounds.get(key);
        if (clip != null) {
            try {
                // 确保Clip处于正确状态
                if (!clip.isOpen()) {
                    clip.open();
                }
                
                // 停止当前播放并重置
                clip.stop();
                clip.setFramePosition(0);
                
                // 设置音量
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float range = volumeControl.getMaximum() - volumeControl.getMinimum();
                    float gain = (range * sfxVolume) + volumeControl.getMinimum();
                    volumeControl.setValue(gain);
                }
                
                // 开始播放
                clip.start();
                System.out.println("成功播放音效: " + key);
                
            } catch (LineUnavailableException e) {
                System.err.println("音效播放失败(线路不可用): " + key);
                e.printStackTrace();
            }
        } else {
            System.err.println("音效未加载: " + key);
        }
    }

    public void playBGM(String path) {
        try {
//            stopBGM();
            URL url = getClass().getResource(path);
            if (url == null) {
                System.err.println("背景音乐文件未找到：" + path);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            bgm = AudioSystem.getClip();
            bgm.open(audioIn);

            // 必须在打开 Clip 后才能获取音量控制
            if (bgm.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                bgmVolumeControl = (FloatControl) bgm.getControl(FloatControl.Type.MASTER_GAIN);
                setBGMVolume(bgmVolume); // 应用初始音量
                System.out.println("BGM音量控制已启用，当前音量: " + bgmVolume);
            } else {
                System.err.println("警告：当前系统不支持BGM音量控制");
            }

            bgm.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("背景音乐已启动：" + path);
        } catch (Exception e) {
            System.err.println("播放背景音乐失败：" + e.getMessage());
            e.printStackTrace();
        }
        
     // 测试背景音乐是否真的播放
        if (bgm != null) {
            System.out.println("BGM状态: " + (bgm.isRunning() ? "播放中" : "已停止"));
            System.out.println("BGM帧位置: " + bgm.getFramePosition() + "/" + bgm.getFrameLength());
            System.out.println("BGM音量值: " + (bgmVolumeControl != null ? bgmVolumeControl.getValue() : "N/A"));
        }
    }

    public void setBGMVolume(float volume) {
		this.bgmVolume = volume;
		if(bgmVolumeControl != null) {
			float range = bgmVolumeControl.getMaximum()-bgmVolumeControl.getMinimum();
			float gain = (range * volume)+bgmVolumeControl.getMinimum();
			bgmVolumeControl.setValue(gain);
		}
	}
    
    public void setSFXVolume(float volume) {
    	this.sfxVolume = volume;
    }

	public void stopBGM() {
        if (bgm != null && bgm.isRunning()) {
            bgm.stop();
        }
    }

	public void pauseBGM() {
		if(bgm != null && bgm.isRunning()) {
			bgm.stop();
		}
		
	}
	
	public void resumeBGM() {
		if(bgm != null && !bgm.isRunning()) {
			bgm.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void debugSoundStatus() {
	    System.out.println("===== 音效状态 =====");
	    System.out.println("已加载音效数量: " + sounds.size());
	    
	    sounds.forEach((key, clip) -> {
	        System.out.println("音效: " + key);
	        System.out.println("  状态: " + (clip.isOpen() ? "已打开" : "未打开"));
	        System.out.println("  帧长度: " + clip.getFrameLength());
	        System.out.println("  当前帧: " + clip.getLongFramePosition());
	    });
	    
	    if (bgm != null) {
	        System.out.println("背景音乐状态: " + (bgm.isRunning() ? "播放中" : "停止"));
	    }
	}
	
}