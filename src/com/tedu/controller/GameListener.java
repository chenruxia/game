
package com.tedu.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.tedu.show.GameMainJPanel;
import com.tedu.controller.GameThread;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.element.ElementObj;

public class GameListener extends KeyAdapter {
    private GameThread gameThread;
    private GameMainJPanel panel;

    public GameListener(GameThread gameThread, GameMainJPanel panel) {
        this.gameThread = gameThread;
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 主菜单状态
        if (gameThread.getGameState() == GameThread.GameState.MENU) {
            // 排行榜界面
            if (panel.isInRank()) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    panel.setInRank(false); // ESC返回主菜单
                }
                return;
            }
            // 主菜单选项切换
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    panel.menuUp();
                    break;
                case KeyEvent.VK_DOWN:
                    panel.menuDown();
                    break;
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                    int idx = panel.getMenuIndex();
                    if (idx == 0) {
                        // 开始游戏，进入关卡选择
                        gameThread.setGameState(GameThread.GameState.LEVEL_SELECT);
                    } else if (idx == 1) {
                        // 排行榜
                        panel.setInRank(true);
                    } else if (idx == 2) {
                        // 退出
                        System.exit(0);
                    }
                    break;
            }
            return;
        }

        // 排行榜界面（其它状态下也能进排行榜时可加此判断）
        if (panel.isInRank()) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                panel.setInRank(false);
            }
            return;
        }

        // 关卡选择界面
        if (gameThread.getGameState() == GameThread.GameState.LEVEL_SELECT) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    panel.selectLevelUp();
                    break;
                case KeyEvent.VK_DOWN:
                    panel.selectLevelDown();
                    break;
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                    // 进入战机选择
                    gameThread.setGameState(GameThread.GameState.SHIP_SELECT);
                    break;
                case KeyEvent.VK_ESCAPE:
                    // 返回主菜单
                    gameThread.setGameState(GameThread.GameState.MENU);
                    break;
            }
            return;
        }

        // 战机选择界面
        if (gameThread.getGameState() == GameThread.GameState.SHIP_SELECT) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    panel.selectShipUp();
                    break;
                case KeyEvent.VK_DOWN:
                    panel.selectShipDown();
                    break;
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                    // 选定战机，开始游戏
                    // 你需要在GameThread中实现startGame方法，或根据实际逻辑调整
                    gameThread.startGame(panel.getSelectedLevel(), panel.getSelectedShipIndex());
                    break;
                case KeyEvent.VK_ESCAPE:
                    // 返回关卡选择
                    gameThread.setGameState(GameThread.GameState.LEVEL_SELECT);
                    break;
            }
            return;
        }

        // 游戏运行中
        if (gameThread.getGameState() == GameThread.GameState.RUNNING) {
            // 方向、射击、暂停等
            for (ElementObj obj : ElementManager.getManager().getElementsByKey(GameElement.PLAY)) {
                obj.keyClick(true, e.getKeyCode());
            }
            if (e.getKeyCode() == KeyEvent.VK_P) {
                gameThread.setGameState(GameThread.GameState.PAUSED);
            }
            return;
        }

        // 游戏暂停
        if (gameThread.getGameState() == GameThread.GameState.PAUSED) {
            if (e.getKeyCode() == KeyEvent.VK_P) {
                gameThread.setGameState(GameThread.GameState.RUNNING);
            }
            return;
        }

        // 游戏结束
        if (gameThread.getGameState() == GameThread.GameState.GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                gameThread.setGameState(GameThread.GameState.MENU);
            }
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 游戏运行中，处理方向键松开
        if (gameThread.getGameState() == GameThread.GameState.RUNNING) {
            for (ElementObj obj : ElementManager.getManager().getElementsByKey(GameElement.PLAY)) {
                obj.keyClick(false, e.getKeyCode());
            }
        }
    }
}
