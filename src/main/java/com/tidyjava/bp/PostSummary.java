package com.tidyjava.bp;

public class PostSummary {
    private String title;
    private String summary;
    private String url;

    public PostSummary(String title, String summary, String url) {
        this.title = title;
        this.summary = summary;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
    }
}
