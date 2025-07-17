package com.tedu.manager;

import java.io.*;
import java.util.*;

public class AchievementsManager {
    private static final String FILE = "results/score.txt";

    // 保存分数
    public static void saveScore(int score) {
        File file = new File("results/score.txt");
        file.getParentFile().mkdirs();
        try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
            out.println(score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取前N名分数
    public static List<Integer> getTopScores(int n) {
        List<Integer> scores = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = in.readLine()) != null) {
                try {
                    scores.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException ignore) {}
            }
        } catch (Exception e) {
            // 文件不存在时忽略
        }
        scores.sort(Comparator.reverseOrder());
        if (scores.size() > n) {
            return scores.subList(0, n);
        }
        return scores;
    }
}