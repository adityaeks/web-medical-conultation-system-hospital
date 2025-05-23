package com.medical.web.repo;

import com.medical.web.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    // Additional custom queries can be defined here if needed
    long countByDoctorId(Long doctorId);
}