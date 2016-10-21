package com.tidyjava.bp.disqus

import com.tidyjava.bp.BloggingPlatform
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringApplicationConfiguration(BloggingPlatform)
@WebAppConfiguration
@DirtiesContext
class DisqusIntegrationSpec extends Specification {

    @Autowired
    WebApplicationContext wac

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }

    def "disqus component should fill model properties necessary to integrate with Disqus"() {
        expect:
        def result = mockMvc.perform(get("/post1"))
                .andExpect(status().isOk())
                .andReturn()

        result.modelAndView.model.blogUrl == 'http://localhost:8080'
        result.modelAndView.model.disqusEnabled == true
        result.modelAndView.model.disqusName == 'disqus-test'
    }
}