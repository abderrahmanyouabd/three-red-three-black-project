//package com.a1st.threeredthreeblack.controller;
//
//import com.a1st.threeredthreeblack.model.GameResult;
//import com.a1st.threeredthreeblack.view.HighScoreDisplay;
//import com.a1st.threeredthreeblack.view.PuzzleGame;
//
//import java.time.LocalTime;
//import java.util.Scanner;
//
///**
// * @author: Abderrahman Youabd aka: A1ST
// * @version: 1.0
// */
//public class PuzzleGameMain {
//
//    private static final Scanner scanner = new Scanner(System.in);
//    public static void main(String[] args) {
//        PuzzleGame game = new PuzzleGame();
//        int  numMoves = 0;
//        boolean solved = false;
//
//        game.initializeGameForCMD();
//
//        while (!solved) {
//            System.out.print("Enter the indices of the two stones to swap (e.g., 0 1): ");
//            String[] inputIndices = scanner.nextLine().split(" ");
//
//            // Check if the user entered two indices
//            if (inputIndices.length != 2) {
//                System.out.println("Invalid input. Please enter two indices separated by a space.");
//                continue; // Continue to the next iteration of the loop
//            }
//
//            // Parse the input indices
//            int index1, index2;
//            try {
//                index1 = Integer.parseInt(inputIndices[0]);
//                index2 = Integer.parseInt(inputIndices[1]);
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input. Please enter valid integer indices.");
//                continue; // Continue to the next iteration of the loop
//            }
//
//            if (game.makeMove(index1, index2)) {
//                numMoves++;
//                game.displayStones();
//                solved = game.puzzleLogic.isTargetArrangement();
//            } else {
//                numMoves++;
//                System.out.println(game.puzzleLogic.getBoxes());
//            }
//        }
//
//
//        LocalTime endTime = LocalTime.now();
//        GameResult gameResult = new GameResult(game.username, numMoves, game.startTime, endTime, true);
//        GameResultStorage.saveGameResult(gameResult);
//
//        System.out.println("Congratulations, " + game.username + "! You solved the puzzle in " + numMoves + " moves.");
//        System.out.println("Do you want to play again? (y/n)");
//        String response = scanner.nextLine();
//        if (response.equalsIgnoreCase("y")) {
//            main(args);
//        } else {
//            HighScoreDisplay.displayHighScores();
//        }
//    }
//}
