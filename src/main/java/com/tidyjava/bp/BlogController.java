package com.tidyjava.bp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController {

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
}
