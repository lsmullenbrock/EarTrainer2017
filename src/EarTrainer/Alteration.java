package EarTrainer;

import javafx.scene.image.Image;

/**
 * Enum to be used in conjunction with Note enum.
 * Will alter regular Note to correct number on
 * 12-tone clock.
 */
public enum Alteration {
    FLAT(-1), NATURAL(0), SHARP(1);

    Alteration(int alt) {
        this.alt = alt;
    }

    private int alt;

    private static Image sharpImg = new Image(ETResources.SHARP_IMAGE_LOC);
    private static Image flatImg = new Image(ETResources.FLAT_IMAGE_LOC);
    private static Image naturalImg = new Image(ETResources.NATURAL_IMAGE_LOC);

    public int getAlt() {
        return alt;
    }

    public Image getImg() {
        if (this.equals(FLAT)) {
            return flatImg;
        } else if (this.equals(SHARP)) {
            return sharpImg;
        } else {
            return naturalImg;
        }
    }
}
