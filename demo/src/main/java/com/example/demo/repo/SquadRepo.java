package com.example.demo.repo;

import com.example.demo.models.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


public interface SquadRepo extends JpaRepository<Squad, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Developer d WHERE d.squad.id = :squadId AND d.id = :developerId")
    void removeDeveloperFromSquad(@Param("squadId") Long squadId, @Param("developerId") Long developerId);
}

