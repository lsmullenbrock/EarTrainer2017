package EarTrainer;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Manager class for all exercises. Takes a Synthesizer and a Score.
 */
public class ExerciseManager {
    final private Score score;
    final private MidiChannel[] midiChannels;

    public ExerciseManager(Score score, Synthesizer synthesizer) {
        this.score = score;
        this.midiChannels = synthesizer.getChannels();
    }

    /**
     * Because of the current Score design, this was the easiest implementation of generated new exercises without
     * reworking both Score and Staff from the ground up.
     * Possibly refactor with lambdas to make it look pretty?
     */
    public void generateNewExercise(int exerciseLength, int numVoices) {
        score.reset();

        int nextMidiNum;
        int currentStaffNum;

        for(int currentVoiceNum = 0; currentVoiceNum < numVoices; currentVoiceNum++) {
            //Even numbers == treble staff, odd numbers == bass staff
            currentStaffNum = currentVoiceNum % 2;
            for(int currentStaffSlot = 0; currentStaffSlot < exerciseLength; currentStaffSlot++) {
                boolean uniqueNumFound = false;
                while(!uniqueNumFound) {
                    nextMidiNum = genRandomMidiNumPerVoice(currentVoiceNum);

                    if(!score.getStaves().get(currentStaffNum).containsAtSlot(nextMidiNum, currentStaffSlot)) {
                        score.getStaves().get(currentStaffNum).addPitch(new Pitch(nextMidiNum, currentStaffSlot));
                        uniqueNumFound = true;
                        }
                    }
                }
            }
        }

    /**
     * Even input number generate notes to fit treble staff, odd input numbers are for bass staff.
     * The reason for the space of 12 between the bound and the origin in the random functions are
     * to keep the generated values within an octave to make exercises more linear and less jagged.
     * @param voiceNum current voice input number
     * @return random midi number generated to fit on correct staff
     */
    private int genRandomMidiNumPerVoice(int voiceNum) {
        switch (voiceNum) {
            case 0:     //midiNums 60 to 72, treble staff
            case 2:
                return current().nextInt(60, 72 + 1);
            case 1:     //midiNums 48 to 60, bass staff
            case 3:
                return current().nextInt(48, 60 + 1);
            case 4:     //midiNums 72 to 84, treble staff
            case 6:
                return current().nextInt(72, 84 + 1);
            case 5:     //midiNums 36 to 48, bass staff
            case 7:
                return current().nextInt(36, 48 + 1);
            default:
                System.err.println("Value of " + voiceNum + " was fed into ExerciseManager.genRandomMidiNumPerVoice()");
                return -1;
        }
    }

    /**
     * Simply uses basic midi channels to flip on and off notes.
     * The strange for loops can eventually be replaced with lambdas (hopefully)
     * @param volume desired volume
     * @param tempo desired tempo in BPM
     */
    public void playExercise(int volume, int tempo) {

        final int msDuration = 60000 / tempo;

        ArrayList<Pitch> pitches = new ArrayList<>();

        //easier to iterate over when all pitches are in a single array
        for(Staff staff : score.getStaves()) {
            pitches.addAll(staff.getPitches());
        }

        for(int currentSlotNum = 0; currentSlotNum < score.getNumStaffSlots(); currentSlotNum++) {
            //note ON
            for (Pitch pitch : pitches) {
                if (pitch.getStaffSlotNumber() == currentSlotNum) {
                    midiChannels[0].noteOn(pitch.getMidiNum(), volume);
                }
            }
            //wait
            try {
                Thread.sleep(msDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //note OFF
            for(Pitch pitch : pitches) {
                if(pitch.getStaffSlotNumber() == currentSlotNum) {
                    midiChannels[0].noteOff(pitch.getMidiNum());
                }
            }
        }
    }

    /**
     * Displays generated exercise (if one has been generated)
     */
    public void revealAnswer() {
        score.update();
    }

    /**
     * Plays C4 (midi number 60) for reference.
     * @param volume input vol
     * @param delayTime time in milliseconds to play C4
     */
    public void playMiddleC(int volume, int delayTime) {
        Runnable runnable = () -> {
            this.midiChannels[0].noteOn(60, volume);
            try
            {
                Thread.sleep(delayTime);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            this.midiChannels[0].noteOff(60);
        };
        runnable.run();
    }
}