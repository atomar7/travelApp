package com.travel.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A People.
 */

@Document(collection = "people")
public class People implements Serializable {

    @Id
    private String id;

    @Field("followers_count")
    private Integer followersCount;
    
    @Field("following_count")
    private Integer followingCount;
    
    @Field("bucket_count")
    private Integer bucketCount;
    
    @Field("bucket_achieved_count")
    private Integer bucketAchievedCount;
    
    @Field("followers")
    private String[] followers;
    
    @Field("following")
    private String[] following;
    
    @Field("buckets")
    private String[] buckets;
    
    @Field("bucket_achieved")
    private String[] bucketAchieved;
    
    @Field("rating")
    private Float rating;
    
    @Field("user_id")
    private String userId;
    
    @Field("blogs_count")
    private Integer blogsCount;
    
    @Field("blogs")
    private String[] blogs;
    
    @Field("image_url")
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
        People people = (People) o;
        if(people.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, people.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "People{" +
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
