package com.tidyjava.bp.post;

import com.tidyjava.bp.git.GitSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostReaderImpl implements PostReader {

    @Autowired
    private PostFactory postFactory;

    @Autowired
    private GitSupport gitSupport;

    @Override
    public List<Post> readLast(int quantity) {
        List<Post> all = readAll();
        return all.subList(0, Math.min(quantity, all.size()));
    }

    List<Post> readAll() {
        File contentsDir = gitSupport.getWorkTree();
        return Stream.of(contentsDir
                .listFiles(withSupportedExtension()))
                .map(postFactory::create)
                .sorted(byDate().reversed())
                .collect(Collectors.toList());
    }

    private FilenameFilter withSupportedExtension() {
        return (dir, name) -> name.endsWith(PostFactory.EXTENSION);
    }

    private Comparator<Post> byDate() {
        return (p1, p2) -> p1.getDate().compareTo(p2.getDate());
    }

    Post readOne(String name) {
        File postFile = new File(toPostPath(name));
        if (!postFile.exists()) {
            throw new MissingPostException();
        }
        return postFactory.create(postFile);
    }

    private String toPostPath(String name) {
        return gitSupport.getWorkTree().getPath() + "/" + name + PostFactory.EXTENSION;
    }

    List<Post> readByTag(String tag) {
        return readAll()
                .stream()
                .filter(post -> post.getTags().contains(tag))
                .collect(Collectors.toList());
    }
}
