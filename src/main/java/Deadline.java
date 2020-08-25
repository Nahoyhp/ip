package main.java;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private LocalTime time;

    public Deadline(String name, String eventDetail) throws DukeException {
        super(name);
        String[] input = eventDetail.split("\\s+");
        try {
            super.setDate(LocalDate.parse(input[0]));
            if (input.length == 2) {
                time = LocalTime.parse(input[1]);
            } else{
                time = null;
            }
        } catch (DateTimeParseException err) {
            throw new DukeException("Error: Please key in as: \n " +
                    "event [title] /by YYYY-MM-DD HH:MM");
        }
    }

    @Override
    public String toSaveFormat() {
        return String.format("D%s | %s %s", super.toSaveFormat(),
                Task.SAVE_DATE_FORMATTER.format(super.date.get()), Task.TIME_FORMATTER.format(time));
    }

    @Override
    public String toString() {
        String dateTime = Task.DATE_FORMATTER.format(super.date.get());
        if (time != null) {
            dateTime += " " + Task.TIME_FORMATTER.format(this.time);
        }
        return String.format("[D]%s (by: %s)", super.toString(), dateTime);
    }
}
