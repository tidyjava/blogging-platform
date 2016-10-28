package com.tidyjava.bp.sitemap;

import com.tidyjava.bp.post.Post;
import com.tidyjava.bp.post.PostReader;
import com.tidyjava.bp.post.Tag;
import com.tidyjava.bp.util.DateUtils;
import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.WebPageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tidyjava.bp.util.DateUtils.toDate;

@Component
public class SitemapGenerator {
    private static final double MEDIUM_PRIORITY = 0.7;
    private static final double LOW_PRIORITY = 0.4;

    @Value("${blog.url}")
    private String blogUrl;

    @Autowired
    private PostReader postReader;

    String generate() {
        cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator sitemapGenerator = new cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator(blogUrl);
        List<Post> allPosts = postReader.readAll();
        sitemapGenerator.addPage(homePage(allPosts));
        sitemapGenerator.addPages(postPages(allPosts));
        sitemapGenerator.addPages(tagPages(allPosts));
        return sitemapGenerator.constructSitemapString();
    }

    private WebPage homePage(List<Post> posts) {
        return new WebPageBuilder()
                .name("/")
                .lastMod(lastModification(posts))
                .changeFreqDaily()
                .priorityMax()
                .build();
    }

    private List<WebPage> postPages(List<Post> posts) {
        return posts.stream()
                .map(this::toWebPage)
                .collect(Collectors.toList());
    }

    private WebPage toWebPage(Post post) {
        return new WebPageBuilder()
                .name(post.getUrl())
                .lastMod(toDate(post.getDate()))
                .changeFreqMonthly()
                .priority(MEDIUM_PRIORITY)
                .build();
    }

    private List<WebPage> tagPages(List<Post> posts) {
        Set<Tag> tags = getTags(posts);
        return tags.stream()
                .map(tag -> toWebPage(tag, posts))
                .collect(Collectors.toList());
    }

    private Set<Tag> getTags(List<Post> posts) {
        return posts.stream()
                .flatMap(post -> post.getTags().stream())
                .collect(Collectors.toSet());
    }

    private WebPage toWebPage(Tag tag, List<Post> posts) {
        List<Post> postsWithTag = findByTag(tag, posts);
        return new WebPageBuilder()
                .name(tag.getUrl())
                .lastMod(lastModification(postsWithTag))
                .changeFreqWeekly()
                .priority(LOW_PRIORITY)
                .build();
    }

    private List<Post> findByTag(Tag tag, List<Post> posts) {
        return posts.stream()
                .filter(post -> post.hasTag(tag))
                .collect(Collectors.toList());
    }

    private Date lastModification(List<Post> posts) {
        return posts.stream()
                .map(Post::getDate)
                .map(DateUtils::toDate)
                .max(Date::compareTo)
                .orElseThrow(IllegalStateException::new);
    }
}
