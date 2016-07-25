package com.tidyjava.bp;

import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.html.HtmlRenderer;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Service
public class PostReader {

    @Value("${posts.location}")
    private String postsLocation;

    public List<PostSummary> summarize() {
        try {
            URL postsUrl = PostReader.class.getClassLoader().getResource(postsLocation);
            if (postsUrl == null) {
                return emptyList();
            }
            File postsDirectory = new File(postsUrl.toURI());
            return Arrays.stream(postsDirectory
                    .listFiles((dir, name) -> name.endsWith(".md")))
                    .map(this::parseFile)
                    .map(this::toMetadata)
                    .map(this::toSummary)
                    .collect(toList());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Node parseFile(File file) {
        Parser parser = Parser.builder().extensions(singletonList(YamlFrontMatterExtension.create())).build();
        try {
            return parser.parseReader(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, List<String>> toMetadata(Node document) {
        YamlFrontMatterVisitor visitor = new YamlFrontMatterVisitor();
        document.accept(visitor);
        return visitor.getData();
    }

    private PostSummary toSummary(Map<String, List<String>> metadata) {
        return new PostSummary(
                metadata.get("title").get(0),
                metadata.get("summary").get(0),
                metadata.get("url").get(0));
    }

    public Post readPost(String path) {
        URL postUrl = PostReader.class.getClassLoader().getResource(postsLocation + path + ".md");
        Node document;
        try {
            document = parseFile(new File(postUrl.toURI()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Map<String, List<String>> metadata = toMetadata(document);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return new Post(
                metadata.get("title").get(0),
                metadata.get("summary").get(0),
                renderer.render(document));
    }
}
