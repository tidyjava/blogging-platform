package com.tidyjava.bp.sitemap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@Controller
public class SitemapController {

    @Autowired
    private SitemapGenerator sitemapGenerator;

    @RequestMapping(path = "/sitemap.xml", produces = APPLICATION_XML_VALUE)
    @ResponseBody
    public String sitemap() {
        return sitemapGenerator.generate();
    }
}
