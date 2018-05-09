package com.madeline.repository;

import com.madeline.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findById(Long id);
}
