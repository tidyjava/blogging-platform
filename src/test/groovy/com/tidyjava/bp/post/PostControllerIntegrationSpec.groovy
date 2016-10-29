package com.tidyjava.bp.post

import com.tidyjava.bp.BloggingPlatform
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@SpringApplicationConfiguration(BloggingPlatform)
@WebAppConfiguration
@DirtiesContext
class PostControllerIntegrationSpec extends Specification {

    @Autowired
    WebApplicationContext wac

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }

    def "home endpoint should present all posts"() {
        expect:
        def result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andReturn()

        result.modelAndView.model.title == 'Test Blog'
        result.modelAndView.model.subtitle == 'What we test, we believe.'

        def posts = result.modelAndView.model.posts
        "assert post contains test data"(posts[0], 2, ['tagged'])
        "assert post contains test data"(posts[1], 1, ['tagged', 'first'])
        "assert post contains tilt data"(posts[2])
    }

    def "post endpoint should present a single post"() {
        expect:
        def result = mockMvc.perform(get("/post$n"))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andReturn()

        result.modelAndView.model.title == "Post $n"
        "assert post contains test data"(result.modelAndView.model.post, n, tags)

        where:
        n || tags
        1 || ['tagged', 'first']
        2 || ['tagged']
    }

    def "tag endpoint should present posts with given tag"() {
        expect:
        def result = mockMvc.perform(get("/tag/tagged"))
                .andExpect(status().isOk())
                .andExpect(view().name("tag"))
                .andReturn()

        result.modelAndView.model.title == 'tagged'
        def posts = result.modelAndView.model.posts
        "assert post contains test data"(posts[0], 2, ['tagged'])
        "assert post contains test data"(posts[1], 1, ['tagged', 'first'])
    }

    def "post with missing fields should be presented with default values"() {
        expect:
        def result = mockMvc.perform(get("/tilt"))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andReturn()

        result.modelAndView.model.title == "TILT"
        "assert post contains tilt data"(result.modelAndView.model.post)
    }

    def "missing post endpoint should present not-found page"() {
        expect:
        mockMvc.perform(get("/surely-not-existent"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("not-found"))
    }

    void "assert post contains test data"(post, n, tags) {
        assert post.id == "post$n"
        assert post.title == "Post $n"
        assert post.summary == "<p>Summary $n</p>\n"
        assert post.date.format(ISO_LOCAL_DATE) == "197$n-01-01"
        assert post.url == "/post$n"
        assert post.content == "<p><strong>Content $n</strong></p>\n"
        assert post.tags == toTags(tags)
        assert post.author == "Author $n"
    }

    def toTags(tags) {
        tags.collect {
            new Tag(it)
        }
    }

    void "assert post contains tilt data"(post) {
        assert post.id == "tilt"
        assert post.title == "TILT"
        assert post.summary == "<p>TILT</p>\n"
        assert post.date.format(ISO_LOCAL_DATE) == "1970-01-01"
        assert post.url == "/tilt"
        assert post.content == ""
        assert post.author == "TILT"
    }
}
