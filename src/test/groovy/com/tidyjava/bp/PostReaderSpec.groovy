package com.tidyjava.bp

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class PostReaderSpec extends Specification {

    @Rule
    TemporaryFolder directory

    def postReader = new PostReader()

    def "non-existent posts location"() {
        given:
        postReader.postsLocation = "non-existent"

        expect:
        postReader.readAll().isEmpty()
    }

    def "empty posts location"() {
        given:
        postReader.postsLocation = directory.newFolder("empty").path

        expect:
        postReader.readAll().isEmpty()
    }
}
