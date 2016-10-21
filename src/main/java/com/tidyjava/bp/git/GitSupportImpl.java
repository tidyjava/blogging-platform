package com.tidyjava.bp.git;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;

import static com.tidyjava.bp.util.ExceptionUtils.rethrow;

@Component
class GitSupportImpl implements GitSupport {

    @Value("${git.repository}")
    private String repositoryUrl;

    private Git git;

    @PostConstruct
    void cloneRepository() {
        File contentsDir = new File(".contents");
        clean(contentsDir);
        cloneTo(contentsDir);
    }

    private void clean(File dir) {
        rethrow(() -> FileUtils.deleteDirectory(dir));
        dir.mkdirs();
    }

    private void cloneTo(File contentsDir) {
        git = rethrow(() -> Git.cloneRepository()
                .setURI(repositoryUrl)
                .setDirectory(contentsDir)
                .call());
    }

    @Override
    public File getWorkTree() {
        return git.getRepository().getWorkTree();
    }

    void pullChanges() {
        rethrow(() -> git.pull().call());
    }

    @PreDestroy
    void closeRepository() {
        git.close();
    }
}
