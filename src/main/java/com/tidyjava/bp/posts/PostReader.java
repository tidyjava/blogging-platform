package com.tidyjava.bp.posts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class PostReader {

    @Value("${posts.location}")
    private String postsLocation;

    public List<Post> summarize() {
        URL postsUrl = PostReader.class.getClassLoader().getResource(postsLocation);
        if (postsUrl == null) {
            return emptyList();
        }
        File postsDirectory = toFile(postsUrl);
        return Arrays.stream(postsDirectory
                .listFiles((dir, name) -> name.endsWith(MarkdownPostParser.EXTENSION)))
                .map(this::toPost)
                .collect(toList());
    }

    public Post readPost(String path) {
        URL postUrl = PostReader.class.getClassLoader().getResource(postsLocation + path + MarkdownPostParser.EXTENSION);
        if (postUrl == null) {
            throw new MissingPost();
        }
        return toPost(toFile(postUrl));
    }

    private File toFile(URL url) {
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Post toPost(File file) {
        MarkdownPostParser parser = new MarkdownPostParser(file);
        return new Post(parser.getTitle(), parser.getSummary(), "/" + file.getName().replace(MarkdownPostParser.EXTENSION, ""), parser.getContent());
    }

    public static class MissingPost extends RuntimeException {
    }
}
