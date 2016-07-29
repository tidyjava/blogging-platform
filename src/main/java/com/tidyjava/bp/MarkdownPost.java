package com.tidyjava.bp;

import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.html.HtmlRenderer;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

public class MarkdownPost {
    public static final String EXTENSION = ".md";

    private Node parsedResource;
    private Map<String, List<String>> metadata;
    private String url;

    public MarkdownPost(Resource resource) {
        try {
            this.parsedResource = parse(resource);
            this.metadata = extractMetadata(parsedResource);
            this.url = "/" + resource.getFilename().replace(EXTENSION, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Node parse(Resource resource) throws IOException {
        Parser parser = Parser.builder().extensions(singletonList(YamlFrontMatterExtension.create())).build();
        return parser.parseReader(new InputStreamReader(resource.getInputStream()));
    }

    private Map<String, List<String>> extractMetadata(Node document) {
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
        return HtmlRenderer.builder().build().render(parsedResource);
    }
}
