package rafista.szpital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafista.szpital.model.Hospital;
import rafista.szpital.model.User;
import rafista.szpital.repository.HospitalsRepository;

@EnableScheduling
@Component
@RestController
@RequestMapping("/api/hospital")
public class HopistalController {

    @Autowired
    HospitalsRepository hospitalsRepository;

    @PostMapping(value = "/create")
    public Hospital Create(@RequestBody Hospital newHospital){
        hospitalsRepository.saveAndFlush(newHospital);

        return newHospital;
    }
}
