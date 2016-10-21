package com.tidyjava.bp.rss;

import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.synd.*;
import com.tidyjava.bp.post.Post;
import com.tidyjava.bp.post.PostReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.ZoneOffset.UTC;
import static java.util.Arrays.asList;

@Component
public class RssFeedGenerator {
    private static final int DEFAULT_FEED_LENGTH = 5;

    @Value("${blog.name}")
    private String blogName;
    @Value("${blog.url}")
    private String blogUrl;

    @Autowired
    private PostReader postReader;

    SyndFeed generate() {
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setLink(blogUrl);
        feed.setTitle(blogName);
        feed.setDescription("");
        feed.setEntries(buildFeedEntries());
        return feed;
    }

    private List<SyndEntry> buildFeedEntries() {
        return postReader.readLast(DEFAULT_FEED_LENGTH)
                .stream()
                .map(this::toEntry)
                .collect(Collectors.toList());
    }

    private SyndEntry toEntry(Post post) {
        SyndEntry entry = new SyndEntryImpl();
        entry.setTitle(post.getTitle());
        entry.setLink(blogUrl + post.getUrl());
        entry.setAuthor(post.getAuthor());
        entry.setPublishedDate(toDate(post.getDate()));
        entry.setCategories(toCategories(post.getTags()));
        entry.setDescription(toContent(post.getSummary()));
        entry.setContents(asList(toContent(post.getContent())));
        return entry;
    }

    private List<SyndCategory> toCategories(List<String> tags) {
        return tags.stream()
                .map(this::toCategory)
                .collect(Collectors.toList());
    }

    private SyndCategory toCategory(String tag) {
        SyndCategory category = new SyndCategoryImpl();
        category.setName(tag);
        return category;
    }

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atOffset(UTC).toInstant());
    }

    private SyndContent toContent(String postContent) {
        SyndContent content = new SyndContentImpl();
        content.setValue(postContent);
        content.setType(Content.HTML);
        return content;
    }
}
