package com.tidyjava.bp.rss

import com.tidyjava.bp.BloggingPlatform
import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringApplicationConfiguration(BloggingPlatform)
@WebAppConfiguration
@DirtiesContext
class RssControllerIntegrationSpec extends Specification {

    @Autowired
    WebApplicationContext wac

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }

    def "rss"() {
        expect:
        mockMvc.perform(get("/rss/"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("rss")))
                .andExpect(content().string(Matchers.containsString("Post 1")))
                .andExpect(content().string(Matchers.containsString("Post 2")))
                .andExpect(content().string(Matchers.containsString("TILT")))
    }
}
