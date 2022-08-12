package com.revature.services;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.models.Likes;
import com.revature.models.Notification;
import com.revature.models.NotificationType;
import com.revature.models.Post;
import com.revature.repositories.NotificationRepository;

@Service
public class NotificationService {
	
	private NotificationRepository nr;
	private final PostService ps;
	
	public NotificationService(NotificationRepository nr,PostService ps) {
		super();
		this.nr = nr;
		this.ps = ps;
	}

	public Notification makeNotification(Notification notification) {
		
		
		
		return nr.save(notification);
		
	}
	public void deleteNotification(int id) {
		nr.deleteById(id);
	}
	public List<Notification> findNotificationByUserId(int id){
		
		return nr.findNotificationByUserId(id);
		
		
	}
	public void likenotification(Likes likes) {
		Notification notification = new Notification();
		Post post = ps.findById(likes.getPostId());
		notification.setNotificationBody("Your post was liked!");
		notification.setType(NotificationType.POST);
		notification.setUserId(post.getAuthor().getId());
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
		notification.setTimeStamp(timestamp1);
		this.makeNotification(notification);
		
	}
	
	public void commentNotification(Post post) {
		List<Post> comments =post.getComments(); 
		if (comments.size() > 0){
			Notification notification = new Notification();
			notification.setNotificationBody("Commented on your post!");
			notification.setType(NotificationType.POST);
			notification.setUserId(post.getAuthor().getId());
			Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
			notification.setTimeStamp(timestamp1);
			this.makeNotification(notification);
			
			}

		
	}
	
	/*
	 * public void followNotification(Follow Follower) { Notification notification =
	 * new Notification(); Post post = ps.findById(likes.getPostId());
	 * notification.setNotificationBody(first name + last name + "Followed you!");
	 * notification.setType(NotificationType.POST);
	 * notification.setUserId(post.getAuthor().getId()); Timestamp timestamp1 = new
	 * Timestamp(System.currentTimeMillis()); notification.setTimeStamp(timestamp1);
	 * this.makeNotification(notification);
	 * 
	 * }
	 */
	
	
}
