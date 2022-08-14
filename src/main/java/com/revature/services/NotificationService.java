package com.revature.services;

import java.sql.Timestamp;
import java.util.ArrayList;
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

 		List<NotificationStatus> status = new ArrayList<>();
		status.add(NotificationStatus.READ);
		status.add(NotificationStatus.UNREAD);
    	return nr.findNotificationByUserIdAndStatusInOrderByIdDesc(id, status);
	}

	public Optional<Notification> findANotificationByUserId(int id) {

		return nr.findById(id);

	}
	
	public void likenotification(Likes likes) {
		Notification notification = new Notification();
		Post post = ps.findById(likes.getPostId());
		Optional<User> u = us.findById(likes.getUserId());
		if(u.isPresent()) {
			notification.setNotificationBody(u.get().getFirstName() + " " + u.get().getLastName() + " liked your post!");
			notification.setType(NotificationType.POST);
			notification.setStatus(NotificationStatus.UNREAD);
			notification.setUserId(post.getAuthor().getId());
			Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
			notification.setTimeStamp(timestamp1);

			this.makeNotification(notification);
			
		}
	}
	
	public User  findcommentUser(List<Post> comments) {
		User u = new User();
	    for (Post comment : comments) {
	        if (comment.getId() == 0) {
	        	u =  comment.getAuthor();
	        	break;
	        } else {
	        	if (comment.getComments().size() > 0) {
	        		u = findcommentUser(comment.getComments());
	        	}
	        }
	    }
	    return u;
		
	}
	
	public void commentNotification(Post post) {
		List<Post> comments = post.getComments();
		if (comments.size() > 0) {
			User u = new User();
			Notification notification = new Notification();
			u = this.findcommentUser(post.getComments());
			notification.setNotificationBody(u.getFirstName() + " " + u.getLastName() + " commented on your Post!");
			notification.setType(NotificationType.POST);
			notification.setStatus(NotificationStatus.UNREAD);
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
		  notification.setStatus(NotificationStatus.UNREAD);
		  notification.setUserId(fk.getFollowingId());
		  Timestamp timestamp1 = new Timestamp(System.currentTimeMillis()); notification.setTimeStamp(timestamp1);
		  this.makeNotification(notification);
	  }
	  
	  }
	 

}
