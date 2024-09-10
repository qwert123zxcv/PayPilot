package com.basics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.basics.model.ReminderSetting;

@Repository
public interface ReminderSettingRepository extends JpaRepository<ReminderSetting, Long> {
}
