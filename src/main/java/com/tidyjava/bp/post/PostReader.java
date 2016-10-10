package com.tidyjava.bp.post;

import java.util.List;

public interface PostReader {
    List<Post> readAll();

    Post readOne(String name) throws MissingPostException;

    List<Post> readByTag(String tag);
}
