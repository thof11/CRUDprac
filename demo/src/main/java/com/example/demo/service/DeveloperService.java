package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.models.Developer;
import com.example.demo.repo.DeveloperRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeveloperService {

    private final DeveloperRepo developerRepo;

    public List<Developer> getAllUsers() {
        return developerRepo.findAll();
    }

    public Developer getUserById(long id) {
        return developerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public Developer createUser(Developer developer) {
        return developerRepo.save(developer);
    }

    public Developer updateUser(long id, Developer developer) {
        Developer existingDeveloper = getUserById(id);
        existingDeveloper.setFirstName(developer.getFirstName());
        existingDeveloper.setLastName(developer.getLastName());
        existingDeveloper.setAge(developer.getAge());
        return developerRepo.save(existingDeveloper);
    }

    public void deleteUser(long id) {
        Developer developer = getUserById(id);
        developerRepo.delete(developer);
    }

    public void deleteAllUsers() {
        developerRepo.deleteAll();
    }
}
