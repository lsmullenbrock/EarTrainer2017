package EarTrainer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Originally meant to inherent from JavaFX Canvas, however the way Canvas is initialized
 * by Java does not allow values to be calculated out of the gate (as Canvas size is set to 0 on initialize() in
 * Controller.java, for whatever convoluted reason). Thus, we pass a Canvas in as a variable.
 * Soon to find way around this annoyance.
 * (Note that the getWidth() and getHeight() methods are surrogates for when inheritance from Canvas can be figured out)
 */
public class Score {
    private final Canvas canvas;
    private final double paddingX; //no need for Y-padding because we draw with respect to the center of the canvas
    private final double spacingX, spacingY;

    private ArrayList<Staff> staves;

    public Score(Canvas canvas) {
        this.canvas = canvas;
        getGraphicsContext2D().setStroke(Color.BLACK);
        staves = new ArrayList<>();
        ScoreObject.setScore(this); //ABSOLUTELY MANDATORY

        double scalingVal = 15; //eventually this will scale with canvas size, but 15 is good for now
        paddingX = scalingVal;
        spacingX = scalingVal;
        spacingY = scalingVal;

        reset();
    }

    public void reset() {
        staves.clear();
        addGrandStaff();
        update();
    }

    public void update() {
        clear();
        draw();
    }

    public void draw() {
        staves.forEach(Staff::render);
    }

    public void clear() {
        getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
    }

    public void addGrandStaff() {
        staves.add(new Staff(Clef.TREBLE));
        staves.add(new Staff(Clef.BASS));
    }

    public ArrayList<Staff> getStaves() {
        return staves;
    }

    public int getNumStaffSlots() {
        return staves.get(0).getPitches().size();
    }

    public double getPaddingX() {
        return paddingX;
    }

    public double getSpacingX() {
        return spacingX;
    }

    public double getSpacingY() {
        return spacingY;
    }

    public double getMiddleCPosition() {
        return getHeight() / 2;
    }

    public double getWidth() {
        return canvas.getWidth();
    }

    public double getHeight() {
        return canvas.getHeight();
    }

    public GraphicsContext getGraphicsContext2D() {
        return canvas.getGraphicsContext2D();
    }
}
