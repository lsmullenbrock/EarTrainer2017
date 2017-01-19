package EarTrainer;

import javafx.scene.image.Image;

/**
 * Note values are assigned by where they appear on the
 * 12-tone "clock." C is at 0, D is at 2, etc.
 * In order to get C#/Db (i.e., number 1 on the "clock")
 * an alteration is needed to change its value.
 * For example, D = 2. If we want D#(3), we go to
 * the Alteration enum and add a sharp, or "shift" of +1,
 * in order to attain the value 3 on the "clock."
 * If you don't understand what I mean by the 12-tone clock,
 * find a clock and write the corresponding Note letters
 * as listed below next to the numbers of the clock.
 * Or use Google.
 */
public enum Note {
    C(0), D(2), E(4), F(5), G(7), A(9), B(11);

    private int num;

    private static Image noteImg = new Image(ETConst.NOTE_IMAGE_LOC);

    Note(int num) {
        this.num = num;
    }

    public int getNoteNum() {
        return num;
    }

    public Image getNoteImg() {
        return noteImg;
    }
}
