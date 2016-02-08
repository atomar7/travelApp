package com.travel.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Blog.
 */

@Document(collection = "blog")
public class Blog implements Serializable {

    @Id
    private String id;

    @Field("title")
    private String title;
    
    @Field("blog")
    private String blog;
    
    @Field("image_urls")
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
        Blog blog = (Blog) o;
        if(blog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, blog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Blog{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", blog='" + blog + "'" +
            ", imageUrls='" + imageUrls + "'" +
            '}';
    }
}
