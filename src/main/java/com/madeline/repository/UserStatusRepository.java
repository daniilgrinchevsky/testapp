package com.madeline.repository;

import com.madeline.model.UserStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends CrudRepository<UserStatus, Long> {

}