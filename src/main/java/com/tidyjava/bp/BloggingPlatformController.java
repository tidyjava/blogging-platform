package com.tidyjava.bp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BloggingPlatformController {

    @Value("${blog.name}")
    private String blogName;

    @ModelAttribute("blogName")
    public String getBlogName() {
        return blogName;
    }
}
