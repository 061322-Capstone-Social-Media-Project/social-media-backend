package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.keys.FollowerKey;
import com.revature.models.Likes;
import com.revature.models.Notification;
import com.revature.models.NotificationStatus;
import com.revature.models.NotificationType;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.NotificationRepository;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;

@SpringBootTest
class NotificationServiceTests {

	@MockBean
	NotificationRepository nRepository;

	@Autowired
	NotificationService nsService;

	@MockBean
	private PostRepository postRepository;

	@Autowired
	PostService postService;

	@MockBean
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Test
	void makeNotificationTest() {
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

		Notification nExpected = new Notification();
		nExpected.setType(NotificationType.POST);
		nExpected.setStatus(NotificationStatus.UNREAD);
		nExpected.setNotificationBody("test");
		nExpected.setUserId(1);
		nExpected.setTimeStamp(timestamp1);

		Mockito.when(nRepository.save(nExpected)).thenReturn(nExpected);
		Notification actualNotification = nsService.makeNotification(nExpected);

		assertEquals(actualNotification, nExpected);
	}

	@Test
	void findNotificationByUserIdTest() {
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

		Notification nExpected = new Notification();
		nExpected.setType(NotificationType.POST);
		nExpected.setStatus(NotificationStatus.UNREAD);
		nExpected.setNotificationBody("test");
		nExpected.setUserId(1);
		nExpected.setTimeStamp(timestamp1);
		List<Notification> nExpectedList = new ArrayList<>();
		nExpectedList.add(nExpected);

		List<NotificationStatus> status = new ArrayList<>();
		status.add(NotificationStatus.READ);
		status.add(NotificationStatus.UNREAD);
		Mockito.when(nRepository.findNotificationByUserIdAndStatusInOrderByIdDesc(1, status)).thenReturn(nExpectedList);
		List<Notification> actualNotifications = nsService.findNotificationByUserId(1);

		assertEquals(actualNotifications, nExpectedList);

	}

	@Test
	void deleteNotificationTest() {
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

		Notification nExpected = new Notification();
		nExpected.setId(200);
		nExpected.setType(NotificationType.POST);
		nExpected.setStatus(NotificationStatus.UNREAD);
		nExpected.setNotificationBody("test");
		nExpected.setUserId(1);
		nExpected.setTimeStamp(timestamp1);

		nsService.deleteNotification(200);
		
		Mockito.verify(nRepository).deleteById(200);

	}

	@Test
	void likeNotificationTest() {
		User u = new User();
		u.setId(1);
		u.setEmail("gian@gmail.com");
		u.setPassword("pass123");
		u.setFirstName("Gianmarco");
		u.setLastName("Barone");

		Post post = new Post();
		post.setId(1);
		post.setAuthor(u);
		post.setImageUrl("test");
		post.setText("test");
		Likes likeExpected = new Likes();
		likeExpected.setId(1);
		likeExpected.setPostId(1);
		likeExpected.setUserId(1);

		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

		Notification nExpected = new Notification();
		nExpected.setType(NotificationType.POST);
		nExpected.setStatus(NotificationStatus.UNREAD);
		nExpected.setNotificationBody("test");
		nExpected.setUserId(1);
		nExpected.setTimeStamp(timestamp1);
		Mockito.when(userRepository.findUserById(1)).thenReturn(u);

		Mockito.when(postRepository.findPostById(1)).thenReturn(post);

		postService.findById(1);
		Mockito.when(nsService.makeNotification(nExpected)).thenReturn(nExpected);
		nsService.likeNotification(likeExpected);

	}

	@Test
	void findANotificationByUserIdTest() {
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

		Optional<Notification> nExpected = Optional.ofNullable(
				new Notification(1, "test", 1, NotificationType.POST, timestamp1, NotificationStatus.UNREAD));

		Mockito.when(nRepository.findById(1)).thenReturn(nExpected);
		Optional<Notification> actualNotification = nsService.findANotificationByUserId(1);

		assertEquals(actualNotification, nExpected);
	}

	@Test
	void followNotificationTest() {
		Optional<User> uExpected = Optional.ofNullable(
				new User(1, "gian@gmail.com", "pass123", "Gianmarco", "Barone", null, null, null, null, null));
		Mockito.when(userRepository.findById(1)).thenReturn(uExpected);

		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

		Notification nExpected = new Notification();
		nExpected.setId(200);
		nExpected.setType(NotificationType.POST);
		nExpected.setStatus(NotificationStatus.UNREAD);
		nExpected.setNotificationBody("test");
		nExpected.setUserId(1);
		nExpected.setTimeStamp(timestamp1);

		FollowerKey fk = new FollowerKey(1, 1);
		Mockito.when(nsService.makeNotification(nExpected)).thenReturn(nExpected);
		nsService.followNotification(fk);

	}

	@Test
	void commentNotificationTest() {
		User u = new User(1, "gian@gmail.com", "pass123", "Gianmarco", "Barone", null, null, null, null, null);
		Post postComment = new Post();
		postComment.setAuthor(u);
		postComment.setImageUrl("test");
		postComment.setText("test");
		postComment.setCommentsId(1);

		List<Post> postComments = new ArrayList<>();
		postComments.add(postComment);
		Post postExpected = new Post(1, "test", "test", null, postComments, u);

		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

		Notification nExpected = new Notification();
		nExpected.setId(200);
		nExpected.setType(NotificationType.POST);
		nExpected.setStatus(NotificationStatus.UNREAD);
		nExpected.setNotificationBody("test");
		nExpected.setUserId(1);
		nExpected.setTimeStamp(timestamp1);

		Mockito.when(postRepository.findPostById(1)).thenReturn(postExpected);

		Mockito.when(nsService.makeNotification(nExpected)).thenReturn(nExpected);
		nsService.commentNotification(postExpected);

		User uSecond = new User(1, "gian@gmail.com", "pass123", "Gianmarco", "Barone", null, null, null, null, null);

		List<Post> commentsSecond = new ArrayList<>();

		Post commentSecond = new Post();
		commentSecond.setAuthor(uSecond);
		commentSecond.setImageUrl("test");
		commentSecond.setText("test");
		commentSecond.setCommentsId(4);
		Post comment1Second = new Post(5, "test", "test", 2, null, uSecond);

		commentsSecond.add(commentSecond);
		commentsSecond.add(comment1Second);

		Post postCommentSecond = new Post(4, "test", "test", 3, commentsSecond, uSecond);

		List<Post> postCommentSeconds = new ArrayList<>();
		postCommentSeconds.add(postCommentSecond);
		Post postSubSecond = new Post(3, "test", "test", 1, postCommentSeconds, uSecond);
		List<Post> postSecondSubs = new ArrayList<>();
		postSecondSubs.add(postSubSecond);
		Post postExpectedSecond = new Post(1, "test", "test", null, postSecondSubs, uSecond);

		Mockito.when(postRepository.findPostById(3)).thenReturn(postSubSecond);
		Mockito.when(postService.findPostPrimary(3)).thenReturn(postExpectedSecond);

		Mockito.when(nsService.makeNotification(nExpected)).thenReturn(nExpected);
		nsService.commentNotification(postSubSecond);
	}

}
