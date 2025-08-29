package com.example.PetRadar.missing;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissingRepository extends JpaRepository<Missing, Long> {
    List<Missing> findByUserId(Long userId, Sort sort);
}