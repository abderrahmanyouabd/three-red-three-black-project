package com.a1st.threeredthreeblack;

import com.a1st.threeredthreeblack.controller.GameResultStorage;
import com.a1st.threeredthreeblack.model.GameResult;
import com.a1st.threeredthreeblack.model.PuzzleLogic;
import com.a1st.threeredthreeblack.view.PuzzleGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalTime;
import java.util.List;

public class PuzzleGameGUI extends Application {

    private PuzzleGame puzzleGame;
    private PuzzleLogic puzzleLogic;
    private Stage primaryStage;
    private static int numMoves;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Create the main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));

        // Create the game board as an HBox
        HBox gameBoard = new HBox(10);
        gameBoard.setPadding(new Insets(10));

        // Add the game board to the main layout
        mainLayout.getChildren().add(gameBoard);

        // Create the input fields and buttons
        TextField player1Field = new TextField();
        player1Field.setPromptText("Enter player name");
        Button startButton = new Button("Start Game");
        startButton.setOnAction(event -> {
            String playerName = player1Field.getText();
            if (!playerName.isEmpty()) {
                puzzleGame = new PuzzleGame();
                puzzleGame.username = playerName;
                puzzleGame.initializeGame();
                updateGameBoard(gameBoard, puzzleGame);
            }
        });

        // Add the input fields and buttons to the main layout
        VBox inputLayout = new VBox(5);
        inputLayout.getChildren().addAll(new Label("Player Name:"), player1Field, startButton);
        mainLayout.getChildren().add(inputLayout);

        // Create the high score display
        VBox highScoreLayout = new VBox(10);
        highScoreLayout.getChildren().add(new Label("High Scores:"));
        List<GameResult> highScores = GameResultStorage.loadGameResults();
        for (GameResult gameResult : highScores) {
            highScoreLayout.getChildren().add(new Label(gameResult.getUserName() + " - " + gameResult.getNumMoves() + " moves"));
        }
        mainLayout.getChildren().add(highScoreLayout);

        // Create the scene and show the stage
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Three Red Three Black Puzzle");
        primaryStage.show();
    }

    private void updateGameBoard(HBox gameBoard, PuzzleGame puzzleGame) {
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

            // Add action listener to each button for swapping
            button.setOnAction(event -> {
                // If first box is clicked, set it as selected index
                if (puzzleGame.getSelectedIndex() == -1) {
                    puzzleGame.setSelectedIndex(index);
                } else {
                    // If second box is clicked, swap boxes if adjacent
                    int index1 = puzzleGame.getSelectedIndex();
                    int index2 = index;
                    if (index1 != index2 && isAdjacent(index1, index2)) {
                        char temp = boxes[index1];
                        boxes[index1] = boxes[index2];
                        boxes[index2] = temp;
                        numMoves++;
                        updateGameBoard(gameBoard, puzzleGame); // Update game board with swapped boxes
                        puzzleGame.setSelectedIndex(-1); // Reset selected index

                        // Check if the puzzle is solved
                        if (puzzleGame.puzzleLogic.isTargetArrangement()) {
                            // Display a pop-up message indicating the puzzle is solved
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.initStyle(StageStyle.UTILITY);
                            alert.setTitle("Congratulations!");
                            alert.setHeaderText(null);
                            alert.setContentText("You solved the puzzle! Would you like to play again?");

                            // Add "Play Again" button
                            ButtonType playAgainButton = new ButtonType("Play Again");
                            alert.getButtonTypes().setAll(playAgainButton, ButtonType.CANCEL);

                            alert.showAndWait().ifPresent(buttonType -> {
                                if (buttonType == playAgainButton) {
                                    saveGameResult(puzzleGame.username, numMoves);
                                    primaryStage.close(); // Close the current stage
                                    Stage newStage = new Stage(); // Create a new stage
                                    start(newStage); // Start the new game with the new stage

                                } else {
                                    saveGameResult(puzzleGame.username, numMoves);
                                    // Display the highest score
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
                                        highScoreAlert.setContentText("The highest score is: " + highestScore.getNumMoves() + " moves by " + highestScore.getUserName());
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

                            });
                        }
                    } else {
                        // If clicked boxes are not adjacent or same, clear selection
                        puzzleGame.setSelectedIndex(-1);
                    }
                }
            });
        }
    }

    // Helper method to save game result
// Helper method to save game result
    private void saveGameResult(String playerName, int numMoves) {
        // Create a GameResult object with the current time and solved status
        LocalTime startTime = puzzleGame.startTime;
        LocalTime endTime = LocalTime.now();
        boolean isSolved = puzzleGame.puzzleLogic.isTargetArrangement();
        GameResult gameResult = new GameResult(playerName, numMoves, startTime, endTime, isSolved);

        // Save the game result
        GameResultStorage.saveGameResult(gameResult);
    }


    // Helper method to check if two indices are adjacent
    private boolean isAdjacent(int index1, int index2) {
        return Math.abs(index1 - index2) == 1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
