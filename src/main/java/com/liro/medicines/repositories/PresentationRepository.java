package com.liro.medicines.repositories;

import com.liro.medicines.model.dbentities.Brand;
import com.liro.medicines.model.dbentities.Presentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Long> {

    Page<Presentation> findAllByNameContaining(String nameContaining, Pageable pageable);
    Optional<Presentation> findByName(String name);

}
