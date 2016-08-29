package com.tidyjava.bp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    @Value("${blog.name}")
    private String blogName;

    @Autowired
    private GitPostReader postReader;

    @ModelAttribute("blogName")
    public String getBlogName() {
        return blogName;
    }

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postReader.readAll());
        return "home";
    }

    @RequestMapping("/{path}")
    public String post(@PathVariable("path") String path, Model model) {
        model.addAttribute("post", postReader.readOne(path));
        return "post";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MissingPostException.class)
    public String missingPost() {
        return "not-found";
    }
}
