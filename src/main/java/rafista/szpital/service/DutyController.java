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
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
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
        deleteOldDuties();
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
        deleteOldDuties();
        return dutiesRepository.findAll();
    }

    @GetMapping("/get/changeable")
    public List<Duty> getAllChangeAbleDuty(@RequestParam(name = "userId", required = true) int userId) {
        deleteOldDuties();
        List<Duty> myDuties = dutiesRepository.findByUser(userId);
        List<Duty> dutiesToChange = dutiesRepository.findAllChangeableDuty(userId);
        List<Duty> returnList = new ArrayList<>();

        if (myDuties.size() == 0) {
            return dutiesToChange;
        }
        for (Duty dutyChange : dutiesToChange) {
            for (Duty dutyMy : myDuties) {
                if (!checkDutyTimestamp(dutyChange.getDateStart(), dutyChange.getDateEnd(), dutyMy.getDateStart(), dutyMy.getDateEnd())) {
                    returnList.add(dutyChange);
                }
            }
        }

        return returnList;
    }

    //zwraca true jesli nachodzi
    private boolean checkDutyTimestamp(Timestamp from1, Timestamp to1, Timestamp from2, Timestamp to2) {
        if ((from1.before(to2) && from1.after(from2)) || (from2.before(to1) && from2.after(from1)) || (to1.after(from2) && to1.before(to2)) || (to2.after(from1) && to2.before(to1))) {
            return true;
        }
        return false;
    }

    @GetMapping("/get/changeable/hospital")
    public List<Duty> getAllChangeAbleDutyByHospital(@RequestParam(name = "userId", required = true) int userId,
                                                     @RequestParam(name = "hospitalId", required = true) int hospitalId) {
        return dutiesRepository.findAllChangeableDutyByHospital(userId, hospitalId);
    }

    @GetMapping("/get/{userId}")
    public List<Duty> getDutyByUser(@PathVariable int userId) {
        deleteOldDuties();
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

    @PutMapping("/take/{dutyId}")
    public Duty takeDuty(@PathVariable int dutyId, @RequestParam(name = "userId", required = true) int userId) {
        //sprawdz czy w tym czasie nie ma innej duty
        return null;
    }

    private void deleteOldDuties() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        List<Duty> allOldDuties = dutiesRepository.findOldDuties(currentTime);
        for (Duty duty : allOldDuties) {
            dutiesRepository.delete(duty);
        }

    }

    private Duty badDuty(int value) {
        Duty duty = new Duty();
        duty.setId(value);
        return duty;
    }
}
