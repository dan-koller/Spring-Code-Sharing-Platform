package io.dankoller.codesharingplatform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "code")
public class Code {

    // Basic snippets variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @Column
    @JsonIgnore
    private String uuid;

    @Column
    private String code = "Hello world";

    @Column
    private String date = "2021-01-01 12:00";

    // Values for time and view restrictions
    @Column
    private int time;

    @Column
    private int views;

    @Column
    @JsonIgnore
    private boolean restrictedByTime;

    @Column
    @JsonIgnore
    private boolean restrictedByViews;

    public boolean isRestrictedByTime() {
        return restrictedByTime;
    }

    public void setRestrictedByTime(boolean restrictedByTime) {
        this.restrictedByTime = restrictedByTime;
    }

    public boolean isRestrictedByViews() {
        return restrictedByViews;
    }

    public void setRestrictedByViews(boolean restrictedByViews) {
        this.restrictedByViews = restrictedByViews;
    }

    @Column
    @JsonIgnore
    private boolean enabled = true;

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getViews() {
        return views;
    }

    public boolean isEnabled() {
        return enabled;
    }

    // Update restrictions
    public void updateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime uploadDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        int duration = (int) Duration.between(uploadDate, now).toSeconds();

        // Check if view time has passed
        if (duration < time && views == 0) {
            enabled = false;
            time = 0;
        } else {
            int val = time - duration;

            if (val < 0) {
                time = 0;
            } else {
                time -= duration;
            }
        }
    }

    public void updateViews() {
        // Disable code if view limit is reached
        if (views < 1 && time == 0) {
            enabled = false;
            views = 0;
        } else {
            views--;
        }
    }

    // Getters and setters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setDate(LocalDateTime date) {
        this.date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public String getDate() {
        return this.date;
    }
}
