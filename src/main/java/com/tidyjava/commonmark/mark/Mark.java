package com.tidyjava.commonmark.mark;

import org.commonmark.node.CustomNode;
import org.commonmark.node.Delimited;

public class Mark extends CustomNode implements Delimited {

    private static final String DELIMITER = "==";

    @Override
    public String getOpeningDelimiter() {
        return DELIMITER;
    }

    @Override
    public String getClosingDelimiter() {
        return DELIMITER;
    }
}
