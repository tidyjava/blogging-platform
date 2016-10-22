package com.tidyjava.bp.post;

import java.util.List;

public interface PostReader {
    List<Post> readLast(int quantity);

    List<Post> readAll();
}
