package com.a1st.threeredthreeblack.controller;

import com.a1st.threeredthreeblack.model.GameResult;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */
public class GameResultStorage {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/game";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Raja19492000";

// todo:This one doesn't update when it's same username it created another one with same name but different id of course.

//    public static void saveGameResult(GameResult gameResult) {
//        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
//            String sql = "INSERT INTO game_results (username, num_moves, start_time, end_time, solved) VALUES (?, ?, ?, ?, ?)";
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setString(1, gameResult.getUserName());
//                pstmt.setInt(2, gameResult.getNumMoves());
//                pstmt.setTime(3, Time.valueOf(gameResult.getStartTime()));
//                pstmt.setTime(4, Time.valueOf(gameResult.getEndTime()));
//                pstmt.setBoolean(5, gameResult.isSolved());
//                pstmt.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static void saveGameResult(GameResult gameResult) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            // Check if a user with the same username already exists
            String checkUserSql = "SELECT * FROM game_results WHERE username = ?";
            try (PreparedStatement checkUserStmt = conn.prepareStatement(checkUserSql)) {
                checkUserStmt.setString(1, gameResult.getUsername());
                try (ResultSet rs = checkUserStmt.executeQuery()) {
                    if (rs.next()) {
                        // Update the existing record
                        String updateSql = "UPDATE game_results SET num_moves = ?, start_time = ?, end_time = ?, solved = ? WHERE username = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, gameResult.getNumMoves());
                            updateStmt.setTimestamp(2, Timestamp.valueOf(gameResult.getStartTime()));
                            updateStmt.setTimestamp(3, Timestamp.valueOf(gameResult.getEndTime()));
                            updateStmt.setBoolean(4, gameResult.isSolved());
                            updateStmt.setString(5, gameResult.getUsername());
                            updateStmt.executeUpdate();
                        }
                    } else {
                        // Insert a new record
                        String insertSql = "INSERT INTO game_results (username, num_moves, start_time, end_time, solved) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setString(1, gameResult.getUsername());
                            insertStmt.setInt(2, gameResult.getNumMoves());
                            insertStmt.setTimestamp(3, Timestamp.valueOf(gameResult.getStartTime()));
                            insertStmt.setTimestamp(4, Timestamp.valueOf(gameResult.getEndTime()));
                            insertStmt.setBoolean(5, gameResult.isSolved());
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayHighestScore() {
        List<GameResult> highScores = GameResultStorage.loadGameResults();
        if (!highScores.isEmpty()) {
            GameResult highestScore = highScores.get(0);
            for (GameResult result : highScores) {
                if (result.getNumMoves() < highestScore.getNumMoves()) {
                    highestScore = result;
                }
            }
            // Show a message with the highest score
            Alert highScoreAlert = new Alert(Alert.AlertType.INFORMATION);
            highScoreAlert.setTitle("Highest Score");
            highScoreAlert.setHeaderText(null);
            highScoreAlert.setContentText("The highest score is: " + highestScore.getNumMoves() + " moves by " + highestScore.getUsername());
            highScoreAlert.showAndWait();
        } else {
            // No high scores available
            Alert noHighScoreAlert = new Alert(Alert.AlertType.INFORMATION);
            noHighScoreAlert.setTitle("No High Scores");
            noHighScoreAlert.setHeaderText(null);
            noHighScoreAlert.setContentText("No high scores available yet.");
            noHighScoreAlert.showAndWait();
        }
    }


    public static List<GameResult> loadGameResults() {
        List<GameResult> gameResults = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            // Select game results ordered by number of moves and duration
            String sql = "SELECT * FROM game_results WHERE solved=true ORDER BY num_moves, TIMEDIFF(end_time, start_time) LIMIT 10";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String playerName = rs.getString("username");
                        int numMoves = rs.getInt("num_moves");
                        LocalDateTime startTime = rs.getTimestamp("start_time").toLocalDateTime();
                        LocalDateTime endTime = rs.getTimestamp("end_time").toLocalDateTime();
                        boolean solved = rs.getBoolean("solved");
                        GameResult gameResult = new GameResult(playerName, numMoves, startTime, endTime, solved);
                        gameResults.add(gameResult);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameResults;
    }

}
