package EarTrainer;

/**
 * Images and text only.
 */
public interface ETResources
{
    /*
     * Images
     */
    String NOTE_IMAGE_LOC      = "file:images/note_head.png";
    String NATURAL_IMAGE_LOC   = "file:images/natural.png";
    String FLAT_IMAGE_LOC      = "file:images/flat.png";
    String SHARP_IMAGE_LOC     = "file:images/sharp.png";
    String TREBLE_CLEF_IMG_LOC = "file:images/treble_clef.png";
    String BASS_CLEF_IMG_LOC   = "file:images/bass_clef.png";
    String ICON_IMG_LOC        = "file:images/icon.png";

    /*
     * Text
     */
    String ABOUT_TEXT   =   "Created by Lee Mullenbrock. " +
                            "Check out the GitHub: " +
                            "https://github.com/lsmullenbrock/EarTrainer2017";

    String HOW_TO_USE_TEXT =
            "1. Select how many different voices total you want in your exercise with the " +
                "\"Number of Voices\" (i.e., how many notes to play at the same time).\n" +
            "2. Select how long you want the exercise to be using the \"Exercise Length\" slider at the bottom.\n" +
            "3. Select tempo, or how fast, to play the exercise in BPM (Beats per minute).\n" +
            "4. Click the \"New Exercise\" button to generate a new ear training exercise.\n" +
            "5. Click the \"Play Exercise\" button to play the exercise. Guess the notes as you hear them, " +
                "write them on staff paper, or try to play them on a piano. Hit \"Play Exercise\" as many " +
                "times as you need it, this isn't a race! Use the \"Play Middle C\" button for a reference " +
                "if you need it.\n" +
            "6. Click \"Reveal Answer\" to check if you're right.";
}
