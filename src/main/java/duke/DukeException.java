package duke;

/**
 * Custom Exception for the project: duke.Duke
 */
public class DukeException extends Exception {
    public DukeException() {
        super();
    }


    public DukeException(String message) {
        super(message);
        assert !message.equals("") : "Empty Duke Exception Message";
    }
}
