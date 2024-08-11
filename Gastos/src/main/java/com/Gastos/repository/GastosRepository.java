package com.Gastos.repository;

import com.Gastos.model.Gastos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GastosRepository extends JpaRepository<Gastos, Long> {
}
