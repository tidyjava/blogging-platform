package com.tidyjava.bp

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

@SpringApplicationConfiguration(BloggingPlatform.class)
@WebAppConfiguration
@DirtiesContext
class PostControllerIntegrationSpec extends Specification {

    @Autowired
    WebApplicationContext wac

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }

    def "home"() {
        expect:
        def result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andReturn()
        def posts = result.modelAndView.model.posts
        assertTestPost(posts[0], 2)
        assertTestPost(posts[1], 1)
        assertTiltPost(posts[2])
    }

    def "post"(n) {
        expect:
        def result = mockMvc.perform(get("/post$n"))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andReturn()
        assertTestPost(result.modelAndView.model.post, n)

        where:
        n << [1, 2]
    }

    def "tilt post"() {
        expect:
        def result = mockMvc.perform(get("/tilt"))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andReturn()
        assertTiltPost(result.modelAndView.model.post)
    }

    def "missing post"() {
        expect:
        mockMvc.perform(get("/surely-not-existent"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("not-found"))
    }

    void assertTestPost(post, n) {
        assert post.title == "Post $n"
        assert post.summary == "Summary $n"
        assert post.date.format(ISO_LOCAL_DATE) == "197$n-01-01"
        assert post.url == "/post$n"
        assert post.content == "<p><strong>Content $n</strong></p>\n"
    }

    void assertTiltPost(post) {
        assert post.title == "TILT"
        assert post.summary == "TILT"
        assert post.date.format(ISO_LOCAL_DATE) == "1970-01-01"
        assert post.url == "/tilt"
        assert post.content == ""
    }
}
