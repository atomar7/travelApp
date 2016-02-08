package com.travel.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the People entity.
 */
public class PeopleDTO implements Serializable {

    private String id;

    private Integer followersCount;


    private Integer followingCount;


    private Integer bucketCount;


    private Integer bucketAchievedCount;


    private String[] followers;


    private String[] following;


    private String[] buckets;


    private String[] bucketAchieved;


    private Float rating;


    private String userId;


    private Integer blogsCount;


    private String[] blogs;


    private String imageUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }
    public Integer getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }
    public Integer getBucketCount() {
        return bucketCount;
    }

    public void setBucketCount(Integer bucketCount) {
        this.bucketCount = bucketCount;
    }
    public Integer getBucketAchievedCount() {
        return bucketAchievedCount;
    }

    public void setBucketAchievedCount(Integer bucketAchievedCount) {
        this.bucketAchievedCount = bucketAchievedCount;
    }
    public String[] getFollowers() {
        return followers;
    }

    public void setFollowers(String[] followers) {
        this.followers = followers;
    }
    public String[] getFollowing() {
        return following;
    }

    public void setFollowing(String[] following) {
        this.following = following;
    }
    public String[] getBuckets() {
        return buckets;
    }

    public void setBuckets(String[] buckets) {
        this.buckets = buckets;
    }
    public String[] getBucketAchieved() {
        return bucketAchieved;
    }

    public void setBucketAchieved(String[] bucketAchieved) {
        this.bucketAchieved = bucketAchieved;
    }
    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Integer getBlogsCount() {
        return blogsCount;
    }

    public void setBlogsCount(Integer blogsCount) {
        this.blogsCount = blogsCount;
    }
    public String[] getBlogs() {
        return blogs;
    }

    public void setBlogs(String[] blogs) {
        this.blogs = blogs;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PeopleDTO peopleDTO = (PeopleDTO) o;

        if ( ! Objects.equals(id, peopleDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PeopleDTO{" +
            "id=" + id +
            ", followersCount='" + followersCount + "'" +
            ", followingCount='" + followingCount + "'" +
            ", bucketCount='" + bucketCount + "'" +
            ", bucketAchievedCount='" + bucketAchievedCount + "'" +
            ", followers='" + followers + "'" +
            ", following='" + following + "'" +
            ", buckets='" + buckets + "'" +
            ", bucketAchieved='" + bucketAchieved + "'" +
            ", rating='" + rating + "'" +
            ", userId='" + userId + "'" +
            ", blogsCount='" + blogsCount + "'" +
            ", blogs='" + blogs + "'" +
            ", imageUrl='" + imageUrl + "'" +
            '}';
    }
}
