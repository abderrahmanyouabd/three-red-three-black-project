package com.a1st.threeredthreeblack.view;

import com.a1st.threeredthreeblack.model.PuzzleLogic;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */
public class PuzzleGame {
    private final Scanner scanner = new Scanner(System.in);
    public final PuzzleLogic puzzleLogic;
    public String username;
    public LocalTime startTime;
    private int selectedIndex;

    public PuzzleGame() {
        puzzleLogic = new PuzzleLogic();
        username = null;
        startTime = null;
        selectedIndex = -1;
    }

    public void initializeGameForCMD() {
        // todo: Display initial arrangement of the stones
        System.out.println(puzzleLogic.getBoxes());
        System.out.println("Please enter your Username: ");
        username = scanner.nextLine();
        startTime = LocalTime.now();
    }

    public void initializeGame() {
        // todo: Display initial arrangement of the stones
//        System.out.println(puzzleLogic.getBoxes());
//        System.out.println("Please enter your Username: ");
//        username = scanner.nextLine();
        startTime = LocalTime.now();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }


    public boolean makeMove(int index1, int index2) {
        return puzzleLogic.movesStones(index1, index2);
    }

    public void displayStones() {
//        // Display the initial arrangement of stones
//        System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
//        for (int i = 0; i < 16; i++) {
//            if (puzzleLogic.getBoxes()[i] == 'O') {
//                System.out.print("| O ");
//            } else if (puzzleLogic.getBoxes()[i] == 'X') {
//                System.out.print("| X ");
//            } else {
//                System.out.print("|   ");
//            }
//            if (i % 4 == 3) {
//                System.out.println("|");
//                System.out.println("+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
//            }
//        }
        System.out.println(puzzleLogic.getBoxes());
    }



}
