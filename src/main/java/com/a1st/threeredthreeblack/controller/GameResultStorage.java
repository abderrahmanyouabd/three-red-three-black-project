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

    public static void saveGameResult(GameResult gameResult) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO game_results (username, num_moves, start_time, end_time, solved) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, gameResult.getUserName());
                pstmt.setInt(2, gameResult.getNumMoves());
                pstmt.setTime(3, Time.valueOf(gameResult.getStartTime()));
                pstmt.setTime(4, Time.valueOf(gameResult.getEndTime()));
                pstmt.setBoolean(5, gameResult.isSolved());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<GameResult> loadGameResults() {
        List<GameResult> gameResults = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM game_results";
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
