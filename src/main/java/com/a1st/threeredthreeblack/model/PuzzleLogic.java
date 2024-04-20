package com.a1st.threeredthreeblack.model;

import lombok.Data;
import lombok.Getter;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */


@Data
public class PuzzleLogic {

    private final char[] boxes;


//    public char[] getBoxes() {
//        return this.boxes;
//    }

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
    public boolean movesStones(int stoneIndex1, int stoneIndex2, int emptyIndex1, int emptyIndex2) {
        // Check if the stone indices are adjacent
        if (Math.abs(stoneIndex1 - stoneIndex2) != 1) {
            return false;
        }

        // Check if the empty indices are adjacent
        if (Math.abs(emptyIndex1 - emptyIndex2) != 1) {
            return false;
        }

        // Ensure stones are moving towards the empty boxes and preserving order
        if ((stoneIndex1 < stoneIndex2 && emptyIndex1 < emptyIndex2) || (stoneIndex1 > stoneIndex2 && emptyIndex1 > emptyIndex2)) {
            // Move stones to the empty boxes while preserving order
            char stone1 = boxes[stoneIndex1];
            char stone2 = boxes[stoneIndex2];
            boxes[stoneIndex1] = '-';
            boxes[stoneIndex2] = '-';
            boxes[emptyIndex1] = stone1;
            boxes[emptyIndex2] = stone2;

            // Always return true after performing the move
            return true;
        } else {
            // Stones and empty boxes are not moving towards each other
            return false;
        }
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
