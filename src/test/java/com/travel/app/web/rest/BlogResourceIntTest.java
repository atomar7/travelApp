package com.travel.app.web.rest;

import com.travel.app.Application;
import com.travel.app.domain.Blog;
import com.travel.app.repository.BlogRepository;
import com.travel.app.service.BlogService;
import com.travel.app.web.rest.dto.BlogDTO;
import com.travel.app.web.rest.mapper.BlogMapper;

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
 * Test class for the BlogResource REST controller.
 *
 * @see BlogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BlogResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_BLOG = "AAAAA";
    private static final String UPDATED_BLOG = "BBBBB";
    private static final String[] DEFAULT_IMAGE_URLS = {"AAAAA"};
    private static final String[] UPDATED_IMAGE_URLS = {"BBBBB"};

    @Inject
    private BlogRepository blogRepository;

    @Inject
    private BlogMapper blogMapper;

    @Inject
    private BlogService blogService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBlogMockMvc;

    private Blog blog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BlogResource blogResource = new BlogResource();
        ReflectionTestUtils.setField(blogResource, "blogService", blogService);
        ReflectionTestUtils.setField(blogResource, "blogMapper", blogMapper);
        this.restBlogMockMvc = MockMvcBuilders.standaloneSetup(blogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        blogRepository.deleteAll();
        blog = new Blog();
        blog.setTitle(DEFAULT_TITLE);
        blog.setBlog(DEFAULT_BLOG);
        blog.setImageUrls(DEFAULT_IMAGE_URLS);
    }

    @Test
    public void createBlog() throws Exception {
        int databaseSizeBeforeCreate = blogRepository.findAll().size();

        // Create the Blog
        BlogDTO blogDTO = blogMapper.blogToBlogDTO(blog);

        restBlogMockMvc.perform(post("/api/blogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(blogDTO)))
                .andExpect(status().isCreated());

        // Validate the Blog in the database
        List<Blog> blogs = blogRepository.findAll();
        assertThat(blogs).hasSize(databaseSizeBeforeCreate + 1);
        Blog testBlog = blogs.get(blogs.size() - 1);
        assertThat(testBlog.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBlog.getBlog()).isEqualTo(DEFAULT_BLOG);
        assertThat(testBlog.getImageUrls()).isEqualTo(DEFAULT_IMAGE_URLS);
    }

    @Test
    public void getAllBlogs() throws Exception {
        // Initialize the database
        blogRepository.save(blog);

        // Get all the blogs
        restBlogMockMvc.perform(get("/api/blogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(blog.getId())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].blog").value(hasItem(DEFAULT_BLOG.toString())))
                .andExpect(jsonPath("$.[*].imageUrls").value(hasItem(DEFAULT_IMAGE_URLS.toString())));
    }

    @Test
    public void getBlog() throws Exception {
        // Initialize the database
        blogRepository.save(blog);

        // Get the blog
        restBlogMockMvc.perform(get("/api/blogs/{id}", blog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(blog.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.blog").value(DEFAULT_BLOG.toString()))
            .andExpect(jsonPath("$.imageUrls").value(DEFAULT_IMAGE_URLS.toString()));
    }

    @Test
    public void getNonExistingBlog() throws Exception {
        // Get the blog
        restBlogMockMvc.perform(get("/api/blogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBlog() throws Exception {
        // Initialize the database
        blogRepository.save(blog);

		int databaseSizeBeforeUpdate = blogRepository.findAll().size();

        // Update the blog
        blog.setTitle(UPDATED_TITLE);
        blog.setBlog(UPDATED_BLOG);
        blog.setImageUrls(UPDATED_IMAGE_URLS);
        BlogDTO blogDTO = blogMapper.blogToBlogDTO(blog);

        restBlogMockMvc.perform(put("/api/blogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(blogDTO)))
                .andExpect(status().isOk());

        // Validate the Blog in the database
        List<Blog> blogs = blogRepository.findAll();
        assertThat(blogs).hasSize(databaseSizeBeforeUpdate);
        Blog testBlog = blogs.get(blogs.size() - 1);
        assertThat(testBlog.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBlog.getBlog()).isEqualTo(UPDATED_BLOG);
        assertThat(testBlog.getImageUrls()).isEqualTo(UPDATED_IMAGE_URLS);
    }

    @Test
    public void deleteBlog() throws Exception {
        // Initialize the database
        blogRepository.save(blog);

		int databaseSizeBeforeDelete = blogRepository.findAll().size();

        // Get the blog
        restBlogMockMvc.perform(delete("/api/blogs/{id}", blog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Blog> blogs = blogRepository.findAll();
        assertThat(blogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
