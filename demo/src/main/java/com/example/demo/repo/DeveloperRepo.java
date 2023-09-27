package com.example.demo.repo;

import com.example.demo.models.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface DeveloperRepo extends JpaRepository<Developer, Long> {

    void deleteById(Long id);


}
