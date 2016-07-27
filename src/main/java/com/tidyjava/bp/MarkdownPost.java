package com.tidyjava.bp;

import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.html.HtmlRenderer;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

public class MarkdownPost {
    public static final String EXTENSION = ".md";

    private Node node;
    private Map<String, List<String>> metadata;
    private String url;

    public MarkdownPost(File file) {
        try {
            this.node = parseNode(file);
            this.metadata = parseMetadata(node);
            this.url = "/" + file.getName().replace(EXTENSION, "");
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

    public LocalDate getDate() {
        return LocalDate.parse(metadata.get("date").get(0), DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(node);
    }
}
