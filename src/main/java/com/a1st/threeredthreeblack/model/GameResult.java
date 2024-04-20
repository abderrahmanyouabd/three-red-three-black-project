package com.a1st.threeredthreeblack.model;

import java.time.LocalTime;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */
public class GameResult {

    private String username;
    private int numMoves;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isSolved;


    public GameResult(String username, int numMoves, LocalTime startTime, LocalTime endTime, boolean isSolved) {
        this.username = username;
        this.numMoves = numMoves;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isSolved = isSolved;
    }

    public String getUserName() {
        return this.username;
    }

    public int getNumMoves() {
        return this.numMoves;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public boolean isSolved() {
        return this.isSolved;
    }

}
