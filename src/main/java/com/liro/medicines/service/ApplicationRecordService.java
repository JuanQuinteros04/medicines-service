package com.liro.medicines.service;

import com.liro.medicines.dto.ApplicationRecordDTO;
import com.liro.medicines.dto.responses.ApplicationRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationRecordService {

    Page<ApplicationRecordResponse> findAll(Pageable pageable);

    ApplicationRecordResponse findById(Long applicationRecordId);

    Page<ApplicationRecordResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    ApplicationRecordResponse createApplicationRecord(ApplicationRecordDTO applicationRecordDTO, String token);

}
