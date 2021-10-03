package io.dankoller.codesharingplatform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CodeSnippet {

    private String code = "initial commit";
    // Format time stamp
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private String dateTime = LocalDateTime.now().format(formatter);

    // Getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime.format(formatter);
    }
}
