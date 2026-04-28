package com.example.startup;

public class Conversation {
    private final String name;
    private final String summary;
    private final String time;
    private final boolean unread;

    public Conversation(String name, String summary, String time, boolean unread) {
        this.name = name;
        this.summary = summary;
        this.time = time;
        this.unread = unread;
    }

    public String getName() { return name; }
    public String getSummary() { return summary; }
    public String getTime() { return time; }
    public boolean isUnread() { return unread; }
}
