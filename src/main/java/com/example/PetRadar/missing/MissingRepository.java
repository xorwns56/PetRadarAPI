package com.example.PetRadar.missing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissingRepository extends JpaRepository<Missing, Long> {
}