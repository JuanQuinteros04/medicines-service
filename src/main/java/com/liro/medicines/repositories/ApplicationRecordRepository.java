package com.liro.medicines.repositories;
import com.liro.medicines.model.dbentities.ApplicationRecord;
import com.liro.medicines.model.dbentities.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRecordRepository extends JpaRepository<ApplicationRecord, Long> {


        @Query("SELECT ar FROM ApplicationRecord ar " +
                "JOIN ar.medicine m " +
                "JOIN m.medicineGroup mg " +
                "WHERE ar.applicationDate = (SELECT MAX(ar2.applicationDate) FROM ApplicationRecord ar2 " +
                "JOIN ar2.medicine m2 " +
                "JOIN m2.medicineGroup mg2 " +
                "WHERE mg2.id = mg.id)")
        List<ApplicationRecord> findLatestApplicationsForEachMedicineGroup();
    }
