package com.tidyjava.bp.git

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class GitSupportImplSpec extends Specification {
    static final CONTENTS_DIRECTORY = ".contents"
    static final CLONE_BLOCKER = CONTENTS_DIRECTORY + "/would-block-clone.txt";

    def gitSupport = new GitSupportImpl()

    def setup() {
        gitSupport.repositoryUrl = "https://github.com/tidyjava/blogging-platform-hello-world.git"
    }

    def cleanup() {
        gitSupport.closeRepository()
    }

    def "cloneRepository should clean target directory and clone given repository"() {
        given:
        File cloneBlocker = new File(CLONE_BLOCKER)
        cloneBlocker.mkdirs()
        cloneBlocker.createNewFile()

        when:
        gitSupport.cloneRepository()

        then:
        Files.exists(Paths.get(CONTENTS_DIRECTORY + "/hello-world.md"))
        !cloneBlocker.exists()
    }
}
