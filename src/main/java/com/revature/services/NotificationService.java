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
import com.revature.repositories.PostRepository;

@Service
public class NotificationService {

	private NotificationRepository nr;
	private final PostService ps;
	private final PostRepository pr;
	private final UserService us;

	public NotificationService(NotificationRepository nr, PostService ps, UserService us, PostRepository pr) {
		super();
		this.nr = nr;
		this.ps = ps;
		this.us = us;
		this.pr = pr;
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
	        	if (!comment.getComments().isEmpty()) {
	        		u = findcommentUser(comment.getComments());
	        	}
	        }
	    }
	    return u;
		
	}
	
	public Post findPostPrimary(int id) {
		boolean postCheck = true;
		 
		while(postCheck) {
			Post post = pr.findPostById(id);
			if(post.getCommentsId() ==  null) {
				return post;
			} else {
				id= post.getCommentsId();
			}  
		}
			
		return null;
	}
	
	public void commentNotification(Post post) {
		List<Post> comments = post.getComments();
		if (!comments.isEmpty()) {
			User u;
			Post postCheck = pr.findPostById(post.getId());

			u = this.findcommentUser(post.getComments());
			if(postCheck == null || postCheck.getCommentsId() == null) {
				Notification notification = new Notification();
 				notification.setNotificationBody(u.getFirstName() + " " + u.getLastName() + " commented on your Post!");
				notification.setType(NotificationType.POST);
				notification.setStatus(NotificationStatus.UNREAD);
				notification.setUserId(post.getAuthor().getId());
				Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
				notification.setTimeStamp(timestamp1);
				this.makeNotification(notification);
			} else {

 				Notification notification = new Notification();
				 
				notification.setNotificationBody(u.getFirstName() + " " + u.getLastName() + " replied to your comment!");
				notification.setType(NotificationType.POST);
				notification.setStatus(NotificationStatus.UNREAD);
				notification.setUserId(post.getAuthor().getId());
				Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
				notification.setTimeStamp(timestamp1);
				this.makeNotification(notification);		
				
				Notification notificationP = new Notification();
				notificationP.setNotificationBody(u.getFirstName() + " " + u.getLastName() + "  commented on your Post!");
				notificationP.setType(NotificationType.POST);
				notificationP.setStatus(NotificationStatus.UNREAD);
				Post postPrimaryPost  = this.findPostPrimary(post.getId());
				notificationP.setUserId(postPrimaryPost.getAuthor().getId());
				notificationP.setTimeStamp(timestamp1);
				this.makeNotification(notificationP);
			}


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
