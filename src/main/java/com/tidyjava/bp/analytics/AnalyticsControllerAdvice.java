package com.tidyjava.bp.analytics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AnalyticsControllerAdvice {

    @Value("${analytics.enabled}")
    private boolean analyticsEnabled;

    @Value("${analytics.trackingCode}")
    private String analyticsCode;

    @ModelAttribute("analyticsEnabled")
    public boolean getAnalyticsEnabled() {
        return analyticsEnabled;
    }

    @ModelAttribute("analyticsCode")
    public String getAnalyticsCode() {
        return analyticsCode;
    }
}
