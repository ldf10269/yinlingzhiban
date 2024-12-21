package com.kezibot.repository;

import com.kezibot.model.Reminder;
import com.kezibot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUser(User user);
    List<Reminder> findByUserAndIsCompletedFalse(User user);
    List<Reminder> findByDueDateBetween(LocalDateTime start, LocalDateTime end);
}
