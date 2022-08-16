package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Notification;
import com.revature.models.NotificationStatus;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	List<Notification> findNotificationByUserIdOrderByIdDesc(int id);
	List<Notification> findNotificationByUserIdAndStatusInOrderByIdDesc(int id, List<NotificationStatus> status);


}
