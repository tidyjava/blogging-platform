package com.tidyjava.bp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class PostReader {

    @Value("${posts.location}")
    private String postsLocation;

    public List<MarkdownPost> readAll() {
        URL postsRes = getResource(postsLocation);
        if (notExists(postsRes)) {
            return emptyList();
        }
        File postsDir = toFile(postsRes);
        return Stream.of(postsDir.listFiles(withSupportedExtension()))
                .map(MarkdownPost::new)
                .sorted(Comparator.comparing(MarkdownPost::getDate).reversed())
                .collect(toList());
    }

    private FilenameFilter withSupportedExtension() {
        return (dir, name) -> name.endsWith(MarkdownPost.EXTENSION);
    }

    public MarkdownPost readOne(String path) {
        URL post = getResource(postsLocation + path + MarkdownPost.EXTENSION);
        if (notExists(post)) {
            throw new MissingPost();
        }
        return new MarkdownPost(toFile(post));
    }

    private URL getResource(String postsLocation) {
        return PostReader.class.getClassLoader().getResource(postsLocation);
    }

    private boolean notExists(URL resource) {
        return resource == null;
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
