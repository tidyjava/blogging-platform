package com.tidyjava.bp.analytics

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
class AnalyticsIntegrationSpec extends Specification {

    @Autowired
    WebApplicationContext wac

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
    }

    def "analytics component should fill model properties necessary to integrate with Google Analytics"() {
        expect:
        def result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn()

        result.modelAndView.model.analyticsEnabled == true
        result.modelAndView.model.analyticsCode == '123'
    }
}