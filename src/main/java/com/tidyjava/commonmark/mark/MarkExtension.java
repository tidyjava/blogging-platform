package com.tidyjava.commonmark.mark;

import com.tidyjava.commonmark.mark.internal.MarkDelimiterProcessor;
import com.tidyjava.commonmark.mark.internal.MarkNodeRenderer;
import org.commonmark.Extension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    private MarkExtension() {
    }

    public static Extension create() {
        return new MarkExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new MarkDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(MarkNodeRenderer::new);
    }
}
