package com.tidyjava.bp.post;

import java.io.File;

public interface PostFactory {
    String extension();

    Post create(File file);
}
