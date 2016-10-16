package com.tidyjava.bp.post

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class PostReaderSpec extends Specification {
    static final CONTENTS_DIRECTORY = ".contents"
    static final CLONE_BLOCKER = CONTENTS_DIRECTORY + "/would-block-clone.txt";

    def gitPostReader = new PostReader()

    def setup() {
        gitPostReader.repositoryUrl = "https://github.com/tidyjava/blogging-platform-hello-world.git"
    }

    def cleanup() {
        gitPostReader.closeRepository()
    }

    def 'should clean and clone'() {
        given:
        File cloneBlocker = new File(CLONE_BLOCKER)
        cloneBlocker.mkdirs()
        cloneBlocker.createNewFile()

        when:
        gitPostReader.cloneRepository()

        then:
        Files.exists(Paths.get(CONTENTS_DIRECTORY + "/hello-world.md"))
        !cloneBlocker.exists()
    }
}
