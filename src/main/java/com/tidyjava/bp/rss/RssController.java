package com.tidyjava.bp.rss;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@Controller
public class RssController {

    @Autowired
    private RssFeedGenerator rssFeedGenerator;

    @RequestMapping(path = "/rss", produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public String rss() throws Exception {
        SyndFeed feed = rssFeedGenerator.generate();
        return new SyndFeedOutput().outputString(feed);
    }
}
