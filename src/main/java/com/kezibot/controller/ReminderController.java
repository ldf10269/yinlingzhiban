package com.kezibot.controller;

import com.kezibot.model.Reminder;
import com.kezibot.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {
    @Autowired
    private ReminderService reminderService;

    @PostMapping("/create")
    public ResponseEntity<Reminder> createReminder(
        @RequestParam Long userId,
        @RequestParam String title,
        @RequestParam(required = false) String description,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate
    ) {
        try {
            Reminder reminder = reminderService.createReminder(userId, title, description, dueDate);
            return ResponseEntity.ok(reminder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<Reminder>> getRemindersByUser(@RequestParam Long userId) {
        try {
            List<Reminder> reminders = reminderService.findRemindersByUser(userId);
            return ResponseEntity.ok(reminders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/incomplete")
    public ResponseEntity<List<Reminder>> getIncompleteReminders(@RequestParam Long userId) {
        try {
            List<Reminder> reminders = reminderService.findIncompleteReminders(userId);
            return ResponseEntity.ok(reminders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/status")
    public ResponseEntity<Reminder> updateReminderStatus(
        @RequestParam Long reminderId,
        @RequestParam boolean isCompleted
    ) {
        try {
            Reminder reminder = reminderService.updateReminderStatus(reminderId, isCompleted);
            return ResponseEntity.ok(reminder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Reminder>> getUpcomingReminders(@RequestParam int hours) {
        try {
            List<Reminder> reminders = reminderService.findRemindersDueInNextHours(hours);
            return ResponseEntity.ok(reminders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
