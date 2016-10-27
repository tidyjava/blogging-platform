package com.tidyjava.bp.post;

import com.tidyjava.bp.git.GitSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

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

    @Override
    public List<Post> readAll() {
        File contentsDir = gitSupport.getWorkTree();
        return Stream.of(contentsDir
                .listFiles(withSupportedExtension()))
                .map(postFactory::create)
                .filter(notFuturePosts())
                .sorted(comparing(Post::getDate).reversed())
                .collect(Collectors.toList());
    }

    private FilenameFilter withSupportedExtension() {
        return (dir, name) -> name.endsWith(PostFactory.EXTENSION);
    }

    private Predicate<Post> notFuturePosts() {
        return post -> !post.getDate().isAfter(LocalDate.now());
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

    List<Post> readByTag(String tagName) {
        Tag tag = new Tag(tagName);
        return readAll()
                .stream()
                .filter(post -> post.hasTag(tag))
                .collect(Collectors.toList());
    }
}
