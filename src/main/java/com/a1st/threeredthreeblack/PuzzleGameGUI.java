package com.a1st.threeredthreeblack;

import com.a1st.threeredthreeblack.util.RestartGameEvent;
import com.a1st.threeredthreeblack.util.RestartGameListener;
import com.a1st.threeredthreeblack.view.PuzzleGame;
import com.a1st.threeredthreeblack.view.PuzzleGameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */

public class PuzzleGameGUI extends Application implements RestartGameListener {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        PuzzleGame puzzleGame = new PuzzleGame();
        PuzzleGameView puzzleGameView = new PuzzleGameView(puzzleGame, this); // Pass PuzzleGame and RestartGameListener
        VBox mainLayout = puzzleGameView.createMainLayout();
        HBox gameBoard = puzzleGameView.createGameBoard();
        VBox inputLayout = puzzleGameView.createInputLayout(gameBoard);
        VBox highScoreLayout = puzzleGameView.createHighScoreLayout();

        mainLayout.getChildren().addAll(gameBoard, inputLayout, highScoreLayout);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Three Red Three Black Puzzle");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onRestartGame(RestartGameEvent event) {
        start(primaryStage); // I Use it to store primaryStage to restart the game
    }
}
