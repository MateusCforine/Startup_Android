package com.example.startup;

public class NotificationItem {
    private final String text;
    private final String time;

    public NotificationItem(String text, String time) {
        this.text = text;
        this.time = time;
    }

    public String getText() { return text; }
    public String getTime() { return time; }
}
