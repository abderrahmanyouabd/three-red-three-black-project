package com.a1st.threeredthreeblack.controller;

import com.a1st.threeredthreeblack.model.GameResult;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
                checkUserStmt.setString(1, gameResult.getUserName());
                try (ResultSet rs = checkUserStmt.executeQuery()) {
                    if (rs.next()) {
                        // Update the existing record
                        String updateSql = "UPDATE game_results SET num_moves = ?, start_time = ?, end_time = ?, solved = ? WHERE username = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, gameResult.getNumMoves());
                            updateStmt.setTime(2, Time.valueOf(gameResult.getStartTime()));
                            updateStmt.setTime(3, Time.valueOf(gameResult.getEndTime()));
                            updateStmt.setBoolean(4, gameResult.isSolved());
                            updateStmt.setString(5, gameResult.getUserName());
                            updateStmt.executeUpdate();
                        }
                    } else {
                        // Insert a new record
                        String insertSql = "INSERT INTO game_results (username, num_moves, start_time, end_time, solved) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setString(1, gameResult.getUserName());
                            insertStmt.setInt(2, gameResult.getNumMoves());
                            insertStmt.setTime(3, Time.valueOf(gameResult.getStartTime()));
                            insertStmt.setTime(4, Time.valueOf(gameResult.getEndTime()));
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


    public static List<GameResult> loadGameResults() {
        List<GameResult> gameResults = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            // Select game results ordered by number of moves and duration
            String sql = "SELECT * FROM game_results ORDER BY num_moves, TIMEDIFF(end_time, start_time)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String playerName = rs.getString("username");
                        int numMoves = rs.getInt("num_moves");
                        LocalTime startTime = rs.getTime("start_time").toLocalTime();
                        LocalTime endTime = rs.getTime("end_time").toLocalTime();
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
