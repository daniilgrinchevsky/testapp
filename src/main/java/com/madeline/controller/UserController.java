package com.madeline.controller;

import com.madeline.exceptions.UserAlreadyExistsException;
import com.madeline.exceptions.UserDoesNotExistException;

import com.madeline.model.User;
import com.madeline.model.UserStatus;

import com.madeline.repository.UserRepository;
import com.madeline.service.UserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import static com.madeline.model.UserStatus.NULL;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusService userStatusService;

    @Autowired
    public void setUserService(UserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Long> addUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) throws Exception{
      if(user.getId() != null)
          throw new UserAlreadyExistsException(user.getId());
      User newUser = userRepository.save(user);
      Long userId = newUser.getId();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) throws Exception{
        User user = userRepository.findById(id).get();
        if (user == null)
            throw new UserDoesNotExistException(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @RequestMapping(value = "/{id}/status", method = RequestMethod.POST)
    public ResponseEntity<String> updateUserStatus(@PathVariable("id") Long id, UserStatus userStatus) throws Exception{
        User user = userRepository.findById(id).get();
        if (user == null)
            throw new UserDoesNotExistException(id);
        UserStatus oldUserStatus = userStatusService.findById(id).orElse(new UserStatus(id, NULL));
        UserStatus newUserStatus = userStatusService.changeStatus(id, userStatus);
        String response = String.format("Id - %d, previous status - %s, current status - %s" ,id.toString() + oldUserStatus.getStatus() + newUserStatus.getStatus());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
