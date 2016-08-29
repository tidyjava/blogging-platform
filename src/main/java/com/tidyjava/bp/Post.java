package com.tidyjava.bp;

import java.time.LocalDate;

public class Post {
    private String title;
    private String summary;
    private LocalDate date;
    private String url;
    private String content;

    public Post(String title, String summary, LocalDate date, String url, String content) {
        this.title = title;
        this.summary = summary;
        this.date = date;
        this.url = url;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }
}
