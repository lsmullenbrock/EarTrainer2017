package EarTrainer;

/**
 * Should this ever become more robust for notation editing this will obviate the need for this class
 * as it will allow things like dynamic markings and barlines to be added to a staff.
 * It also takes care of having multiple notes stacked on top of each other, i.e., having chords, and also
 * multiple markings and expressions stacked on top of each other, such as dynamics and articulations.
 */
abstract class StaffObject extends ScoreObject {
    private int staffSlotNumber;    //Determines where on the Staff X-axis to place object (i.e., each one has a "slot"
                                    //where it fits)

    public int getStaffSlotNumber() {
        return staffSlotNumber;
    }

    public void setStaffSlotNumber(int staffSlotNumber) {
        this.staffSlotNumber = staffSlotNumber;
    }
}
