package com.travel.app.web.rest;

import com.travel.app.Application;
import com.travel.app.domain.Bucket;
import com.travel.app.repository.BucketRepository;
import com.travel.app.service.BucketService;
import com.travel.app.web.rest.dto.BucketDTO;
import com.travel.app.web.rest.mapper.BucketMapper;

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
 * Test class for the BucketResource REST controller.
 *
 * @see BucketResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BucketResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String[] DEFAULT_IMAGE_URLS = {"AAAAA"};
    private static final String[] UPDATED_IMAGE_URLS = {"BBBBB"};

    private static final Integer DEFAULT_PEOPLE_COUNT = 1;
    private static final Integer UPDATED_PEOPLE_COUNT = 2;
    private static final String[] DEFAULT_PEOPLE = {"AAAAA"};
    private static final String[] UPDATED_PEOPLE = {"BBBBB"};

    private static final Integer DEFAULT_ACHIEVED_COUNT = 1;
    private static final Integer UPDATED_ACHIEVED_COUNT = 2;
    private static final String[] DEFAULT_ACHIEVE = {"AAAAA"};
    private static final String[] UPDATED_ACHIEVE = {"BBBBB"};
    private static final String[] DEFAULT_EXPERIENCES = {"AAAAA"};
    private static final String[] UPDATED_EXPERIENCES = {"BBBBB"};

    @Inject
    private BucketRepository bucketRepository;

    @Inject
    private BucketMapper bucketMapper;

    @Inject
    private BucketService bucketService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBucketMockMvc;

    private Bucket bucket;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BucketResource bucketResource = new BucketResource();
        ReflectionTestUtils.setField(bucketResource, "bucketService", bucketService);
        ReflectionTestUtils.setField(bucketResource, "bucketMapper", bucketMapper);
        this.restBucketMockMvc = MockMvcBuilders.standaloneSetup(bucketResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bucketRepository.deleteAll();
        bucket = new Bucket();
        bucket.setTitle(DEFAULT_TITLE);
        bucket.setDescription(DEFAULT_DESCRIPTION);
        bucket.setImageUrls(DEFAULT_IMAGE_URLS);
        bucket.setPeopleCount(DEFAULT_PEOPLE_COUNT);
        bucket.setPeople(DEFAULT_PEOPLE);
        bucket.setAchievedCount(DEFAULT_ACHIEVED_COUNT);
        bucket.setAchieve(DEFAULT_ACHIEVE);
        bucket.setExperiences(DEFAULT_EXPERIENCES);
    }

    @Test
    public void createBucket() throws Exception {
        int databaseSizeBeforeCreate = bucketRepository.findAll().size();

        // Create the Bucket
        BucketDTO bucketDTO = bucketMapper.bucketToBucketDTO(bucket);

        restBucketMockMvc.perform(post("/api/buckets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bucketDTO)))
                .andExpect(status().isCreated());

        // Validate the Bucket in the database
        List<Bucket> buckets = bucketRepository.findAll();
        assertThat(buckets).hasSize(databaseSizeBeforeCreate + 1);
        Bucket testBucket = buckets.get(buckets.size() - 1);
        assertThat(testBucket.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBucket.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBucket.getImageUrls()).isEqualTo(DEFAULT_IMAGE_URLS);
        assertThat(testBucket.getPeopleCount()).isEqualTo(DEFAULT_PEOPLE_COUNT);
        assertThat(testBucket.getPeople()).isEqualTo(DEFAULT_PEOPLE);
        assertThat(testBucket.getAchievedCount()).isEqualTo(DEFAULT_ACHIEVED_COUNT);
        assertThat(testBucket.getAchieve()).isEqualTo(DEFAULT_ACHIEVE);
        assertThat(testBucket.getExperiences()).isEqualTo(DEFAULT_EXPERIENCES);
    }

    @Test
    public void getAllBuckets() throws Exception {
        // Initialize the database
        bucketRepository.save(bucket);

        // Get all the buckets
        restBucketMockMvc.perform(get("/api/buckets?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bucket.getId())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].imageUrls").value(hasItem(DEFAULT_IMAGE_URLS.toString())))
                .andExpect(jsonPath("$.[*].peopleCount").value(hasItem(DEFAULT_PEOPLE_COUNT)))
                .andExpect(jsonPath("$.[*].people").value(hasItem(DEFAULT_PEOPLE.toString())))
                .andExpect(jsonPath("$.[*].achievedCount").value(hasItem(DEFAULT_ACHIEVED_COUNT)))
                .andExpect(jsonPath("$.[*].achieve").value(hasItem(DEFAULT_ACHIEVE.toString())))
                .andExpect(jsonPath("$.[*].experiences").value(hasItem(DEFAULT_EXPERIENCES.toString())));
    }

    @Test
    public void getBucket() throws Exception {
        // Initialize the database
        bucketRepository.save(bucket);

        // Get the bucket
        restBucketMockMvc.perform(get("/api/buckets/{id}", bucket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bucket.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.imageUrls").value(DEFAULT_IMAGE_URLS.toString()))
            .andExpect(jsonPath("$.peopleCount").value(DEFAULT_PEOPLE_COUNT))
            .andExpect(jsonPath("$.people").value(DEFAULT_PEOPLE.toString()))
            .andExpect(jsonPath("$.achievedCount").value(DEFAULT_ACHIEVED_COUNT))
            .andExpect(jsonPath("$.achieve").value(DEFAULT_ACHIEVE.toString()))
            .andExpect(jsonPath("$.experiences").value(DEFAULT_EXPERIENCES.toString()));
    }

    @Test
    public void getNonExistingBucket() throws Exception {
        // Get the bucket
        restBucketMockMvc.perform(get("/api/buckets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBucket() throws Exception {
        // Initialize the database
        bucketRepository.save(bucket);

		int databaseSizeBeforeUpdate = bucketRepository.findAll().size();

        // Update the bucket
        bucket.setTitle(UPDATED_TITLE);
        bucket.setDescription(UPDATED_DESCRIPTION);
        bucket.setImageUrls(UPDATED_IMAGE_URLS);
        bucket.setPeopleCount(UPDATED_PEOPLE_COUNT);
        bucket.setPeople(UPDATED_PEOPLE);
        bucket.setAchievedCount(UPDATED_ACHIEVED_COUNT);
        bucket.setAchieve(UPDATED_ACHIEVE);
        bucket.setExperiences(UPDATED_EXPERIENCES);
        BucketDTO bucketDTO = bucketMapper.bucketToBucketDTO(bucket);

        restBucketMockMvc.perform(put("/api/buckets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bucketDTO)))
                .andExpect(status().isOk());

        // Validate the Bucket in the database
        List<Bucket> buckets = bucketRepository.findAll();
        assertThat(buckets).hasSize(databaseSizeBeforeUpdate);
        Bucket testBucket = buckets.get(buckets.size() - 1);
        assertThat(testBucket.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBucket.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBucket.getImageUrls()).isEqualTo(UPDATED_IMAGE_URLS);
        assertThat(testBucket.getPeopleCount()).isEqualTo(UPDATED_PEOPLE_COUNT);
        assertThat(testBucket.getPeople()).isEqualTo(UPDATED_PEOPLE);
        assertThat(testBucket.getAchievedCount()).isEqualTo(UPDATED_ACHIEVED_COUNT);
        assertThat(testBucket.getAchieve()).isEqualTo(UPDATED_ACHIEVE);
        assertThat(testBucket.getExperiences()).isEqualTo(UPDATED_EXPERIENCES);
    }

    @Test
    public void deleteBucket() throws Exception {
        // Initialize the database
        bucketRepository.save(bucket);

		int databaseSizeBeforeDelete = bucketRepository.findAll().size();

        // Get the bucket
        restBucketMockMvc.perform(delete("/api/buckets/{id}", bucket.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bucket> buckets = bucketRepository.findAll();
        assertThat(buckets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
