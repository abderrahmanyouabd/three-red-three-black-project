package com.a1st.threeredthreeblack.view;

import com.a1st.threeredthreeblack.controller.GameResultStorage;
import com.a1st.threeredthreeblack.util.RestartGameEvent;
import com.a1st.threeredthreeblack.util.RestartGameListener;
import com.a1st.threeredthreeblack.model.GameResult;
import com.a1st.threeredthreeblack.model.PuzzleLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */


@AllArgsConstructor
public class PuzzleGameView {

    private PuzzleGame puzzleGame;
    private static int numMoves;
    private final GameResultStorage gameResultStorage = new GameResultStorage();
    private RestartGameListener restartGameListener;


    public VBox createMainLayout() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        return mainLayout;
    }

    public HBox createGameBoard() {
        HBox gameBoard = new HBox(10);
        gameBoard.setPadding(new Insets(10));
        return gameBoard;
    }

    public void updateGameBoard(HBox gameBoard, PuzzleGame puzzleGame) {
        // Clear the game board
        gameBoard.getChildren().clear();

        // Get the current state of the puzzle boxes
        char[] boxes = puzzleGame.puzzleLogic.getBoxes();

        // Create buttons for each box and add them to the game board
        Button[] buttons = new Button[16];
        for (int i = 0; i < boxes.length; i++) {
            Button button = new Button(String.valueOf(boxes[i]));
            final int index = i; // Store the current index
            buttons[i] = button; // Store button in array for easy access
            gameBoard.getChildren().add(button);

            // Add action listener to each button for selecting stones and empty boxes
            button.setOnAction(event -> {
                // Check if stone or empty box is clicked
                if (boxes[index] != '-') {
                    // If stone is clicked
                    if (puzzleGame.getFirstStoneIndex() == -1) {
                        puzzleGame.setFirstStoneIndex(index);
                    } else if (puzzleGame.getSecondStoneIndex() == -1 && puzzleGame.getFirstStoneIndex() != index) {
                        puzzleGame.setSecondStoneIndex(index);
                    }
                } else {
                    // If empty box is clicked
                    if (puzzleGame.getFirstEmptyIndex() == -1) {
                        puzzleGame.setFirstEmptyIndex(index);
                    } else if (puzzleGame.getSecondEmptyIndex() == -1 && puzzleGame.getFirstEmptyIndex() != index) {
                        puzzleGame.setSecondEmptyIndex(index);
                    }
                }

                // If all four indices are set, make the move
                if (puzzleGame.getFirstStoneIndex() != -1 && puzzleGame.getSecondStoneIndex() != -1 &&
                        puzzleGame.getFirstEmptyIndex() != -1 && puzzleGame.getSecondEmptyIndex() != -1) {
                    boolean moveSuccessful = puzzleGame.makeMove();
                    if (moveSuccessful) {
                        numMoves++;
                        puzzleGame.resetIndices();
                        // Move was successful, update the game board
                        updateGameBoard(gameBoard, puzzleGame);
                        // Check if the current arrangement is the target arrangement
                        if (puzzleGame.puzzleLogic.isTargetArrangement()) {
                            LocalDateTime endTime = LocalDateTime.now();
                            // Display a confirmation dialog with options to play again or cancel
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initStyle(StageStyle.UTILITY);
                            alert.setTitle("Congratulations!");
                            alert.setHeaderText(null);
                            alert.setContentText("You solved the puzzle! Would you like to play again?");

                            // Add "Play Again" button and "Cancel" button
                            ButtonType playAgainButton = new ButtonType("Play Again");
                            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                            alert.getButtonTypes().setAll(playAgainButton, cancelButton);
                            saveGameResult(puzzleGame.username, numMoves, puzzleGame.startTime, endTime, puzzleGame.puzzleLogic.isTargetArrangement());

                            // Show the confirmation dialog and handle the button actions
                            alert.showAndWait().ifPresent(buttonType -> {
                                if (buttonType == playAgainButton) {
                                    // Play again: Restart the game
                                    restartGame();
                                } else {
                                    // Cancel: Save the game result and display highest scores
                                    gameResultStorage.displayHighestScore();
                                    restartGame();
                                }
                            });
                        }
                    } else {
                        // Invalid move, reset indices
                        puzzleGame.resetIndices();
                    }
                }
            });
        }
    }

    public void setRestartGameListener(RestartGameListener listener) {
        this.restartGameListener = listener;
    }

    public void restartGame() {
        if (restartGameListener != null) {
            restartGameListener.onRestartGame(new RestartGameEvent(this));
        }
    }

    public VBox createInputLayout(HBox gameBoard) {
        VBox inputLayout = new VBox(5);
        TextField player1Field = new TextField();
        player1Field.setPromptText("Enter player name");
        Button startButton = new Button("Start Game");
        Button quitButton = new Button("Quit");
        quitButton.setVisible(false); // Initially hidden

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT); // Align buttons to the right
        buttonBox.getChildren().addAll(startButton, quitButton);

        startButton.setOnAction(event -> {
            String playerName = player1Field.getText();
            if (!playerName.isEmpty()) {
                puzzleGame = new PuzzleGame();
                puzzleGame.username = playerName;
                puzzleGame.initializeGame();
                puzzleGame.puzzleLogic = new PuzzleLogic();
                updateGameBoard(gameBoard, puzzleGame);
                quitButton.setVisible(true);
            }
        });

        quitButton.setOnAction(event -> {
            Alert confirmQuit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmQuit.setTitle("Confirm Quit");
            confirmQuit.setHeaderText(null);
            confirmQuit.setContentText("Are you sure you want to quit?");
            confirmQuit.initStyle(StageStyle.UTILITY);
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmQuit.getButtonTypes().setAll(yesButton, noButton);
            confirmQuit.showAndWait().ifPresent(buttonType -> {
                if (buttonType == yesButton) {
//                    saveGameResult(puzzleGame.username, numMoves);
                    saveGameResult(puzzleGame.username, numMoves, puzzleGame.startTime, LocalDateTime.now(), puzzleGame.puzzleLogic.isTargetArrangement());
                    System.exit(0);
                }
            });
        });

        inputLayout.getChildren().addAll(new Label("Player Name:"), player1Field, buttonBox);
        return inputLayout;
    }

    public VBox createHighScoreLayout() {
        VBox highScoreLayout = new VBox(10);
        highScoreLayout.getChildren().add(new Label("Top Scores History:"));
        List<GameResult> highScores = GameResultStorage.loadGameResults();
        for (GameResult gameResult : highScores) {
            LocalDateTime duration = gameResult.getEndTime().minusHours(gameResult.getStartTime().getHour()).minusMinutes(gameResult.getStartTime().getMinute()).minusSeconds(gameResult.getStartTime().getSecond()).minusNanos(gameResult.getStartTime().getNano());
            String durationStr = (
                    (duration.getHour() > 0 ? duration.getHour() + " hours, " : "") +
                            (duration.getMinute() > 0 ? duration.getMonth() + " minutes, " : "") +
                            (duration.getSecond() > 0 ? duration.getSecond() + " seconds, " : "") +
                            (duration.getNano()/1000000 > 0 ? duration.getNano()/1000000 + " milliseconds" : "")
            ).replaceAll(", $", ""); // Remove trailing comma and space

            highScoreLayout.getChildren().add(new Label(
                    String.format("%s: - %d moves - duration: %s",
                            gameResult.getUsername(),
                            gameResult.getNumMoves(),
                            durationStr)
            ));



        }
        return highScoreLayout;
    }

//    Todo: second version of saving (not that much of help).
//    private void saveGameResult(String playerName, int numMoves) {
//        // Create a GameResult object with the current time and solved status
//        LocalDateTime startTime = puzzleGame.startTime;
//        boolean isSolved = puzzleGame.puzzleLogic.isTargetArrangement();
//        GameResult gameResult = new GameResult(playerName, numMoves, startTime, endTime, isSolved);
//
//        // Save the game result
//        GameResultStorage.saveGameResult(gameResult);
//    }

    private void saveGameResult(String playerName, int numMoves, LocalDateTime start, LocalDateTime end, boolean solved) {
        GameResult gameResult = new GameResult(playerName, numMoves, start, end, solved);
        // Save the game result
        GameResultStorage.saveGameResult(gameResult);
    }
}
