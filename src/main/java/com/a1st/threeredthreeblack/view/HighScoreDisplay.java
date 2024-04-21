package com.a1st.threeredthreeblack.view;

import com.a1st.threeredthreeblack.controller.GameResultStorage;
import com.a1st.threeredthreeblack.model.GameResult;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class HighScoreDisplay {

    public static void displayHighScores() {
        List<GameResult> gameResults = GameResultStorage.loadGameResults();

        // Sort the game results by number of moves or time taken to solve the puzzle
        gameResults.sort((r1, r2) -> {
            if (r1.isSolved() && r2.isSolved()) {
                int compareMoves = Integer.compare(r1.getNumMoves(), r2.getNumMoves());
                if (compareMoves == 0) {
                    // If moves are equal, compare time spent
                    return Duration.between(r1.getStartTime(), r1.getEndTime())
                            .compareTo(Duration.between(r2.getStartTime(), r2.getEndTime()));
                } else {
                    return compareMoves;
                }
            } else if (r1.isSolved()) {
                return -1;
            } else if (r2.isSolved()) {
                return 1;
            } else {
                return 0;
            }
        });

        // Display the top 10 high scores
        System.out.println("High Scores:");
        for (int i = 0; i < Math.min(10, gameResults.size()); i++) {
            GameResult result = gameResults.get(i);
            LocalDateTime duration = result.getEndTime().minusHours(result.getStartTime().getHour()).minusMinutes(result.getStartTime().getMinute()).minusSeconds(result.getStartTime().getSecond()).minusNanos(result.getStartTime().getNano());
            System.out.printf("%d. %s - %d moves, %d seconds%n", i + 1, result.getUsername(), result.getNumMoves(), duration.getSecond());
        }
    }

}
