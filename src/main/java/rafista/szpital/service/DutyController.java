package rafista.szpital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import rafista.szpital.model.Duty;
import rafista.szpital.model.Hospital;
import rafista.szpital.model.User;
import rafista.szpital.repository.DutiesRepository;
import rafista.szpital.repository.HospitalsRepository;
import rafista.szpital.repository.UsersRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@EnableScheduling
@Component
@RestController
@RequestMapping("/api/duty")
public class DutyController {

    @Autowired
    DutiesRepository dutiesRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    HospitalsRepository hospitalsRepository;

    @PostMapping(value = "/create")
    public Duty Create(@RequestBody Duty newDuty) {
        Hospital hospital = hospitalsRepository.findHospitalById(newDuty.getIdHospital());
        User user = usersRepository.findUserById(newDuty.getIdUser());
        if (hospital != null && user != null) {
            newDuty.setUser(user);
            newDuty.setHospital(hospital);
            dutiesRepository.saveAndFlush(newDuty);
            return newDuty;
        }
        return badDuty(-1);
    }

    @GetMapping("/get")
    public List<Duty> getAllDuty() {
        return dutiesRepository.findAll();
    }

    @GetMapping("/get/changeable")
    public List<Duty> getAllChangeAbleDuty(@RequestParam(name = "userId", required = true) int userId) {
        return dutiesRepository.findAllChangeableDuty(userId);
    }

    @GetMapping("/get/changeable/hospital")
    public List<Duty> getAllChangeAbleDutyByHospital(@RequestParam(name = "userId", required = true) int userId,
                                                     @RequestParam(name = "hospitalId", required = true) int hospitalId) {
        return dutiesRepository.findAllChangeableDutyByHospital(userId, hospitalId);
    }

    @GetMapping("/get/{userId}")
    public List<Duty> getDutyByUser(@PathVariable int userId) {
        return dutiesRepository.findByUser(userId);
    }

    @PutMapping("/update/{id}")
    public Duty updateDuty(@PathVariable int id) {
        Duty duty = dutiesRepository.findDutyById(id);
        if (duty != null) {
            duty.setChangeable(!duty.isChangeable());
            dutiesRepository.saveAndFlush(duty);
            return duty;
        }
        return badDuty(-1);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDuty(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
        boolean exist = dutiesRepository.existsById(id);
        if (exist) {
            dutiesRepository.deleteById(id);
            return new ResponseEntity<>("Duty has been deleted!", HttpStatus.OK);
        } else {
            System.out.println(response.getStatus());
            return new ResponseEntity<>(
                    "Duty not found",
                    HttpStatus.BAD_REQUEST);
        }

    }

    private Duty badDuty(int value) {
        Duty duty = new Duty();
        duty.setId(value);
        return duty;
    }
}
