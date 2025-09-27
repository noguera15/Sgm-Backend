package com.maternidad.backend.repository;

import com.maternidad.backend.entity.Regimen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegimenRepository extends JpaRepository<Regimen, Integer> {
}



