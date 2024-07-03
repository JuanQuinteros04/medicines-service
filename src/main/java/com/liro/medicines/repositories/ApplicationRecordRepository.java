package com.liro.medicines.repositories;
import com.liro.medicines.model.dbentities.ApplicationRecord;
import com.liro.medicines.model.dbentities.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRecordRepository extends JpaRepository<ApplicationRecord, Long> {

}
