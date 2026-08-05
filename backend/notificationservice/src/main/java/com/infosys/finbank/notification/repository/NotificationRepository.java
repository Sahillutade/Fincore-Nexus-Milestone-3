package com.infosys.finbank.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.infosys.finbank.notification.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}