package com.tidyjava.bp

import spock.lang.Specification

class PostReaderSpec extends Specification {

    def postReader = new PostReader()

    def "no posts"() {
        given:
        postReader.postsLocation = "non-existent-or-empty"

        expect:
        postReader.readAll().isEmpty()
    }
}
