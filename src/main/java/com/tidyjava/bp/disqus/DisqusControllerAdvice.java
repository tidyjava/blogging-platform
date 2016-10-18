package com.tidyjava.bp.disqus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class DisqusControllerAdvice {

    @Value("${blog.url}")
    private String blogUrl;

    @Value("${disqus.enabled}")
    private boolean disqusEnabled;

    @Value("${disqus.name}")
    private String disqusName;

    @ModelAttribute("blogUrl")
    public String getBlogUrl() {
        return blogUrl;
    }

    @ModelAttribute("disqusEnabled")
    public boolean getDisqusEnabled() {
        return disqusEnabled;
    }

    @ModelAttribute("disqusName")
    public String getDisqusName() {
        return disqusName;
    }
}
