package com.liro.medicines.repositories;

import com.liro.medicines.model.dbentities.Formula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormulaRepository extends JpaRepository<Formula, Long> {

    Page<Formula> findAllByNameContaining(String nameContaining, Pageable pageable);



}
