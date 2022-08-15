package com.revature.controllers;



import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.LikeNotFoundException;
import com.revature.models.Likes;
import com.revature.models.Notification;
import com.revature.models.NotificationStatus;
import com.revature.services.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	private final NotificationService ns;
	
	public NotificationController(NotificationService ns) {
		super();
		this.ns = ns;
	}
	
	@PostMapping
	public ResponseEntity<Notification> makeNotification(@RequestBody Notification notification){
		Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
		notification.setTimeStamp(timestamp1);

		 
		return ResponseEntity.ok(this.ns.makeNotification(notification));
	}
	@DeleteMapping("/{id}")
	public  ResponseEntity<Boolean> removeNotification(@PathVariable("id") int id){
	
			ns.deleteNotification(id);
		
		return ResponseEntity.ok(true);
	}
	
	@GetMapping("/user/{user_id}")
	public  ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable("user_id") int id){
		
	
	return ResponseEntity.ok(ns.findNotificationByUserId(id));
	}
	
	@PutMapping
	public ResponseEntity<Boolean> updateNotification(@RequestBody Notification notification){
		Optional<Notification> n = ns.findANotificationByUserId(notification.getId());
		if(n.isPresent()) {
			n.get().setStatus(NotificationStatus.READ);
			ns.makeNotification(notification);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
			
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
