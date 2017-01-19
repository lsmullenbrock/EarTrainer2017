package EarTrainer;

import javafx.scene.image.Image;

/**
 * Basic clef enum. Other clefs may be added later with ease, but is not a necessity as of now.
 */
public enum Clef {
    TREBLE, BASS;

    private Image trebleClefImg = new Image(ETConst.TREBLE_CLEF_IMG_LOC);
    private Image bassClefImg = new Image(ETConst.BASS_CLEF_IMG_LOC);

    public Image getClefImg() {
        if (this.equals(TREBLE)) {
            return trebleClefImg;
        } else {
            return bassClefImg;
        }
    }
}
