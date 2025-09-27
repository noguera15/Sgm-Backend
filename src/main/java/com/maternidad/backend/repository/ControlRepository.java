package com.maternidad.backend.repository;

import com.maternidad.backend.entity.Control;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ControlRepository extends JpaRepository<Control, Integer> {
    
    // Buscar controles por embarazo ordenados por número de control
    List<Control> findByEmbarazoIdOrderByNumeroControlAsc(Integer embarazoId);
    
    // Buscar controles por embarazo ordenados por fecha
    List<Control> findByEmbarazoIdOrderByFechaUltimoControlAsc(Integer embarazoId);
    
    // Buscar el último control de un embarazo
    List<Control> findTop1ByEmbarazoIdOrderByNumeroControlDesc(Integer embarazoId);
}



