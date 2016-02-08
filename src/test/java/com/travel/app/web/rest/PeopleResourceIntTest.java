package com.travel.app.web.rest;

import com.travel.app.Application;
import com.travel.app.domain.People;
import com.travel.app.repository.PeopleRepository;
import com.travel.app.service.PeopleService;
import com.travel.app.web.rest.dto.PeopleDTO;
import com.travel.app.web.rest.mapper.PeopleMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PeopleResource REST controller.
 *
 * @see PeopleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PeopleResourceIntTest {


    private static final Integer DEFAULT_FOLLOWERS_COUNT = 1;
    private static final Integer UPDATED_FOLLOWERS_COUNT = 2;

    private static final Integer DEFAULT_FOLLOWING_COUNT = 1;
    private static final Integer UPDATED_FOLLOWING_COUNT = 2;

    private static final Integer DEFAULT_BUCKET_COUNT = 1;
    private static final Integer UPDATED_BUCKET_COUNT = 2;

    private static final Integer DEFAULT_BUCKET_ACHIEVED_COUNT = 1;
    private static final Integer UPDATED_BUCKET_ACHIEVED_COUNT = 2;
    private static final String[] DEFAULT_FOLLOWERS = {"AAAAA"};
    private static final String[] UPDATED_FOLLOWERS = {"BBBBB"};
    private static final String[] DEFAULT_FOLLOWING = {"AAAAA"};
    private static final String[] UPDATED_FOLLOWING = {"BBBBB"};
    private static final String[] DEFAULT_BUCKETS = {"AAAAA"};
    private static final String[] UPDATED_BUCKETS = {"BBBBB"};
    private static final String[] DEFAULT_BUCKET_ACHIEVED = {"AAAAA"};
    private static final String[] UPDATED_BUCKET_ACHIEVED = {"BBBBB"};

    private static final Float DEFAULT_RATING = 1F;
    private static final Float UPDATED_RATING = 2F;
    private static final String DEFAULT_USER_ID = "AAAAA";
    private static final String UPDATED_USER_ID = "BBBBB";

    private static final Integer DEFAULT_BLOGS_COUNT = 1;
    private static final Integer UPDATED_BLOGS_COUNT = 2;
    private static final String[] DEFAULT_BLOGS = {"AAAAA"};
    private static final String[] UPDATED_BLOGS = {"BBBBB"};
    private static final String DEFAULT_IMAGE_URL = "AAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBB";

    @Inject
    private PeopleRepository peopleRepository;

    @Inject
    private PeopleMapper peopleMapper;

    @Inject
    private PeopleService peopleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPeopleMockMvc;

    private People people;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PeopleResource peopleResource = new PeopleResource();
        ReflectionTestUtils.setField(peopleResource, "peopleService", peopleService);
        ReflectionTestUtils.setField(peopleResource, "peopleMapper", peopleMapper);
        this.restPeopleMockMvc = MockMvcBuilders.standaloneSetup(peopleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        peopleRepository.deleteAll();
        people = new People();
        people.setFollowersCount(DEFAULT_FOLLOWERS_COUNT);
        people.setFollowingCount(DEFAULT_FOLLOWING_COUNT);
        people.setBucketCount(DEFAULT_BUCKET_COUNT);
        people.setBucketAchievedCount(DEFAULT_BUCKET_ACHIEVED_COUNT);
        people.setFollowers(DEFAULT_FOLLOWERS);
        people.setFollowing(DEFAULT_FOLLOWING);
        people.setBuckets(DEFAULT_BUCKETS);
        people.setBucketAchieved(DEFAULT_BUCKET_ACHIEVED);
        people.setRating(DEFAULT_RATING);
        people.setUserId(DEFAULT_USER_ID);
        people.setBlogsCount(DEFAULT_BLOGS_COUNT);
        people.setBlogs(DEFAULT_BLOGS);
        people.setImageUrl(DEFAULT_IMAGE_URL);
    }

    @Test
    public void createPeople() throws Exception {
        int databaseSizeBeforeCreate = peopleRepository.findAll().size();

        // Create the People
        PeopleDTO peopleDTO = peopleMapper.peopleToPeopleDTO(people);

        restPeopleMockMvc.perform(post("/api/peoples")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
                .andExpect(status().isCreated());

        // Validate the People in the database
        List<People> peoples = peopleRepository.findAll();
        assertThat(peoples).hasSize(databaseSizeBeforeCreate + 1);
        People testPeople = peoples.get(peoples.size() - 1);
        assertThat(testPeople.getFollowersCount()).isEqualTo(DEFAULT_FOLLOWERS_COUNT);
        assertThat(testPeople.getFollowingCount()).isEqualTo(DEFAULT_FOLLOWING_COUNT);
        assertThat(testPeople.getBucketCount()).isEqualTo(DEFAULT_BUCKET_COUNT);
        assertThat(testPeople.getBucketAchievedCount()).isEqualTo(DEFAULT_BUCKET_ACHIEVED_COUNT);
        assertThat(testPeople.getFollowers()).isEqualTo(DEFAULT_FOLLOWERS);
        assertThat(testPeople.getFollowing()).isEqualTo(DEFAULT_FOLLOWING);
        assertThat(testPeople.getBuckets()).isEqualTo(DEFAULT_BUCKETS);
        assertThat(testPeople.getBucketAchieved()).isEqualTo(DEFAULT_BUCKET_ACHIEVED);
        assertThat(testPeople.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testPeople.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPeople.getBlogsCount()).isEqualTo(DEFAULT_BLOGS_COUNT);
        assertThat(testPeople.getBlogs()).isEqualTo(DEFAULT_BLOGS);
        assertThat(testPeople.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    public void getAllPeoples() throws Exception {
        // Initialize the database
        peopleRepository.save(people);

        // Get all the peoples
        restPeopleMockMvc.perform(get("/api/peoples?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(people.getId())))
                .andExpect(jsonPath("$.[*].followersCount").value(hasItem(DEFAULT_FOLLOWERS_COUNT)))
                .andExpect(jsonPath("$.[*].followingCount").value(hasItem(DEFAULT_FOLLOWING_COUNT)))
                .andExpect(jsonPath("$.[*].bucketCount").value(hasItem(DEFAULT_BUCKET_COUNT)))
                .andExpect(jsonPath("$.[*].bucketAchievedCount").value(hasItem(DEFAULT_BUCKET_ACHIEVED_COUNT)))
                .andExpect(jsonPath("$.[*].followers").value(hasItem(DEFAULT_FOLLOWERS.toString())))
                .andExpect(jsonPath("$.[*].following").value(hasItem(DEFAULT_FOLLOWING.toString())))
                .andExpect(jsonPath("$.[*].buckets").value(hasItem(DEFAULT_BUCKETS.toString())))
                .andExpect(jsonPath("$.[*].bucketAchieved").value(hasItem(DEFAULT_BUCKET_ACHIEVED.toString())))
                .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
                .andExpect(jsonPath("$.[*].blogsCount").value(hasItem(DEFAULT_BLOGS_COUNT)))
                .andExpect(jsonPath("$.[*].blogs").value(hasItem(DEFAULT_BLOGS.toString())))
                .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }

    @Test
    public void getPeople() throws Exception {
        // Initialize the database
        peopleRepository.save(people);

        // Get the people
        restPeopleMockMvc.perform(get("/api/peoples/{id}", people.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(people.getId()))
            .andExpect(jsonPath("$.followersCount").value(DEFAULT_FOLLOWERS_COUNT))
            .andExpect(jsonPath("$.followingCount").value(DEFAULT_FOLLOWING_COUNT))
            .andExpect(jsonPath("$.bucketCount").value(DEFAULT_BUCKET_COUNT))
            .andExpect(jsonPath("$.bucketAchievedCount").value(DEFAULT_BUCKET_ACHIEVED_COUNT))
            .andExpect(jsonPath("$.followers").value(DEFAULT_FOLLOWERS.toString()))
            .andExpect(jsonPath("$.following").value(DEFAULT_FOLLOWING.toString()))
            .andExpect(jsonPath("$.buckets").value(DEFAULT_BUCKETS.toString()))
            .andExpect(jsonPath("$.bucketAchieved").value(DEFAULT_BUCKET_ACHIEVED.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.blogsCount").value(DEFAULT_BLOGS_COUNT))
            .andExpect(jsonPath("$.blogs").value(DEFAULT_BLOGS.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    public void getNonExistingPeople() throws Exception {
        // Get the people
        restPeopleMockMvc.perform(get("/api/peoples/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePeople() throws Exception {
        // Initialize the database
        peopleRepository.save(people);

		int databaseSizeBeforeUpdate = peopleRepository.findAll().size();

        // Update the people
        people.setFollowersCount(UPDATED_FOLLOWERS_COUNT);
        people.setFollowingCount(UPDATED_FOLLOWING_COUNT);
        people.setBucketCount(UPDATED_BUCKET_COUNT);
        people.setBucketAchievedCount(UPDATED_BUCKET_ACHIEVED_COUNT);
        people.setFollowers(UPDATED_FOLLOWERS);
        people.setFollowing(UPDATED_FOLLOWING);
        people.setBuckets(UPDATED_BUCKETS);
        people.setBucketAchieved(UPDATED_BUCKET_ACHIEVED);
        people.setRating(UPDATED_RATING);
        people.setUserId(UPDATED_USER_ID);
        people.setBlogsCount(UPDATED_BLOGS_COUNT);
        people.setBlogs(UPDATED_BLOGS);
        people.setImageUrl(UPDATED_IMAGE_URL);
        PeopleDTO peopleDTO = peopleMapper.peopleToPeopleDTO(people);

        restPeopleMockMvc.perform(put("/api/peoples")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(peopleDTO)))
                .andExpect(status().isOk());

        // Validate the People in the database
        List<People> peoples = peopleRepository.findAll();
        assertThat(peoples).hasSize(databaseSizeBeforeUpdate);
        People testPeople = peoples.get(peoples.size() - 1);
        assertThat(testPeople.getFollowersCount()).isEqualTo(UPDATED_FOLLOWERS_COUNT);
        assertThat(testPeople.getFollowingCount()).isEqualTo(UPDATED_FOLLOWING_COUNT);
        assertThat(testPeople.getBucketCount()).isEqualTo(UPDATED_BUCKET_COUNT);
        assertThat(testPeople.getBucketAchievedCount()).isEqualTo(UPDATED_BUCKET_ACHIEVED_COUNT);
        assertThat(testPeople.getFollowers()).isEqualTo(UPDATED_FOLLOWERS);
        assertThat(testPeople.getFollowing()).isEqualTo(UPDATED_FOLLOWING);
        assertThat(testPeople.getBuckets()).isEqualTo(UPDATED_BUCKETS);
        assertThat(testPeople.getBucketAchieved()).isEqualTo(UPDATED_BUCKET_ACHIEVED);
        assertThat(testPeople.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testPeople.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPeople.getBlogsCount()).isEqualTo(UPDATED_BLOGS_COUNT);
        assertThat(testPeople.getBlogs()).isEqualTo(UPDATED_BLOGS);
        assertThat(testPeople.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    public void deletePeople() throws Exception {
        // Initialize the database
        peopleRepository.save(people);

		int databaseSizeBeforeDelete = peopleRepository.findAll().size();

        // Get the people
        restPeopleMockMvc.perform(delete("/api/peoples/{id}", people.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<People> peoples = peopleRepository.findAll();
        assertThat(peoples).hasSize(databaseSizeBeforeDelete - 1);
    }
}
