package com.example.demo.service;

import com.example.demo.exception.NotFoundException;

import com.example.demo.Controller.Models.User;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.Repo.UserRepo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();

    }

    public User getUserById(long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: "+ id));
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public User updateUser(long id, User user)  {
        User existingUser = getUserById(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAge(user.getAge());
        existingUser.setOccupation(user.getOccupation());
        return userRepo.save(existingUser);
    }

    public void deleteUser(long id){
        User user = getUserById(id);
        userRepo.delete(user);
    }

}
