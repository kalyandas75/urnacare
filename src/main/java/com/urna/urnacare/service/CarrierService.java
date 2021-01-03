package com.urna.urnacare.service;

import com.urna.urnacare.dto.CarrierDTO;
import com.urna.urnacare.mapper.CarrierMapper;
import com.urna.urnacare.repository.CarrierRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarrierService {
    private final CarrierRepository carrierRepository;
    private final CarrierMapper carrierMapper;

    public CarrierService(CarrierRepository carrierRepository, CarrierMapper carrierMapper) {
        this.carrierRepository = carrierRepository;
        this.carrierMapper = carrierMapper;
    }

    @Transactional(readOnly = true)
    public List<CarrierDTO> getAll() {
        return this.carrierRepository.findAll().stream()
                .map(carrier -> this.carrierMapper.toDto(carrier))
                .collect(Collectors.toList());
    }
}
