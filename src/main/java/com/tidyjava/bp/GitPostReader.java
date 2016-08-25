package com.tidyjava.bp;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tidyjava.bp.ExceptionUtils.rethrow;

@Service
public class GitPostReader {

    @Value("${blog.repositoryUrl}")
    private String repositoryUrl;

    private Git git;

    @PostConstruct
    public void cloneRepository() {
        File contentsDir = new File(".contents");
        clean(contentsDir);
        cloneTo(contentsDir);
    }

    private void clean(File dir) {
        rethrow(() -> FileUtils.deleteDirectory(dir));
        dir.mkdirs();
    }

    private void cloneTo(File contentsDir) {
        git = rethrow(() -> {
            try (Git call = Git.cloneRepository()
                    .setURI(repositoryUrl)
                    .setDirectory(contentsDir)
                    .call()) {
                return call;
            }
        });
    }

    public List<Post> readAll() {
        try (Repository repository = git.getRepository()) {
            File contentsDir = repository.getWorkTree();
            return Stream.of(contentsDir
                    .listFiles(withSupportedExtension()))
                    .map(MarkdownPostFactory::create)
                    .sorted(byDate().reversed())
                    .collect(Collectors.toList());
        }
    }

    private FilenameFilter withSupportedExtension() {
        return (dir, name) -> name.endsWith(MarkdownPostFactory.EXTENSION);
    }

    private Comparator<Post> byDate() {
        return (p1, p2) -> p1.getDate().compareTo(p2.getDate());
    }

    public Post readOne(String name) {
        File postFile = new File(toPostPath(name));
        if (!postFile.exists()) {
            throw new MissingPostException();
        }
        return MarkdownPostFactory.create(postFile);
    }

    private String toPostPath(String name) {
        try (Repository repository = git.getRepository()) {
            return repository.getWorkTree().getPath() + "/" + name + MarkdownPostFactory.EXTENSION;
        }
    }

    public void pullChanges() {
        git.pull();
    }

    @PreDestroy
    public void closeRepository() {
        git.close();
    }
}
