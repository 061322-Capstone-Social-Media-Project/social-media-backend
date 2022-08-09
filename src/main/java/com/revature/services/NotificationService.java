package com.revature.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.models.Notification;

import com.revature.repositories.NotificationRepository;

@Service
public class NotificationService {
	
	private NotificationRepository nr;
	
	public NotificationService(NotificationRepository nr) {
		super();
		this.nr = nr;
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
}
