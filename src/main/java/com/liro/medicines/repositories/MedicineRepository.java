package com.liro.medicines.repositories;

import com.liro.medicines.model.dbentities.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Page<Medicine> findAllByNameContaining(String nameContaining, Pageable pageable);

    Page<Medicine> findAllByNameContainingAndPresentationName(String nameContaining, String presentationName, Pageable pageable);

}
