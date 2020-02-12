package rafista.szpital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import rafista.szpital.model.Ward;
import rafista.szpital.repository.HospitalsRepository;
import rafista.szpital.repository.WardsRepository;

import java.util.List;

@Component
@RestController
@RequestMapping("/api/ward")
public class WardController {
    @Autowired
    WardsRepository wardsRepository;

    @Autowired
    HospitalsRepository hospitalsRepository;

    @PostMapping(value = "/create")
    public Ward Create(@RequestBody Ward ward) {

        if(hospitalsRepository.findHospitalById(ward.getHospital_id())!=null)
        {
            wardsRepository.saveAndFlush(ward);
            return ward;
        }
        return badWard(-1);
    }

    @GetMapping("/get")
    public List<Ward> getAllWards() {
        return wardsRepository.findAll();
    }

    private Ward badWard(int value) {
        Ward ward = new Ward();
        ward.setId(value);
        return ward;
    }
}


