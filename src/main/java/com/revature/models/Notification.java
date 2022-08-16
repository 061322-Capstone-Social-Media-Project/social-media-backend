package com.revature.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class Notification {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	@Column(name="notification_body")
	private String notificationBody;
	@Column(name="user_id")
	private int userId;
	@Column(name="notification_type")
	@Enumerated(EnumType.STRING)
	private NotificationType type;
	@Column(name="timestamp")
	private Timestamp timeStamp;
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private NotificationStatus status;

	

}
