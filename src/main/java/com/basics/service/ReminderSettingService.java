package com.basics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.basics.model.ReminderSetting;
import com.basics.repository.ReminderSettingRepository;

import java.util.Optional;

@Service
public class ReminderSettingService {

    @Autowired
    private ReminderSettingRepository reminderSettingRepository;

    public ReminderSetting saveReminderSetting(ReminderSetting reminderSetting) {
        return reminderSettingRepository.save(reminderSetting);
    }

    public Optional<ReminderSetting> getReminderSetting(Long id) {
        return reminderSettingRepository.findById(id);
    }

    public void deleteReminderSetting(Long id) {
        reminderSettingRepository.deleteById(id);
    }
}
