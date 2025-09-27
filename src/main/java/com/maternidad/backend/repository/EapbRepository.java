package com.maternidad.backend.repository;

import com.maternidad.backend.entity.Eapb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EapbRepository extends JpaRepository<Eapb, Integer> {
}



