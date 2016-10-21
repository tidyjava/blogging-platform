package com.tidyjava.bp.post

import com.tidyjava.bp.git.GitSupport
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class PostReaderImplSpec extends Specification {

    @Rule
    TemporaryFolder temporaryFolder

    def gitSupport = Mock(GitSupport)
    def postReader = new PostReaderImpl()

    def setup() {
        postReader.gitSupport = gitSupport
        postReader.postFactory = new PostFactory()
    }

    def "readLast method should limit returned posts to given quantity"() {
        given:
        gitSupport.workTree >> temporaryFolder.root
        "create posts"(totalQuantity)

        when:
        def lastPosts = postReader.readLast(requestedQuantity)

        then:
        lastPosts.size() == expectedQuantity

        where:
        totalQuantity | requestedQuantity || expectedQuantity
        1             | 2                 || 1
        2             | 2                 || 2
        2             | 1                 || 1
    }

    def "create posts"(quantity) {
        (1..quantity).forEach({ temporaryFolder.newFile(it + ".md") })
    }
}
