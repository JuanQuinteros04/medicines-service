package com.liro.medicines.repositories;

import com.liro.medicines.model.dbentities.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Page<Brand> findAllByNameContaining(String nameContaining, Pageable pageable);

    Optional<Brand> findByName(String name);

}
