package com.revature.services;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.keys.FollowerKey;
import com.revature.models.Likes;
import com.revature.models.Notification;
import com.revature.models.NotificationStatus;
import com.revature.models.NotificationType;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.NotificationRepository;

@Service
public class NotificationService {

	private NotificationRepository nr;
	private final PostService ps;
	private final UserService us;


	public NotificationService(NotificationRepository nr, PostService ps, UserService us) {
		super();
		this.nr = nr;
		this.ps = ps;
		this.us = us;
	}

	public Notification makeNotification(Notification notification) {

		return nr.save(notification);

	}

	public void deleteNotification(int id) {
		nr.deleteById(id);
	}

	public List<Notification> findNotificationByUserId(int id) {

		return nr.findNotificationByUserId(id);

	}

	public void likenotification(Likes likes) {
		Notification notification = new Notification();
		Post post = ps.findById(likes.getPostId());
		notification.setNotificationBody("Your post was liked!");
		notification.setType(NotificationType.POST);
		notification.setStatus(NotificationStatus.NEW);
		notification.setUserId(post.getAuthor().getId());
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
		notification.setTimeStamp(timestamp1);

		this.makeNotification(notification);

	}

	public void commentNotification(Post post) {
		List<Post> comments = post.getComments();
		if (comments.size() > 0) {
			Notification notification = new Notification();
			notification.setNotificationBody("Commented on your post!");
			notification.setType(NotificationType.POST);
			notification.setStatus(NotificationStatus.NEW);
			notification.setUserId(post.getAuthor().getId());
			Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
			notification.setTimeStamp(timestamp1);
			this.makeNotification(notification);

		}

	}
	
	  
	  public void followNotification(FollowerKey fk) {
	  Optional<User> u = us.findById(fk.getFollowerId());
	  if (u.isPresent()) {
		  Notification notification = new Notification(); 
		  notification.setNotificationBody(u.get().getFirstName() + " " + u.get().getLastName() + " followed you!");
		  notification.setType(NotificationType.POST);
		  notification.setStatus(NotificationStatus.NEW);
		  notification.setUserId(fk.getFollowingId());
		  Timestamp timestamp1 = new Timestamp(System.currentTimeMillis()); notification.setTimeStamp(timestamp1);
		  this.makeNotification(notification);
	  }
	  
	  }
	 

}
