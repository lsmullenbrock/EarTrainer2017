package EarTrainer;

import java.util.ArrayList;

/**
 * For now can only hold Pitches and a single Clef.
 * Later implementations may be designed to hold other kinds of StaffObjects, e.g., dynamic markings and the like.
 */
public class Staff extends ScoreObject implements Renderable {
    public static final int NUM_STAFF_LINES = 5; //this will, hopefully, not change for the forseeable future.

    private ArrayList<Pitch> pitches;
    private Clef clef;

    Staff(Clef clef) {
        this.clef = clef;
        this.pitches = new ArrayList<>();
        calculateFields();
        render();
    }

    public void addPitch(Pitch pitch) {
        pitches.add(pitch);
    }

    @Override
    public void render() {
        drawStaffLines();
        drawClef();
        drawPitches();
    }

    private void drawStaffLines() {
        for (int i = 0; i < NUM_STAFF_LINES; i++) {
            getScore().getGraphicsContext2D().strokeLine(
                    getX(),
                    getY() + (i * getScore().getSpacingY()),
                    getX() + getWidth(),
                    getY() + (i * getScore().getSpacingY())
            );
        }
    }

    private void drawClef() {
        getScore().getGraphicsContext2D().drawImage(
                clef.getClefImg(),
                getX(),
                getY() - getScore().getSpacingY(),
                getScore().getSpacingY() * 3,
                getHeight() + getScore().getSpacingY() * 1.5
        );
    }

    private void drawPitches() {
        pitches.forEach(Pitch::render);
    }

    public boolean containsAtSlot(int midiNum, int staffSlot) {
        return pitches.stream()
                .anyMatch(pitch -> (pitch.getMidiNum() == midiNum && pitch.getStaffSlotNumber() == staffSlot));
    }

    @Override
    void calculateFields() {
        if (clef.equals(Clef.BASS)) {
            setY(getScore().getMiddleCPosition() + getScore().getSpacingY());
        } else {
            setY(getScore().getMiddleCPosition() - (NUM_STAFF_LINES * getScore().getSpacingY()));
        }
        setX(getScore().getPaddingX());
        setWidth(getScore().getWidth() - (getScore().getPaddingX() * 2));
        setHeight(getScore().getSpacingY() * NUM_STAFF_LINES);
    }

    public ArrayList<Pitch> getPitches() {
        return pitches;
    }
}
