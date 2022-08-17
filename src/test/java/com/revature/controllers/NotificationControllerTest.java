package com.revature.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Notification;
import com.revature.models.NotificationStatus;
import com.revature.models.NotificationType;
import com.revature.services.NotificationService;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NotificationService ns;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void makeNotificationTest() throws JsonProcessingException, Exception {
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

		Notification nExpected = new Notification();
		nExpected.setType(NotificationType.POST);
		nExpected.setStatus(NotificationStatus.UNREAD);
		nExpected.setNotificationBody("test");
		nExpected.setUserId(1);
		nExpected.setTimeStamp(timestamp1);

		when(ns.makeNotification(nExpected)).thenReturn(nExpected);

		mockMvc.perform(post("/notification").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nExpected))).andExpect(status().isOk());

	}

	@Test
	void removeNotificationTest() throws Exception {
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

		Notification nExpected = new Notification();
		nExpected.setType(NotificationType.POST);
		nExpected.setStatus(NotificationStatus.UNREAD);
		nExpected.setNotificationBody("test");
		nExpected.setUserId(1);
		nExpected.setTimeStamp(timestamp1);

		mockMvc.perform(delete("/notification/1")).andExpect(status().isOk());
	}

	@Test
	void updateNotificationSuccess() throws Exception {
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

		Optional<Notification> nExpected = Optional.ofNullable(
				new Notification(200, "test", 1, NotificationType.POST, timestamp1, NotificationStatus.UNREAD));

		when(ns.findANotificationByUserId(200)).thenReturn(nExpected);

		mockMvc.perform(
				put("/notification")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nExpected)))
		.andExpect(status().isAccepted());
	}
	
	@Test
	void updateNotificationBadRequest() throws JsonProcessingException, Exception {
		Notification nExpected = new Notification();
		nExpected.setId(1);
		nExpected.setType(NotificationType.POST);
		nExpected.setStatus(NotificationStatus.UNREAD);
		nExpected.setNotificationBody("test");
		nExpected.setUserId(1);
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
		nExpected.setTimeStamp(timestamp1);
		
		when(ns.findANotificationByUserId(1)).thenReturn(Optional.empty());
		
		mockMvc.perform(
				put("/notification")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nExpected)))
		.andExpect(status().isBadRequest());
	}

	@Test
	void getNotificationsByUserIdTest() throws Exception {
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

		Notification nExpected = new Notification();
		nExpected.setId(1);
		nExpected.setType(NotificationType.POST);
		nExpected.setStatus(NotificationStatus.UNREAD);
		nExpected.setNotificationBody("test");
		nExpected.setUserId(1);
		nExpected.setTimeStamp(timestamp1);
		List<Notification> nExpecteds = new ArrayList<>();
		nExpecteds.add(nExpected);

		when(ns.findNotificationByUserId(1)).thenReturn(nExpecteds);

		mockMvc.perform(get("/notification/user/1")).andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(nExpecteds)));

	}
}
