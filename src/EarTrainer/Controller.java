package EarTrainer;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.net.URL;
import java.util.ResourceBundle;

import static javax.sound.midi.MidiSystem.getSynthesizer;

public class Controller implements Initializable {

    /*
     * fxml
     */
    public Slider volumeSlider;
    public Label volumeLabel;
    public Slider voicesSlider;
    public Label voicesLabel;
    public Slider exerciseLengthSlider;
    public Label exerciseLengthLabel;
    public Slider tempoSlider;
    public Label tempoLabel;

    public Button generateNewExerciseButton;
    public Button playExerciseButton;
    public Button revealAnswerButton;
    public Button playMidCButton;

    public MenuItem howToUseMenuItem;
    public MenuItem aboutMenuItem;

    public Canvas canvas;

    /*
     * non-fxml
     */
    private ExerciseManager exerciseManager;
    private Score score;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
         * JavaFX stuff
         */
        volumeLabel.setText(String.valueOf((int) volumeSlider.getValue()));
        voicesLabel.setText(String.valueOf((int) voicesSlider.getValue()));
        exerciseLengthLabel.setText(String.valueOf((int) exerciseLengthSlider.getValue()));
        tempoLabel.setText(String.valueOf((int) tempoSlider.getValue()));

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                volumeLabel.textProperty().setValue(String.valueOf((int) volumeSlider.getValue())));
        voicesSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                voicesLabel.textProperty().setValue(String.valueOf((int) voicesSlider.getValue())));
        tempoSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                tempoLabel.textProperty().setValue(String.valueOf((int) tempoSlider.getValue())));
        exerciseLengthSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                exerciseLengthLabel.textProperty().setValue(String.valueOf((int) exerciseLengthSlider.getValue())));

        /*
         * non FX stuff
         */
        score = new Score(canvas);
        score.reset();

        try {
            Synthesizer synthesizer = getSynthesizer();
            synthesizer.open();
            exerciseManager = new ExerciseManager(score, synthesizer);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void generateNewExerciseButtonClicked() {
        exerciseManager.generateNewExercise((int) exerciseLengthSlider.getValue(), (int) voicesSlider.getValue());
    }

    /**
     * When number of voices is high, playExerciseButton.disableProperty().setValue(false) happens
     * very late for some reason. Not sure why, no logical reason why this should be the case unless
     * it takes a very long time to close midi channels.
     */
    public void playExerciseButtonClicked() {

        if (score.getStaves().isEmpty() ||
                score.getStaves() == null ||
                score.getStaves().get(0).getPitches().size() == 0) {
            return;
        }


        final int msDuration = 60000 / (int) tempoSlider.getValue(); // BPM to ms

        Runnable playEx = () -> {
            playExerciseButton.disableProperty().setValue(true);
            exerciseManager.playExercise((int) volumeSlider.getValue(), (int) tempoSlider.getValue());

            try {
                Thread.sleep(msDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playExerciseButton.disableProperty().setValue(false);
        };
        new Thread(playEx).start();
    }

    public void revealAnswerButtonClicked() {
        exerciseManager.revealAnswer();
    }

    public void playMidCButtonClicked() {
        final int delayTime = 1000; //in ms
        Runnable disableDelay = () -> {
            playMidCButton.disableProperty().setValue(true);
            exerciseManager.playMiddleC((int) volumeSlider.getValue(), delayTime);
            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playMidCButton.disableProperty().setValue(false);
        };
        new Thread(disableDelay).start();
    }

    public void howToUseMenuItemClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to Use");
        alert.setHeaderText(null);
        alert.setContentText(ETResources.HOW_TO_USE_TEXT);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(ETResources.ICON_IMG_LOC));

        alert.showAndWait();
    }

    public void aboutMenuItemClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText(ETResources.ABOUT_TEXT);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(ETResources.ICON_IMG_LOC));

        alert.showAndWait();
    }
}
