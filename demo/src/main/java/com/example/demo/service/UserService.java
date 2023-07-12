package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.models.User;
import com.example.demo.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();

    }

    public User getUserById(long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public User updateUser(long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAge(user.getAge());
        existingUser.setOccupation(user.getOccupation());
        return userRepo.save(existingUser);
    }

    public void deleteUser(long id) {
        User user = getUserById(id);
        userRepo.delete(user);
    }

    public void deleteAllUsers(){
        userRepo.deleteAll();
    }
}
