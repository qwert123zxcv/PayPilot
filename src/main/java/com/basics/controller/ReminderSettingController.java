package com.basics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.basics.model.ReminderSetting;
import com.basics.service.ReminderSettingService;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reminder-settings")
public class ReminderSettingController {

    @Autowired
    private ReminderSettingService reminderSettingService;

    @PostMapping
    public ReminderSetting addReminderSetting(@RequestBody ReminderSetting reminderSetting) {
        return reminderSettingService.saveReminderSetting(reminderSetting);
    }

    @GetMapping("/{id}")
    public Optional<ReminderSetting> getReminderSettingById(@PathVariable Long id) {
        return reminderSettingService.getReminderSetting(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReminderSetting(@PathVariable Long id) {
        reminderSettingService.deleteReminderSetting(id);
    }
}
