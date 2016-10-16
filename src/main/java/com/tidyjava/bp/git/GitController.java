package com.tidyjava.bp.git;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitController {

    @Autowired
    private GitSupportImpl gitSupport;

    @RequestMapping(path = "/git/commit", method = RequestMethod.POST)
    public void postCommit() {
        gitSupport.pullChanges();
    }
}
