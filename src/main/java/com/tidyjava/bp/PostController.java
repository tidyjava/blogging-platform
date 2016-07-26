package com.tidyjava.bp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    @Autowired
    private PostReader postReader;

    @Value("${blog.name}")
    private String blogName;

    @ModelAttribute("blogName")
    public String getBlogName() {
        return blogName;
    }

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postReader.summarize());
        return "home";
    }

    @RequestMapping("/{path}")
    public String post(@PathVariable("path") String path, Model model) {
        model.addAttribute("post", postReader.readPost(path));
        return "post";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostReader.MissingPost.class)
    public String missingPost() {
        return "not-found";
    }
}
