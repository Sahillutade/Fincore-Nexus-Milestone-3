package com.infosys.finbank.notification.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notifId;

    private String recipientAccount;
    private String message;
    private String deliveryStatus; // SENT, PENDING
    private LocalDateTime timestamp;

    public Notification() {}

    public Notification(String recipientAccount, String message, String deliveryStatus, LocalDateTime timestamp) {
        this.recipientAccount = recipientAccount;
        this.message = message;
        this.deliveryStatus = deliveryStatus;
        this.timestamp = timestamp;
    }

    public Long getNotifId() { return notifId; }
    public void setNotifId(Long notifId) { this.notifId = notifId; }

    public String getRecipientAccount() { return recipientAccount; }
    public void setRecipientAccount(String recipientAccount) { this.recipientAccount = recipientAccount; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getDeliveryStatus() { return deliveryStatus; }
    public void setDeliveryStatus(String deliveryStatus) { this.deliveryStatus = deliveryStatus; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}