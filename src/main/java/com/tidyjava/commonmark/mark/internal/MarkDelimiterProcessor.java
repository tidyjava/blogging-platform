package com.tidyjava.commonmark.mark.internal;

import com.tidyjava.commonmark.mark.Mark;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.delimiter.DelimiterProcessor;
import org.commonmark.parser.delimiter.DelimiterRun;

public class MarkDelimiterProcessor implements DelimiterProcessor {

    @Override
    public char getOpeningCharacter() {
        return '=';
    }

    @Override
    public char getClosingCharacter() {
        return '=';
    }

    @Override
    public int getMinLength() {
        return 2;
    }

    @Override
    public int getDelimiterUse(DelimiterRun opener, DelimiterRun closer) {
        if (opener.length() >= 2 && closer.length() >= 2) {
            // Use exactly two delimiters even if we have more, and don't care about internal openers/closers.
            return 2;
        } else {
            return 0;
        }
    }

    @Override
    public void process(Text opener, Text closer, int delimiterCount) {
        // Wrap nodes between delimiters in marks.
        Node marks = new Mark();

        Node tmp = opener.getNext();
        while (tmp != null && tmp != closer) {
            Node next = tmp.getNext();
            marks.appendChild(tmp);
            tmp = next;
        }

        opener.insertAfter(marks);
    }
}
