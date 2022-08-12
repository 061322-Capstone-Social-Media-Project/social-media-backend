package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Notification;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	
	List<Notification> findNotificationByUserId(int id);
	

}
