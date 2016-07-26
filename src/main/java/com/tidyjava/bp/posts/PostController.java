package com.tidyjava.bp.posts;

import com.tidyjava.bp.BloggingPlatformController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PostController extends BloggingPlatformController {

    @Autowired
    private PostReader postReader;

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
