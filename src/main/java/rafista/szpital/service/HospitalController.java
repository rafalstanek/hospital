package rafista.szpital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import rafista.szpital.model.Hospital;
import rafista.szpital.model.User;
import rafista.szpital.model.Ward;
import rafista.szpital.repository.HospitalsRepository;
import rafista.szpital.repository.WardsRepository;

import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Component
@RestController
@RequestMapping("/api/hospital")
public class HospitalController {

    @Autowired
    HospitalsRepository hospitalsRepository;

    @Autowired
    WardsRepository wardsRepository;

    @PostMapping(value = "/create")
    public Hospital Create(@RequestBody Hospital newHospital){
        hospitalsRepository.saveAndFlush(newHospital);
        return newHospital;
    }

    @GetMapping(value = "/get")
    public List<Hospital> getAll(){
        return hospitalsRepository.findAll();
    }

    @GetMapping(value = "/get/ward")
    public List<Hospital> getHospitalByWard(@RequestParam(name = "ward", required = true) String wardName){
        List<Hospital> hospitalList = hospitalsRepository.findAll();
        List<Hospital> hospitaListWhereExistWard = new ArrayList<>();
        for (Hospital hospital: hospitalList)
        {
            List<Ward> wardList = wardsRepository.findByHospital(hospital.getId());
            boolean isExist = false;
            for(Ward ward : wardList){
                if(ward.getName().equals(wardName))
                    isExist=true;
            }
            if(isExist)
                hospitaListWhereExistWard.add(hospital);
        }
        return hospitaListWhereExistWard;
    }
}
