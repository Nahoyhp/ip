package duke;

import duke.task.Task;

/**
 * Responsible for interpreting the input and interacting with the User.
 */
public class Duke {
    private final Ui ui;
    private TaskList tasks;
    private final Storage storage;
    private final Parser parser;

    /**
     * Initialised duke.Duke with a designated location to read and save the data.
     * @param filePath File location to read and save data.
     */
    public Duke(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.parser = new Parser();
        try {
            this.tasks = new TaskList(storage.loadFile());
        } catch (DukeException e) {
            ui.showError(e.getMessage());
            this.tasks = new TaskList();
        }
    }

    public String getWelcome() {
        return ui.showWelcome();
    }

    /**
     * Start the program.
     */
    public String processInput(String input) {
        int index;
        String returnMessage = "";
        try {
            String[] words = parser.splitIntoComponents(input);
            String command = words[0];
            switch (command) {
            //Common functions
            case "bye":
                storage.saveFile(tasks.toSaveFormat());
                returnMessage = ui.showBye();
                break;
            case "done":
                index = Integer.parseInt(words[1]);
                tasks.doTask(index);
                returnMessage = ui.showTaskDone(tasks.getTaskStatus(index));
                break;
            case "list":
                if (tasks.getTotalTask() == 0) {
                    returnMessage = ui.show("Currently, you have no tasks on hand");
                } else {
                    returnMessage = ui.showTasks(tasks.toString());
                }
                break;

            //3 different types of task
            case "event":
                try {
                    Task addedEvent = tasks.addEvent(words[1], words[2]);
                    returnMessage = ui.showTaskAdded(addedEvent.toString(), tasks.getTotalTask());
                } catch (IndexOutOfBoundsException err) {
                    returnMessage = ui.showError("Error: Please key in as: \n"
                            + "event [title] /at YYYY-MM-DD [startTime] [endTime]"
                            + "where start and end time is in HH:MM ");
                }
                break;
            case "todo":
                try {
                    Task addedToDo = tasks.addTodo(words[1]);
                    returnMessage = ui.showTaskAdded(addedToDo.toString(), tasks.getTotalTask());
                } catch (IndexOutOfBoundsException err) {
                    returnMessage = ui.showError("Error: Please key in as: \n "
                            + "event [title]");
                }
                break;
            case "deadline":
                try {
                    Task addedDeadline = tasks.addDeadLine(words[1], words[2]);
                    returnMessage = ui.showTaskAdded(addedDeadline.toString(), tasks.getTotalTask());
                } catch (IndexOutOfBoundsException err) {
                    returnMessage = ui.showError("Error: Please key in as: \n "
                            + "event [title] /by YYYY-MM-DD HH:MM");
                }
                break;

            //Delete Task
            case "delete":
                try {
                    index = Integer.parseInt(words[1]);
                    Task deletedTask = tasks.deleteTask(index);
                    returnMessage = ui.showDeletedTasks(deletedTask.toString());
                } catch (NumberFormatException err) {
                    //echo("Error. Please key in an integer after \"done\"");
                } catch (IndexOutOfBoundsException err) {
                    returnMessage = ui.showError("Key in \"delete [x]\" to delete x^th item");
                }
                break;

            //Find task by keyword
            case "find":
                String result = tasks.find(words[1]);
                if (result.equals("")) {
                    returnMessage = ui.show("No match found");
                } else {
                    returnMessage = ui.show("These following tasks match the keyword you entered: \n" + result);
                }
                break;

            //When command does not match any of those above
            default:
                returnMessage = ui.showError("OOPS!!! I don't know what does it mean by: \"" + input + "\"");
            }
        } catch (DukeException err) {
            returnMessage = ui.showError(err.getMessage());
        }
        assert !returnMessage.equals("") : "Error, none of the case catch the command";
        return returnMessage;
    }
}