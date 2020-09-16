package com.urna.urnacare.service;

import com.urna.urnacare.domain.Doctor;
import com.urna.urnacare.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> findByPrimarySpeciality(String primarySpeciality) {
        return this.doctorRepository.findByPrimarySpeciality(primarySpeciality);
    }
}
