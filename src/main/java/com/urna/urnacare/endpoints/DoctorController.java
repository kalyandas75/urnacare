package com.urna.urnacare.endpoints;

import com.urna.urnacare.dto.SearchDoctorDto;
import com.urna.urnacare.mapper.SearchDoctorMapper;
import com.urna.urnacare.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final SearchDoctorMapper searchDoctorMapper;

    public DoctorController(DoctorService doctorService, SearchDoctorMapper searchDoctorMapper) {
        this.doctorService = doctorService;
        this.searchDoctorMapper = searchDoctorMapper;
    }

    @GetMapping("/speciality/doctors/{speciality}")
    public @ResponseBody
    List<SearchDoctorDto> getAllDoctorsBySpecialization(@PathVariable String speciality) {
        return this.searchDoctorMapper.toDto(doctorService.findByPrimarySpeciality(speciality));
    }
}
