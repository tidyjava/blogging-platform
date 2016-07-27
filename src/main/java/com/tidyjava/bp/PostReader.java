package com.tidyjava.bp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class PostReader {

    @Value("${posts.location}")
    private String postsLocation;

    public List<MarkdownPost> readAll() {
        URL postsUrl = PostReader.class.getClassLoader().getResource(postsLocation);
        if (postsUrl == null) {
            return emptyList();
        }
        File postsDirectory = toFile(postsUrl);
        return Arrays.stream(postsDirectory
                .listFiles((dir, name) -> name.endsWith(MarkdownPost.EXTENSION)))
                .map(MarkdownPost::new)
                .sorted(Comparator.comparing(MarkdownPost::getDate).reversed())
                .collect(toList());
    }

    public MarkdownPost readOne(String path) {
        URL postUrl = PostReader.class.getClassLoader().getResource(postsLocation + path + MarkdownPost.EXTENSION);
        if (postUrl == null) {
            throw new MissingPost();
        }
        return new MarkdownPost(toFile(postUrl));
    }

    private File toFile(URL url) {
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static class MissingPost extends RuntimeException {
    }
}
