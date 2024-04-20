package com.a1st.threeredthreeblack.model;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */
public class PuzzleLogic {

    private char[] boxes;

    public PuzzleLogic() {
        boxes = new char[16];

        for(int i = 0;i<16;i++) {
            if(i >= 6) {
                boxes[i] = '-';
                continue;
            }
            if (i%2 == 0){
                boxes[i] = 'O';
            } else {
                boxes[i] = 'X';
            }
        }
    }

    public char[] getBoxes(){
        return boxes;
    }

    public boolean movesStones(int index1, int index2) {
        // todo: check if indices are adjacent.
        if(Math.abs(index1 - index2) != 1){
            return false;
        }

        // todo: Swapping the stones
        char temp = boxes[index1];
        boxes[index1] = boxes[index2];
        boxes[index2] = temp;

        // todo: check if arrangement is final goal.
        return isTargetArrangement();
    }


    public boolean isTargetArrangement(){
        for (int i = 0; i < 3; i++) {
            if(boxes[i] != 'O'){
                return false;
            }
        }
        for (int i = 3; i < 6; i++) {
            if(boxes[i] != 'X'){
                return false;
            }
        }

        for (int i = 6; i < 16; i++) {
            if(boxes[i] != '-'){
                return false;
            }
        }
        return true;
    }



}
