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
            List<Duty> myDuties = dutiesRepository.findByUser(newDuty.getIdUser());
            boolean isDutyTimeExist=false;
            for (Duty dutyMy : myDuties) {
                if (checkDutyTimestamp(dutyMy.getDateStart(), dutyMy.getDateEnd(), newDuty.getDateStart(), newDuty.getDateEnd())) {
                    isDutyTimeExist=true;
                    break;
                }
            }
            if(!isDutyTimeExist)
            {
                newDuty.setUser(user);
                newDuty.setHospital(hospital);
                dutiesRepository.saveAndFlush(newDuty);
                return newDuty;
            }
            else
            {
                return badDuty(-2); //istnieje juz taka pora
            }
        }
        return badDuty(-1);
    }

    @GetMapping("/get")
    public List<Duty> getAllDuty() {
        deleteOldDuties();
        return dutiesRepository.findAll();
    }

    @GetMapping("/get/changeable")
    public List<Duty> getAllChangeAbleDuty(@RequestParam(name = "userId", required = true) int userId, @RequestParam(name = "hospitalId", required = true) int hospitalId) {
        deleteOldDuties();
        List<Duty> myDuties = dutiesRepository.findByUser(userId);
        List<Duty> dutiesToChange = null;
        if(hospitalId>0){
           dutiesToChange  = dutiesRepository.findAllChangeableDutyByHospital(userId, hospitalId);
        }else{
            dutiesToChange = dutiesRepository.findAllChangeableDuty(userId);
        }

        List<Duty> returnList = new ArrayList<>();

        if (myDuties.size() == 0) {
            return dutiesToChange;
        }
        for (Duty dutyChange : dutiesToChange) {
            boolean isExist = false;
            for (Duty dutyMy : myDuties) {
                if (checkDutyTimestamp(dutyChange.getDateStart(), dutyChange.getDateEnd(), dutyMy.getDateStart(), dutyMy.getDateEnd())) {
                    isExist=true;
                    break;
                }
            }
            if(!isExist)
                returnList.add(dutyChange);
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

    @GetMapping("/get/{userId}")
    public List<Duty> getDutyByUser(@PathVariable int userId) {
        deleteOldDuties();
        return dutiesRepository.findByUser(userId);
    }

    @PutMapping("/update/changeable/{id}")
    public Duty updateDuty(@PathVariable int id, @RequestParam(name = "userId", required = true) int userId) {
        Duty duty = dutiesRepository.findDutyById(id);
        if (duty != null && duty.getUser().getId()==userId) {
            duty.setChangeable(!duty.isChangeable());
            dutiesRepository.saveAndFlush(duty);
            return duty;
        }
        return badDuty(-1);
    }

    @DeleteMapping("/delete/{id}")
    public Duty deleteDuty(@PathVariable int id, @RequestParam(name = "userId", required = true) int userId) {
        boolean exist = dutiesRepository.existsById(id);
        if (exist) {
            Duty duty = dutiesRepository.findDutyById(id);
            User user = usersRepository.findUserById(userId);
            if(duty.getUser().getId()==userId || user.getRole()==0)
            {
                dutiesRepository.deleteById(id);
                return badDuty(-1); //success
            }
            else
                {
                return badDuty(-3);
            }
        } else {

            return badDuty(-2);
        }
        //return badDuty(-3);
    }

    @PutMapping("/take/{dutyId}")
    public Duty takeDuty(@PathVariable int dutyId, @RequestParam(name = "userId", required = true) int userId, @RequestParam(name = "oldUserId", required = true) int oldUserId) {
        Duty duty = dutiesRepository.findDutyById(dutyId);
        if(duty!=null)
        {
            if(duty.getUser().getId()==oldUserId && duty.isChangeable()){
                User newUser = usersRepository.findUserById(userId);
                if(newUser!=null)
                {
                    duty.setUser(newUser);
                    duty.setIdUser(newUser.getId());
                    duty.setChangeable(false);
                    dutiesRepository.saveAndFlush(duty);
                    return duty;
                }
                else
                {
                    return badDuty(-1);
                }
            }
            else
            {
                return badDuty(-2); //ten dyzur nalezy juz do kogos innego
            }
        }
        return badDuty(-1);
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
