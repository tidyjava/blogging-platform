package com.tidyjava.bp.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PostController {

    @Value("${blog.name}")
    private String blogName;

    @Value("${blog.description}")
    private String blogDescription;

    @Autowired
    private PostReaderImpl postReader;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", blogName);
        model.addAttribute("subtitle", blogDescription);
        model.addAttribute("posts", postReader.readAll());
        return "home";
    }

    @RequestMapping(Post.URL_PREFIX + "{path}")
    public String post(@PathVariable("path") String path, Model model) {
        Post post = postReader.readOne(path);
        model.addAttribute("title", post.getTitle());
        model.addAttribute("post", post);
        return "post";
    }

    @RequestMapping(Tag.URL_PREFIX + "{path}")
    public String tag(@PathVariable("path") String path, Model model) {
        model.addAttribute("title", path);
        model.addAttribute("tag", path);
        model.addAttribute("posts", postReader.readByTag(path));
        return "tag";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MissingPostException.class)
    public String missingPost() {
        return "not-found";
    }
}
