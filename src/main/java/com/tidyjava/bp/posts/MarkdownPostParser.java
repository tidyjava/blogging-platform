package com.tidyjava.bp.posts;

import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.html.HtmlRenderer;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

public class MarkdownPostParser {
    public static final String EXTENSION = ".md";

    private Node node;
    private Map<String, List<String>> metadata;

    public MarkdownPostParser(File file) {
        try {
            this.node = parseNode(file);
            this.metadata = parseMetadata(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Node parseNode(File file) throws IOException {
        Parser parser = Parser.builder().extensions(singletonList(YamlFrontMatterExtension.create())).build();
        return parser.parseReader(new FileReader(file));
    }

    private Map<String, List<String>> parseMetadata(Node document) {
        YamlFrontMatterVisitor visitor = new YamlFrontMatterVisitor();
        document.accept(visitor);
        return visitor.getData();
    }

    public String getTitle() {
        return metadata.get("title").get(0);
    }

    public String getSummary() {
        return metadata.get("summary").get(0);
    }

    public String getContent() {
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(node);
    }
}
