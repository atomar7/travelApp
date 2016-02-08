package com.travel.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bucket.
 */

@Document(collection = "bucket")
public class Bucket implements Serializable {

    @Id
    private String id;

    @Field("title")
    private String title;
    
    @Field("description")
    private String description;
    
    @Field("image_urls")
    private String[] imageUrls;
    
    @Field("people_count")
    private Integer peopleCount;
    
    @Field("people")
    private String[] people;
    
    @Field("achieved_count")
    private Integer achievedCount;
    
    @Field("achieve")
    private String[] achieve;
    
    @Field("experiences")
    private String[] experiences;
    
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

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }
    
    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }
    
    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String[] getPeople() {
        return people;
    }
    
    public void setPeople(String[] people) {
        this.people = people;
    }

    public Integer getAchievedCount() {
        return achievedCount;
    }
    
    public void setAchievedCount(Integer achievedCount) {
        this.achievedCount = achievedCount;
    }

    public String[] getAchieve() {
        return achieve;
    }
    
    public void setAchieve(String[] achieve) {
        this.achieve = achieve;
    }

    public String[] getExperiences() {
        return experiences;
    }
    
    public void setExperiences(String[] experiences) {
        this.experiences = experiences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bucket bucket = (Bucket) o;
        if(bucket.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bucket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bucket{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", imageUrls='" + imageUrls + "'" +
            ", peopleCount='" + peopleCount + "'" +
            ", people='" + people + "'" +
            ", achievedCount='" + achievedCount + "'" +
            ", achieve='" + achieve + "'" +
            ", experiences='" + experiences + "'" +
            '}';
    }
}
