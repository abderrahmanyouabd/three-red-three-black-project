package com.a1st.threeredthreeblack;

import com.a1st.threeredthreeblack.view.PuzzleGameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PuzzleGameGUI extends Application {
    private final PuzzleGameView puzzleGameView = new PuzzleGameView(null);


    @Override
    public void start(Stage primaryStage) {

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
}
