package com.a1st.threeredthreeblack.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */

@Data
public class GameResult {

    private String username;
    private int numMoves;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isSolved;


    public GameResult(String username, int numMoves, LocalDateTime startTime, LocalDateTime endTime, boolean isSolved) {
        this.username = username;
        this.numMoves = numMoves;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isSolved = isSolved;
    }

    public boolean isSolved() {
        return this.isSolved;
    }

}
