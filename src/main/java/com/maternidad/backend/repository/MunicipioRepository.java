package com.maternidad.backend.repository;

import com.maternidad.backend.entity.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {
    Optional<Municipio> findByNombreMunicipio(String nombreMunicipio);
}
