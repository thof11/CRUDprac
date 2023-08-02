package com.example.demo.repo;

import com.example.demo.models.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquadRepo extends JpaRepository<Squad, Long> {

}