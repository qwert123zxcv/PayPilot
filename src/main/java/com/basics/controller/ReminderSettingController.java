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

    @GetMapping("/{reminderId}")
    public Optional<ReminderSetting> getReminderSettingById(@PathVariable String reminderId) {
        return reminderSettingService.getReminderSetting(reminderId);
    }

    @DeleteMapping("/{reminderId}")
    public void deleteReminderSetting(@PathVariable String reminderId) {
        reminderSettingService.deleteReminderSetting(reminderId);
    }
}
