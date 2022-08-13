package com.revature.repositories;

import com.revature.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	List<Notification> findNotificationByUserIdOrderByIdDesc(int id);
	

}
