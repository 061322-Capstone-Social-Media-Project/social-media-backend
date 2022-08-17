package com.revature.controllers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.LikeNotFoundException;
import com.revature.models.Likes;
import com.revature.services.LikesService;
import com.revature.services.NotificationService;

@WebMvcTest(LikesController.class)
class LikesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService ns;

    @MockBean
    private LikesService likesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findLikesByUserIdAndPostIdTest() throws Exception {
        Likes likeExpected = new Likes();
        likeExpected.setId(1);
        likeExpected.setPostId(1);
        likeExpected.setUserId(1);

        when(likesService.findLikesByUserIdAndPostId(1, 1)).thenReturn(likeExpected);

        mockMvc.perform(get("/likes/user/1/post/1")).andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(likeExpected)));
    }

    @Test
    void userLikesPostSuccess() throws Exception {
        Likes likeExpected = new Likes();
        likeExpected.setId(1);
        likeExpected.setPostId(1);
        likeExpected.setUserId(1);

        when(likesService.findLikesByUserIdAndPostId(1, 1)).thenReturn(null);
        when(likesService.userLikesPost(likeExpected)).thenReturn(likeExpected);

        mockMvc.perform(post("/likes").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(likeExpected))).andExpect(status().isOk());
    }
    
    @Test
    void userLikesPostBadRequest() throws Exception {
        Likes likeFound = new Likes();
        likeFound.setId(1);
        likeFound.setPostId(1);
        likeFound.setUserId(1);

        when(likesService.findLikesByUserIdAndPostId(1, 1)).thenReturn(likeFound);

        mockMvc.perform(
        		post("/likes")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(objectMapper.writeValueAsString(likeFound)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void removeLikeExists() throws Exception {
        mockMvc.perform(
                delete("/likes/1"))
            .andExpect(status().isOk());
        
        verify(likesService).removeLike(1);
    }
    
    @Test
    void removeLikeBadRequest() throws Exception {
    	
    	doThrow(new LikeNotFoundException()).when(likesService).removeLike(1);
    	
    	mockMvc.perform(
    			delete("/likes/1"))
    	.andExpect(status().isBadRequest());
    }
    
    @Test
    void countLikes() throws JsonProcessingException, Exception {
    	
    	when(likesService.countLikesByPostId(1)).thenReturn(5l);
    	
    	mockMvc.perform(
    			get("/likes/count/post/1"))
    	.andExpect(status().isOk())
    	.andExpect(content().json(objectMapper.writeValueAsString(5l)));
    }

}
