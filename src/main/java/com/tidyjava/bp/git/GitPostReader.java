package com.tidyjava.bp.git;

import com.tidyjava.bp.post.MissingPostException;
import com.tidyjava.bp.post.Post;
import com.tidyjava.bp.post.PostFactory;
import com.tidyjava.bp.post.PostReader;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.tidyjava.bp.util.ExceptionUtils.rethrow;

@Service
public class GitPostReader implements PostReader {

    @Value("${blog.repositoryUrl}")
    private String repositoryUrl;

    @Autowired
    private PostFactory postFactory;

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
        git = rethrow(() -> Git.cloneRepository()
                .setURI(repositoryUrl)
                .setDirectory(contentsDir)
                .call());
    }

    @Override
    public List<Post> readAll() {
        File contentsDir = git.getRepository().getWorkTree();
        return Stream.of(contentsDir
                .listFiles(withSupportedExtension()))
                .map(postFactory::create)
                .sorted(byDate().reversed())
                .collect(Collectors.toList());
    }

    private FilenameFilter withSupportedExtension() {
        return (dir, name) -> name.endsWith(postFactory.extension());
    }

    private Comparator<Post> byDate() {
        return (p1, p2) -> p1.getDate().compareTo(p2.getDate());
    }

    @Override
    public Post readOne(String name) {
        File postFile = new File(toPostPath(name));
        if (!postFile.exists()) {
            throw new MissingPostException();
        }
        return postFactory.create(postFile);
    }

    @Override
    public List<Post> readByTag(String tag) {
        return readAll()
                .stream()
                .filter(post -> post.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    private String toPostPath(String name) {
        return git.getRepository().getWorkTree().getPath() + "/" + name + postFactory.extension();
    }

    public void pullChanges() {
        rethrow(() -> git.pull().call());
    }

    @PreDestroy
    public void closeRepository() {
        git.close();
    }
}
