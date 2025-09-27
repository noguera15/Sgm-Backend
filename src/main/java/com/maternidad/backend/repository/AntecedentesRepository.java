package com.maternidad.backend.repository;

import com.maternidad.backend.entity.Antecedentes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AntecedentesRepository extends JpaRepository<Antecedentes, Integer> {
    
    // Buscar antecedentes por ID de embarazo
    Optional<Antecedentes> findByEmbarazoId(Integer embarazoId);
}
