package rafista.szpital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafista.szpital.model.Duty;
import rafista.szpital.repository.DutiesRepository;

@EnableScheduling
@Component
@RestController
@RequestMapping("/api/duty")
public class DutyController {

    @Autowired
    DutiesRepository dutiesRepository;

    @PostMapping(value = "/create")
    public Duty Create(@RequestBody Duty newDuty){
        dutiesRepository.saveAndFlush(newDuty);

        return newDuty;
    }
}
