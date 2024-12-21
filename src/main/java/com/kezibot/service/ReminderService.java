package com.kezibot.service;

import com.kezibot.model.Reminder;
import com.kezibot.model.User;
import com.kezibot.repository.ReminderRepository;
import com.kezibot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReminderService {
    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserRepository userRepository;

    public Reminder createReminder(Long userId, String title, String description, LocalDateTime dueDate) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Reminder reminder = new Reminder();
        reminder.setUser(user);
        reminder.setTitle(title);
        reminder.setDescription(description);
        reminder.setDueDate(dueDate);
        reminder.setIsCompleted(false);

        return reminderRepository.save(reminder);
    }

    public List<Reminder> findRemindersByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return reminderRepository.findByUser(user);
    }

    public List<Reminder> findIncompleteReminders(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return reminderRepository.findByUserAndIsCompletedFalse(user);
    }

    public Reminder updateReminderStatus(Long reminderId, boolean isCompleted) {
        Reminder reminder = reminderRepository.findById(reminderId)
            .orElseThrow(() -> new RuntimeException("Reminder not found"));

        reminder.setIsCompleted(isCompleted);
        return reminderRepository.save(reminder);
    }

    public List<Reminder> findRemindersDueInNextHours(int hours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureTime = now.plusHours(hours);
        return reminderRepository.findByDueDateBetween(now, futureTime);
    }
}
