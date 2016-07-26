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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostSummary that = (PostSummary) o;

        if (!title.equals(that.title)) return false;
        if (!summary.equals(that.summary)) return false;
        return url.equals(that.url);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + summary.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }
}
