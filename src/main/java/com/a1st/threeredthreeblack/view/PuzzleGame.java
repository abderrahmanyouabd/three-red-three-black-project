package com.a1st.threeredthreeblack.view;

import com.a1st.threeredthreeblack.model.PuzzleLogic;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Scanner;




/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */
@Data
public class PuzzleGame {
    private final Scanner scanner = new Scanner(System.in);
    public PuzzleLogic puzzleLogic;
    public String username;
    public LocalDateTime startTime;

    private int firstStoneIndex;

    private int secondStoneIndex;

    private int firstEmptyIndex;

    private int secondEmptyIndex;

    public PuzzleGame() {
        puzzleLogic = new PuzzleLogic();
        username = null;
        startTime = null;
        firstStoneIndex = -1;
        secondStoneIndex = -1;
        firstEmptyIndex = -1;
        secondEmptyIndex = -1;
    }

    public void initializeGameForCMD() {
        System.out.println(puzzleLogic.getBoxes());
        System.out.println("Please enter your Username: ");
        username = scanner.nextLine();
        startTime = LocalDateTime.now();
    }

    public void initializeGame() {
        startTime = LocalDateTime.now();
    }

    public boolean makeMove() {
        // Get the selected indices
        int firstStoneIndex = getFirstStoneIndex();
        int secondStoneIndex = getSecondStoneIndex();
        int firstEmptyIndex = getFirstEmptyIndex();
        int secondEmptyIndex = getSecondEmptyIndex();

        // Check if the move is valid
        if (firstStoneIndex != -1 && secondStoneIndex != -1 && firstEmptyIndex != -1 && secondEmptyIndex != -1) {
            // Check if the stone indices are adjacent
            if (Math.abs(firstStoneIndex - secondStoneIndex) != 1) {
                return false;
            }

            // Check if the empty indices are adjacent
            if (Math.abs(firstEmptyIndex - secondEmptyIndex) != 1) {
                return false;
            }

            // Call the movesStones method in the PuzzleLogic class
            return puzzleLogic.movesStones(firstStoneIndex, secondStoneIndex, firstEmptyIndex, secondEmptyIndex);
        } else {
            // Reset the indices if they are not all set
            resetIndices();
            return false;
        }
    }

    public void resetIndices() {
        firstStoneIndex = -1;
        secondStoneIndex = -1;
        firstEmptyIndex = -1;
        secondEmptyIndex = -1;
    }

    public void displayStones() {
        System.out.println(puzzleLogic.getBoxes());
    }
}
