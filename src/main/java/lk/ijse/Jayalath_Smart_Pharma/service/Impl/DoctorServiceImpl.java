package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.DoctorDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Doctor;
import lk.ijse.Jayalath_Smart_Pharma.repository.DoctorRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    @Transactional
    public void saveDoctor(DoctorDTO doctorDTO) {
        log.info("Executing saveDoctor method for Name: {}", doctorDTO.getDoctorName());
        try {
            Doctor doctor = new Doctor();
            doctorDTO.setDoctorName(doctorDTO.getDoctorName());
            doctorDTO.setHospital(doctorDTO.getHospital());
            doctorDTO.setSlmcRegistrationNo(doctorDTO.getSlmcRegistrationNo());
            doctorRepository.save(doctor);

        } catch (Exception e) {
            log.error("Error in saveDoctor method: " + e.getMessage());
            throw e;
        }
    }

}
