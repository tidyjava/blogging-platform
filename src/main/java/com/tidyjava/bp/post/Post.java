package com.tidyjava.bp.post;

import java.time.LocalDate;
import java.util.List;

public class Post {
    static final String URL_PREFIX = "/";

    private String id;
    private String title;
    private String summary;
    private LocalDate date;
    private String url;
    private String content;
    private List<Tag> tags;
    private String author;

    Post(String id, String title, String summary, LocalDate date, String url, String content, List<Tag> tags, String author) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.date = date;
        this.url = url;
        this.content = content;
        this.tags = tags;
        this.author = author;
    }

    public String getId() {
        return id;
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

    public List<Tag> getTags() {
        return tags;
    }

    public boolean hasTag(Tag tag) {
        return tags.contains(tag);
    }

    public String getAuthor() {
        return author;
    }
}
