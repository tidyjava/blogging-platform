package com.tidyjava.bp.git

import com.tidyjava.bp.BloggingPlatform
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringApplicationConfiguration(BloggingPlatform)
@WebAppConfiguration
@DirtiesContext
class GitControllerIntegrationSpec extends Specification {

    @Autowired
    WebApplicationContext wac

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }

    def "post commit hook should be successful"() {
        expect:
        mockMvc.perform(post("/git/commit"))
                .andExpect(status().isOk())
    }
}
