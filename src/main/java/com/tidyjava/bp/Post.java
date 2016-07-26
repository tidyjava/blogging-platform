package com.tidyjava.bp;

public class Post {
    private String title;
    private String summary;
    private String content;

    public Post(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (!title.equals(post.title)) return false;
        if (!summary.equals(post.summary)) return false;
        return content.equals(post.content);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + summary.hashCode();
        result = 31 * result + content.hashCode();
        return result;
    }
}
