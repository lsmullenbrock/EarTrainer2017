package EarTrainer;

/**
 * Most basic bread-and-butter StaffObject child class.
 * Used directly by Staff, but not by Score.
 * Note that all numerical constants are hand-tested to make it look OK when rendering,
 * they are not magic values whatsoever.
 */
public class Pitch extends StaffObject implements Renderable {
    private Note note;
    private Alteration alteration;
    private int octave;
    private final int midiNum;

    Pitch(int midiNum, int staffSlotNumber) {
        this.midiNum = midiNum;
        setStaffSlotNumber(staffSlotNumber);
        calculateFields();
    }

    @Override
    void calculateFields() {
        //Alterations are chosen by which I feel most comfortable reading on the staff
        switch (midiNum % 12) //the chromatic scale has 12 notes
        {
            case 0:
                this.note = Note.C;
                this.alteration = Alteration.NATURAL;
                break;
            case 1:
                this.note = Note.C;
                this.alteration = Alteration.SHARP;
                break;
            case 2:
                this.note = Note.D;
                this.alteration = Alteration.NATURAL;
                break;
            case 3:
                this.note = Note.E;
                this.alteration = Alteration.FLAT;
                break;
            case 4:
                this.note = Note.E;
                this.alteration = Alteration.NATURAL;
                break;
            case 5:
                this.note = Note.F;
                this.alteration = Alteration.NATURAL;
                break;
            case 6:
                this.note = Note.F;
                this.alteration = Alteration.SHARP;
                break;
            case 7:
                this.note = Note.G;
                this.alteration = Alteration.NATURAL;
                break;
            case 8:
                this.note = Note.A;
                this.alteration = Alteration.FLAT;
                break;
            case 9:
                this.note = Note.A;
                this.alteration = Alteration.NATURAL;
                break;
            case 10:
                this.note = Note.B;
                this.alteration = Alteration.FLAT;
                break;
            case 11:
                this.note = Note.B;
                this.alteration = Alteration.NATURAL;
                break;
            default:
                this.note = Note.C;
                this.alteration = Alteration.NATURAL;
                break;
        }
        this.octave = midiNum / 12 - 1; //Ensures midi number 60 == C4 / middle C

        setHeight(getScore().getSpacingY());
        setWidth(getScore().getSpacingX());
        setX(calculateXCoord());
        setY(calculateYCoord());
    }

    /**
     * The clef takes up the first chunk of the staff, so the first note needs to be drawn a significant distance
     * to the right of it, which is the initialOffset (x-axis)
     * @return Correct x-axis placement of this object
     */
    private double calculateXCoord() {
        final double initialOffset = Staff.NUM_STAFF_LINES * getScore().getSpacingX() * 1.25;
        return (getScore().getSpacingX() * 3 * getStaffSlotNumber()) + initialOffset; //3 is a hand-tested value
    }

    /**
     * Works off the fact that Middle C defined as the center of the Score.
     * Calculates y-position based on that.
     * @return Correct y-axis placement of this object.
     */
    private double calculateYCoord() {
        double result = getScore().getMiddleCPosition(); //where we measure from
        int multiplier = 4 - octave; //Trust me.

        //It Just Works(TM).
        result += multiplier * noteSpacing() * Note.values().length;
        result -= note.ordinal() * noteSpacing();
        result -= noteSpacing();

        return result;
    }

    @Override
    public void render() {
        drawNote();
        drawAlteration();
        drawLedgerLines();
    }

    private void drawNote() {
        getScore().getGraphicsContext2D().drawImage(
                note.getNoteImg(),
                getX(),
                getY(),
                getWidth(),
                getHeight()
        );
    }

    private void drawAlteration() {
        if (!alteration.equals(Alteration.NATURAL)) //implement natural case later?
        {
            getScore().getGraphicsContext2D().drawImage(
                    alteration.getImg(),
                    getX() - getWidth(),
                    getY() - getHeight() * .75,
                    getWidth(),
                    getHeight() * 2.5
            );
        }
    }

    private void drawLedgerLines() {

        if (note.equals(Note.C) && octave == 4) {
            drawLedgerLineMiddleC();
        } else if (midiNum >= 80) {
            drawLedgerLinesTrebleStaff();
        } else if (midiNum < 41) {
            drawLedgerLinesBassStaff();
        }
    }

    private void drawLedgerLineMiddleC() {
        getScore().getGraphicsContext2D().strokeLine(
                getX() - getWidth() * .25,
                getY() + noteSpacing(),
                getX() + getWidth() * 1.25,
                getY() + noteSpacing()
        );
    }

    private void drawLedgerLinesTrebleStaff() {
        double firstLineAboveTrebleStaff =
                getScore().getMiddleCPosition() - ((Staff.NUM_STAFF_LINES + 1) * getScore().getSpacingY());

        while(firstLineAboveTrebleStaff > getY()) {
            getScore().getGraphicsContext2D().strokeLine(
                    getX() - getWidth() * .25,
                    firstLineAboveTrebleStaff,
                    getX() + getWidth() * 1.25,
                    firstLineAboveTrebleStaff
            );
            firstLineAboveTrebleStaff -= getScore().getSpacingY();
        }
    }

    private void drawLedgerLinesBassStaff() {
        double firstLineBelowBassStaff =
                getScore().getMiddleCPosition() + ((Staff.NUM_STAFF_LINES + 1) * getScore().getSpacingY());

        while(firstLineBelowBassStaff <= getY() + noteSpacing()) {
            getScore().getGraphicsContext2D().strokeLine(
                    getX() - getWidth() * .25,
                    firstLineBelowBassStaff,
                    getX() + getWidth() * 1.25,
                    firstLineBelowBassStaff
            );
            firstLineBelowBassStaff += getScore().getSpacingY();
        }
    }

    /**
     * This is essentially half the distance between 2 staff lines, i.e., the space between two
     * vertically adjacent notes (e.g., the distance on the JavaFX Canvas between C and D in pixels)
     * @return vertical note spacing distance (one half the distance between Staff lines)
     */
    private double noteSpacing(){
        return getHeight() / 2;
    }

    /**
     * Will be used at some point to offset notes to avoid overlap when drawing to the Staff.
     * @param pitch pitch to be compared
     * @return truthy value
     */
    public boolean isAdjacent(Pitch pitch) {
        if(pitch.getX() == this.getX()) {
            if(Math.abs(pitch.getY() - this.getY()) <= getScore().getSpacingY()) {
                return true;
            }
        }
        return false;
    }

    public int getMidiNum() {
        return midiNum;
    }
}