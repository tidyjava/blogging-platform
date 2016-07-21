package com.tidyjava.bp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
class BlogControllerSpec extends Specification {

    @Autowired
    def WebApplicationContext wac

    def mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }

    def "home"() {
        expect:
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Post 1")))
                .andExpect(content().string(containsString("Summary 1")))
                .andExpect(content().string(containsString("/post1")))
                .andExpect(content().string(containsString("Post 2")))
                .andExpect(content().string(containsString("Summary 2")))
                .andExpect(content().string(containsString("/post2")))
    }

    def "post"(postNumber) {
        expect:
        mockMvc.perform(get("/post" + postNumber))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Post " + postNumber)))
                .andExpect(content().string(containsString("Summary " + postNumber)))
                .andExpect(content().string(containsString("<strong>Content " + postNumber + "</strong>")))

        where:
        postNumber << [1, 2]
    }

    // TODO error cases
}
