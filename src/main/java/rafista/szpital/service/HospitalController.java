package rafista.szpital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import rafista.szpital.model.Hospital;
import rafista.szpital.model.User;
import rafista.szpital.repository.HospitalsRepository;

import java.util.List;

@EnableScheduling
@Component
@RestController
@RequestMapping("/api/hospital")
public class HospitalController {

    @Autowired
    HospitalsRepository hospitalsRepository;

    @PostMapping(value = "/create")
    public Hospital Create(@RequestBody Hospital newHospital){
        hospitalsRepository.saveAndFlush(newHospital);

        return newHospital;
    }

    @GetMapping(value = "/get")
    public List<Hospital> getAll(){
        return hospitalsRepository.findAll();
    }
}
