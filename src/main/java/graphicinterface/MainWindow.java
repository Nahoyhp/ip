package graphicinterface;

import duke.Duke;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for graphicInterface.MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private boolean isFinished;

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));
    private Runnable closeWindowFunction;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    public void welcomeMessage() {
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(duke.getWelcome(), dukeImage));
    }

    public void setTerminateFunction(Runnable function) {
        this.closeWindowFunction = function;
    }

    public void setFinished() {
        this.isFinished = true;
    }

    @FXML
    public void showErrorMessage() {
        String errorMessage = "Unexpected Error Occurred";
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(errorMessage, dukeImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing duke.Duke's reply and then
     * appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.processInput(input, () -> this.setFinished());
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
        if (this.isFinished) {
            this.closeWindowFunction.run();
        }
    }
}