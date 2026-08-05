package com.infosys.finbank.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.infosys.finbank.notification.model.Notification;
import com.infosys.finbank.notification.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationRepository repository;

    public NotificationController(NotificationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(@RequestBody Map<String, String> payload) {
        String account = payload.getOrDefault("account", "ACC1001");
        String message = payload.getOrDefault("message", "Payment processed successfully.");

        Notification notification = new Notification(
            account,
            message,
            "SENT",
            LocalDateTime.now()
        );

        Notification saved = repository.save(notification);
        System.out.println("--> [SMS SENT]: " + message);
        return ResponseEntity.ok(saved);
    }
}