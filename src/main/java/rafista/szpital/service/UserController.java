package rafista.szpital.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rafista.szpital.model.Duty;
import rafista.szpital.model.User;
import rafista.szpital.repository.DutiesRepository;
import rafista.szpital.repository.UsersRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@EnableScheduling
@Component
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    DutiesRepository dutiesRepository;

    @PostMapping(value = "/create")
    public User Create(@RequestBody User newUser) {
        if (usersRepository.existsByLogin(newUser.getLogin()) == null) {
            usersRepository.save(newUser);
            usersRepository.flush();
            return newUser;
        }
        return badUser(-1);
    }


    @GetMapping("/get")
    public List<User> getAllUser() {
        return usersRepository.findAll();
    }

    @ResponseBody
    @GetMapping("/login")
    public User getUserByLogin(@RequestParam(name = "login", required = true) String login, @RequestParam(name = "password", required = true) String password) {
        if (login != null && password != null) {
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            User existUser = usersRepository.existsByLogin(login);
            if (existUser != null) {
                if (bcrypt.matches(password, existUser.getPassword())) {
                    return existUser;
                }
            }
        }
        return badUser(-1);
    }

    @GetMapping("/get/role/{status}")
    public Collection<User> getUserByRole(@PathVariable int status) {
        return usersRepository.findByRole(status);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
        boolean exist = usersRepository.existsById(id);
        if (exist) {
            List<Duty> allDutyOfUser = dutiesRepository.findByUser(id);
            for (Duty duty : allDutyOfUser) {
                dutiesRepository.delete(duty);
            }
            usersRepository.deleteById(id);
            System.out.println(response.getStatus());
            return new ResponseEntity<>("User has been deleted!", HttpStatus.OK);
        } else {
            System.out.println(response.getStatus());
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update/password/{id}")
    public User updateUser(@PathVariable int id, @RequestParam(name = "oldPassword", required = true) String oldPassword, @RequestParam(name = "password", required = true) String password) {
        boolean exist = usersRepository.existsById(id);
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        if (exist) {
            User user = usersRepository.findById(id).get(0);
            if (bcrypt.matches(oldPassword, user.getPassword())) {
                user.setPassword(password);
                usersRepository.saveAndFlush(user);
                return user;
            } else {
                return badUser(-2);
            }
        }

        return badUser(-1);
    }

    private User badUser(int value) {
        User user = new User();
        user.setId(value);
        return user;
    }
}
