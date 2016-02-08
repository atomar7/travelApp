package com.travel.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Bucket entity.
 */
public class BucketDTO implements Serializable {

    private String id;

    private String title;


    private String description;


    private String[] imageUrls;


    private Integer peopleCount;


    private String[] people;


    private Integer achievedCount;


    private String[] achieve;


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

        BucketDTO bucketDTO = (BucketDTO) o;

        if ( ! Objects.equals(id, bucketDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BucketDTO{" +
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
