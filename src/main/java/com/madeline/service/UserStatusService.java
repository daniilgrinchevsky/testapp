package com.madeline.service;

import com.madeline.model.UserStatus;

import java.util.Optional;

public interface UserStatusService {

    UserStatus changeStatus(Long id, UserStatus newUserStatus);

    Optional<UserStatus> findById(Long id);

    UserStatus save(UserStatus userStatus);
}