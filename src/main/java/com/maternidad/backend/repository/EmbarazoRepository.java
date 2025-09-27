package com.maternidad.backend.repository;

import com.maternidad.backend.entity.Embarazo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmbarazoRepository extends JpaRepository<Embarazo, Integer> {
    
    // Buscar embarazos por documento de paciente
    List<Embarazo> findByPacienteDocumento(Integer documento);
}
