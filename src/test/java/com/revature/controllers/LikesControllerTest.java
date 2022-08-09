package com.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Likes;
import com.revature.services.LikesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikesController.class)
public class LikesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikesService likesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findLikesByUserIdAndPostIdTest() throws Exception {
        Likes likeExpected = new Likes();
        likeExpected.setId(1);
        likeExpected.setPostId(1);
        likeExpected.setUserId(1);

        when(likesService.findLikesByUserIdAndPostId(1, 1)).thenReturn(likeExpected);

        mockMvc.perform(
                        get("/likes/user/1/post/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(likeExpected)));
    }

    @Test
    public void userLikesPostTest() throws Exception {
        Likes likeExpected = new Likes();
        likeExpected.setId(1);
        likeExpected.setPostId(1);
        likeExpected.setUserId(1);

        when(likesService.findLikesByUserIdAndPostId(1, 1)).thenReturn(null);
        when(likesService.userLikesPost(likeExpected)).thenReturn(likeExpected);

        mockMvc.perform(
                        post("/likes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(likeExpected)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteByIdExists() throws Exception {
        mockMvc.perform(
                        delete("/likes/1"))
                .andExpect(status().isOk());
    }
	
}
