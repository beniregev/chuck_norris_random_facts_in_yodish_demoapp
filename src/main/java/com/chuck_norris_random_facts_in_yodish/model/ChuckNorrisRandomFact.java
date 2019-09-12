package com.chuck_norris_random_facts_in_yodish.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Original Chuck Norris fact structure for JSON
 */
public class ChuckNorrisRandomFact {
    private String[] categories;
    private String createdAt;
    private String iconUrl;
    private int id;
    private String updatedAt;
    private String url;
    private String value;

    public ChuckNorrisRandomFact() {
    }

    public ChuckNorrisRandomFact(String[] categories, String createdAt, String iconUrl, int id, String updatedAt, String url, String value) {
        this.categories = categories;
        this.createdAt = createdAt;
        this.iconUrl = iconUrl;
        this.id = id;
        this.updatedAt = updatedAt;
        this.url = url;
        this.value = value;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChuckNorrisRandomFact that = (ChuckNorrisRandomFact) o;
        return id == that.id &&
                Arrays.equals(categories, that.categories) &&
                createdAt.equals(that.createdAt) &&
                iconUrl.equals(that.iconUrl) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                url.equals(that.url) &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(createdAt, iconUrl, id, updatedAt, url, value);
        result = 31 * result + Arrays.hashCode(categories);
        return result;
    }

    @Override
    public String toString() {
        return "ChuckNorrisRandomFact{" +
                "categories=" + Arrays.toString(categories) +
                ", createdat='" + createdAt + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", id=" + id +
                ", updatedAt='" + updatedAt + '\'' +
                ", url='" + url + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
