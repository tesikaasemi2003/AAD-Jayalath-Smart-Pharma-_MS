package lk.ijse.Jayalath_Smart_Pharma.service.Impl;

import jakarta.transaction.Transactional;
import lk.ijse.Jayalath_Smart_Pharma.constant.CommonResponse;
import lk.ijse.Jayalath_Smart_Pharma.dto.DoctorDTO;
import lk.ijse.Jayalath_Smart_Pharma.entity.Doctor;
import lk.ijse.Jayalath_Smart_Pharma.repository.DoctorRepository;
import lk.ijse.Jayalath_Smart_Pharma.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<DoctorDTO> getAllDoctors() {
        log.info("Executing getAllDoctors method");
        try {
            List<Doctor> doctors = doctorRepository.findAll();
            List<DoctorDTO> dtoList = new ArrayList<>();
            for (Doctor doctor : doctors) {
                dtoList.add(convertToDTO(doctor));
            }
            return dtoList;
        } catch (Exception e) {
            log.error("Error in getAllDoctors method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public DoctorDTO getDoctorById(Long doctorId) {
        log.info("Executing getDoctorById method for ID: {}", doctorId);
        try {
            Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
            if (!optionalDoctor.isPresent()) {
                throw new RuntimeException("Doctor with ID: " + doctorId + " not found");
            }
            return convertToDTO(optionalDoctor.get());
        } catch (Exception e) {
            log.error("Error in getDoctorById method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void updateDoctor(Long doctorId, DoctorDTO doctorDTO) {
        log.info("Executing updateDoctor method for ID: {}", doctorId);
        try {
            Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
            if (optionalDoctor.isEmpty()) {
                throw new RuntimeException("Doctor with ID: " + doctorId + " not found");
            }

            Doctor doctor = optionalDoctor.get();
            doctorDTO.setDoctorName(doctorDTO.getDoctorName());
            doctorDTO.setHospital(doctorDTO.getHospital());
            doctorDTO.setSlmcRegistrationNo(doctorDTO.getSlmcRegistrationNo());
            doctorRepository.save(doctor);

        } catch (Exception e) {
            log.error("Error in updateDoctor method: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteDoctor(Long doctorId) {
        log.info("Executing deleteDoctor method for ID: {}", doctorId);
        try {
            Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
            if (optionalDoctor.isEmpty()) {
                throw new RuntimeException("Doctor with ID: " + doctorId + " not found");
            }
            doctorRepository.deleteById(doctorId);
        } catch (Exception e) {
            log.error("Error in deleteDoctor method: " + e.getMessage());
            throw e;
        }
    }

    private DoctorDTO convertToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setDoctorId(doctor.getDoctorId());
        dto.setDoctorName(doctor.getDoctorName());
        dto.setHospital(doctor.getHospital());
        dto.setSlmcRegistrationNo(doctor.getSlmcRegistrationNo());

        return dto;
    }

}
