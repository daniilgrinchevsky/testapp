package com.madeline.service;

import com.madeline.model.UserStatus;
import com.madeline.repository.UserStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.madeline.model.UserStatus.AWAY;
import static com.madeline.model.UserStatus.ONLINE;
@Service
public class UserStatusServiceImpl implements UserStatusService {

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Override
    public UserStatus changeStatus(Long id, UserStatus newUserStatus) {
        newUserStatus.setId(id);
        changeDelay(newUserStatus);
        return userStatusRepository.save(newUserStatus);
    }

    private Runnable changeOnline(UserStatus userStatus) {
        return () -> {
            Optional<UserStatus> currentStatus = userStatusRepository.findById(userStatus.getId());
            if (currentStatus != null && currentStatus.get().getChangingTime().isEqual(userStatus.getChangingTime())) {
                userStatus.setStatus(AWAY);
                userStatusRepository.save(userStatus);
            }
        };
    }

    private void changeDelay(UserStatus userStatus) {
        if (userStatus.getStatus() == ONLINE) {
            Date initDelayDate = new Date(System.currentTimeMillis() + userStatus.getDurationSeconds() * 1000);
            threadPoolTaskScheduler.schedule(changeOnline(userStatus), initDelayDate);
        }
    }

    @Override
    public Optional<UserStatus> findById(Long id) {
        return userStatusRepository.findById(id);
    }

    @Override
    public UserStatus save(UserStatus userStatus) {
        return userStatusRepository.save(userStatus);
    }
}