package com.example.demo.repo;

import com.example.demo.models.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepo extends JpaRepository<Developer, Long> {
}
