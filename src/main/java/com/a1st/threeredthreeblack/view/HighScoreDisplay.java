package com.a1st.threeredthreeblack.view;

import com.a1st.threeredthreeblack.controller.GameResultStorage;
import com.a1st.threeredthreeblack.model.GameResult;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */
public class HighScoreDisplay {

    public static void displayHighScores() {
        // Display the top 10 high scores
        System.out.println("Top Scores History:");
        List<GameResult> highScores = GameResultStorage.loadGameResults();
        for (GameResult result : highScores) {
            LocalDateTime duration = result.getEndTime().minusHours(result.getStartTime().getHour()).minusMinutes(result.getStartTime().getMinute()).minusSeconds(result.getStartTime().getSecond()).minusNanos(result.getStartTime().getNano());
            String durationStr = (
                    (duration.getHour() > 0 ? duration.getHour() + " hours, " : "") +
                            (duration.getMinute() > 0 ? duration.getMonth() + " minutes, " : "") +
                            (duration.getSecond() > 0 ? duration.getSecond() + " seconds, " : "") +
                            (duration.getNano()/1000000 > 0 ? duration.getNano()/1000000 + " milliseconds" : "")
            ).replaceAll(", $", ""); // Remove trailing comma and space


            System.out.printf("%s: - %d moves - duration: %s\n", result.getUsername(), result.getNumMoves(), durationStr);
        }
    }

}
