package com.tidyjava.bp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitController {

    @Autowired
    private GitPostReader gitPostReader;

    @RequestMapping(path = "/git/commit", method = RequestMethod.POST)
    public void postCommit() {
        gitPostReader.pullChanges();
    }
}
