package com.tidyjava.bp.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class SocialLinksControllerAdvice {

    @Autowired
    private SocialLinkProperties socialLinkProperties;

    @ModelAttribute("socialLinks")
    public List<SocialLink> getSocialLinks() {
        return socialLinkProperties.getLinks();
    }
}
