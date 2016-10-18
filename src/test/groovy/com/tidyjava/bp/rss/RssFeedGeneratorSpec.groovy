package com.tidyjava.bp.rss

import com.tidyjava.bp.post.Post
import com.tidyjava.bp.post.PostReader
import spock.lang.Specification

import java.time.LocalDate

class RssFeedGeneratorSpec extends Specification {
    def postReader = Mock(PostReader)
    def rssFeedGenerator = new RssFeedGenerator()

    def setup() {
        rssFeedGenerator.postReader = postReader
    }

    def "generates feed only for 5 last posts"() {
        given:
        def tooManyPosts = []
        (1..10).forEach({ tooManyPosts << post() })
        postReader.readAll() >> tooManyPosts

        when:
        rssFeedGenerator.generate()

        then:

    }

    def post() {
        new Post("test", "test", "test", LocalDate.now(), "test", "test", [], "test")
    }
}
