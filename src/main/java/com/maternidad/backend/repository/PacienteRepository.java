package com.maternidad.backend.repository;

import com.maternidad.backend.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    // Métodos adicionales si se requieren
}