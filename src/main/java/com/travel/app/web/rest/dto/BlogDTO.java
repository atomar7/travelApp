package com.travel.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Blog entity.
 */
public class BlogDTO implements Serializable {

    private String id;

    private String title;


    private String blog;


    private String[] imageUrls;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }
    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlogDTO blogDTO = (BlogDTO) o;

        if ( ! Objects.equals(id, blogDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BlogDTO{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", blog='" + blog + "'" +
            ", imageUrls='" + imageUrls + "'" +
            '}';
    }
}
