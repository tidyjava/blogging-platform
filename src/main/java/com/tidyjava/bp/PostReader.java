package com.tidyjava.bp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class PostReader {

    @Value("${posts.location}")
    private String postsLocation;

    @Autowired
    private PathMatchingResourcePatternResolver resourceResolver;

    public List<MarkdownPost> readAll() {
        try {
            return Stream.of(resourceResolver.getResources(postLocation("*")))
                    .map(MarkdownPost::new)
                    .sorted(Comparator.comparing(MarkdownPost::getDate).reversed())
                    .collect(toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MarkdownPost readOne(String path) {
        Resource resource = resourceResolver.getResource(postLocation(path));
        if (!resource.exists()) {
            throw new MissingPost();
        }
        return new MarkdownPost(resource);
    }

    private String postLocation(String path) {
        return postsLocation + path + MarkdownPost.EXTENSION;
    }

    public static class MissingPost extends RuntimeException {
    }
}
