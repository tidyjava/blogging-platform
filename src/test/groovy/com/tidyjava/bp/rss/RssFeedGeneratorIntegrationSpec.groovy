package com.tidyjava.bp.rss

import com.rometools.rome.feed.synd.SyndCategoryImpl
import com.tidyjava.bp.BloggingPlatform
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

import java.util.stream.Collectors

@SpringApplicationConfiguration(BloggingPlatform)
@WebAppConfiguration
@DirtiesContext
class RssFeedGeneratorIntegrationSpec extends Specification {

    @Autowired
    RssFeedGenerator rssFeedGenerator

    def "feed generation"() {
        when:
        def feed = rssFeedGenerator.generate()

        then:
        feed.title == 'Test Blog'
        feed.link == 'http://localhost:8080'

        def entries = feed.entries
        assertTestItem(entries[0], 2, ['tagged'])
        assertTestItem(entries[1], 1, ['tagged', 'first'])
        assertTiltItem(entries[2])
    }

    void assertTestItem(entry, n, tags) {
        assert entry.title == "Post $n"
        assert entry.description.value == "<p>Summary $n</p>\n"
        assert entry.link == "http://localhost:8080/post$n"
        assert entry.categories == toCategories(tags)
        assert entry.author == "Author $n"
        assert entry.publishedDate.format("yyyy-MM-dd") == "197$n-01-01"
        assert entry.contents[0].value == "<p><strong>Content $n</strong></p>\n"
    }

    def toCategories(tags) {
        tags.stream().map({
            def category = new SyndCategoryImpl()
            category.name = it
            category
        }).collect(Collectors.toList())
    }

    void assertTiltItem(entry) {
        assert entry.title == "TILT"
        assert entry.description.value == "<p>TILT</p>\n"
        assert entry.link == "http://localhost:8080/tilt"
        assert entry.categories == []
        assert entry.author == "TILT"
        assert entry.publishedDate.format("YYYY-MM-DD") == "1970-01-01"
        assert entry.contents[0].value == ""
    }
}
