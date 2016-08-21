package com.tidyjava.bp

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class GitPostReaderSpec extends Specification {
    static final CONTENTS_DIRECTORY = ".contents"
    static final CLONE_BLOCKER = Paths.get(CONTENTS_DIRECTORY + "/would-block-clone.txt");

    def gitPostReader = new GitPostReader()

    def setup() {
        gitPostReader.repositoryUrl = "https://github.com/tidyjava/blogging-platform-hello-world.git"
    }

    def cleanup() {
        gitPostReader.closeRepository()
    }

    def 'should clean and clone'() {
        given:
        Files.createFile(CLONE_BLOCKER)

        when:
        gitPostReader.cloneRepository()

        then:
        Files.exists(Paths.get(CONTENTS_DIRECTORY + "/hello-world.md"))
        !Files.exists(CLONE_BLOCKER)
    }
}
