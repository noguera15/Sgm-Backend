package com.maternidad.backend.repository;

import com.maternidad.backend.entity.Ips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpsRepository extends JpaRepository<Ips, Integer> {
}



