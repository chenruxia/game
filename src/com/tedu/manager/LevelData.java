package com.tedu.manager;

public class LevelData {
    private int level;
    private int enemyCount;       // 需要击败的敌机数量
    private int bossHp;          // Boss血量
    private int enemySpawnRate;  // 敌机生成频率(帧数)
    private String bgImage;      // 背景图片路径
    private int powerUpRate;     // 道具掉落概率
    private int enemyTypes;      // 可用敌机类型数量

    public LevelData(int level, int enemyCount, int bossHp, int enemySpawnRate, 
                   String bgImage, int powerUpRate, int enemyTypes) {
        this.level = level;
        this.enemyCount = enemyCount;
        this.bossHp = bossHp;
        this.enemySpawnRate = enemySpawnRate;
        this.bgImage = bgImage;
        this.powerUpRate = powerUpRate;
        this.enemyTypes = enemyTypes;
    }

    // Getters
    public int getLevel() { return level; }
    public int getEnemyCount() { return enemyCount; }
    public int getBossHp() { return bossHp; }
    public int getEnemySpawnRate() { return enemySpawnRate; }
    public String getBgImage() { return bgImage; }
    public int getPowerUpRate() { return powerUpRate; }
    public int getEnemyTypes() { return enemyTypes; }
}