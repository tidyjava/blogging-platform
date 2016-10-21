package com.tidyjava.bp.social;

import org.hibernate.validator.constraints.NotBlank;

public class SocialLink {

    @NotBlank
    private String name;

    @NotBlank
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SocialLink that = (SocialLink) o;

        if (!name.equals(that.name)) return false;
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }
}
