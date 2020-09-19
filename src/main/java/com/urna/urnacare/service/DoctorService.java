package com.urna.urnacare.service;

import com.urna.urnacare.domain.Doctor;
import com.urna.urnacare.dto.DoctorDTO;
import com.urna.urnacare.mapper.AddressMapper;
import com.urna.urnacare.mapper.DoctorMapper;
import com.urna.urnacare.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final AddressMapper addressMapper;

    public DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper, AddressMapper addressMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
        this.addressMapper = addressMapper;
    }

    public List<Doctor> findByPrimarySpeciality(String primarySpeciality) {
        return this.doctorRepository.findByPrimarySpeciality(primarySpeciality);
    }

    public Optional<DoctorDTO> update(DoctorDTO doctorDTO) {
        return Optional.of(doctorRepository
                .findById(doctorDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(doctor -> {
                    doctor.setEmail(doctorDTO.getEmail());
                    doctor.setRegistrationNumber(doctorDTO.getRegistrationNumber());
                    doctor.setPrimarySpeciality(doctorDTO.getPrimarySpeciality());
                    doctor.setPractice(doctorDTO.getPractice());
                    doctor.setOtherSpecialities(doctorDTO.getOtherSpecialities());
                    doctor.setQualifications(doctorDTO.getQualifications());
                    doctor.setFirstName(doctorDTO.getFirstName());
                    doctor.setAddresses(this.addressMapper.toEntity(doctorDTO.getAddresses()));
                    doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
                    doctor.setAlternatePhoneNumber(doctorDTO.getAlternatePhoneNumber());
                    return this.doctorMapper.toDto(doctor);
                });
    }

}
