package com.tidyjava.bp.social;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "social")
public class SocialLinkProperties {

    @Valid
    private List<SocialLink> links;

    public List<SocialLink> getLinks() {
        return links;
    }

    public void setLinks(List<SocialLink> links) {
        this.links = links;
    }
}
