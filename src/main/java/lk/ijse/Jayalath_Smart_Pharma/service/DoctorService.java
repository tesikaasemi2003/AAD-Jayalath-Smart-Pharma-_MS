package lk.ijse.Jayalath_Smart_Pharma.service;

import lk.ijse.Jayalath_Smart_Pharma.dto.DoctorDTO;

import java.util.List;

public interface DoctorService {
    public void saveDoctor(DoctorDTO doctorDTO);
    public List<DoctorDTO> getAllDoctors();
    public DoctorDTO getDoctorById(Long doctorId);
    public void updateDoctor(Long doctorId, DoctorDTO doctorDTO);
    public void deleteDoctor(Long doctorId);
}
