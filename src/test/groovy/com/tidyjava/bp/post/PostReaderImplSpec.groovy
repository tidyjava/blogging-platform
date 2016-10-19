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

    def "read last 5"() {
        given:
        gitSupport.workTree >> temporaryFolder.root
        (1..posts).forEach({ temporaryFolder.newFile(it + ".md") })

        when:
        def last5 = postReader.readLast5()

        then:
        last5.size() == expectedPosts

        where:
        posts || expectedPosts
        1     || 1
        5     || 5
        10    || 5
    }
}
