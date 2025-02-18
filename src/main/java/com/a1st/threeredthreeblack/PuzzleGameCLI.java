package com.a1st.threeredthreeblack;

import com.a1st.threeredthreeblack.controller.GameResultStorage;
import com.a1st.threeredthreeblack.model.GameResult;
import com.a1st.threeredthreeblack.view.HighScoreDisplay;
import com.a1st.threeredthreeblack.view.PuzzleGame;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */
public class PuzzleGameCLI {

    private static final Scanner scanner = new Scanner(System.in);
    private static LocalDateTime endTime;
    public static void main(String[] args) {
        PuzzleGame game = new PuzzleGame();
        int  numMoves = 0;
        boolean solved = false;

        game.initializeGameForCMD();

        while (!solved) {
            System.out.println("Enter the indices of the two stones and 2 empty boxes to swap respectively (e.g., 0 1 3 4): ");
            System.out.println("To quit please enter letter (q)");
            String[] inputIndices = scanner.nextLine().split(" ");
            if(Arrays.stream(inputIndices).anyMatch(a -> Objects.equals(a, "q"))) {
                GameResult gameResult = new GameResult(game.username, numMoves, game.startTime, LocalDateTime.now(), false);
                GameResultStorage.saveGameResult(gameResult);
                HighScoreDisplay.displayHighScores();
                System.exit(0);
            }

            // Check if the user entered two indices
            if (inputIndices.length != 4) {
                System.out.println("Invalid input. Please enter 4 valid indices separated by a space.");
                continue; // Continue to the next iteration of the loop
            }

            // Parse the input indices
            int index1, index2, emptyIndex1, emptyIndex2;
            try {
                index1 = Integer.parseInt(inputIndices[0]);
                index2 = Integer.parseInt(inputIndices[1]);
                emptyIndex1 = Integer.parseInt(inputIndices[2]);
                emptyIndex2 = Integer.parseInt(inputIndices[3]);
                game.setFirstStoneIndex(index1);
                game.setSecondStoneIndex(index2);
                game.setFirstEmptyIndex(emptyIndex1);
                game.setSecondEmptyIndex(emptyIndex2);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid integer indices.");
                continue; // Continue to the next iteration of the loop
            }
            if (game.makeMove()) {
                numMoves++;
                game.displayStones();
                solved = game.puzzleLogic.isTargetArrangement();
                if(solved) {
                    endTime = LocalDateTime.now();
                }
            } else {
//                numMoves++;
                System.out.println("Invalid Indices Please enter valid integer indices between 0 & 15");
                System.out.println(game.puzzleLogic.getBoxes());
            }
        }

        GameResult gameResult = new GameResult(game.username, numMoves, game.startTime, endTime, true);
        GameResultStorage.saveGameResult(gameResult);

        System.out.println("Congratulations, " + game.username + "! You solved the puzzle in " + numMoves + " moves.");
        System.out.println("Do you want to play again? (y/n)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("y")) {
            main(args);
        } else {
            HighScoreDisplay.displayHighScores();
        }
    }
}
