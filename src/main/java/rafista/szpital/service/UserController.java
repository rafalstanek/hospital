package rafista.szpital.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import rafista.szpital.model.User;
import rafista.szpital.repository.UsersRepository;

import java.util.List;

@EnableScheduling
@Component
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UsersRepository usersRepository;

    @PostMapping(value = "/create")
    public User Create(@RequestBody User newUser){
        usersRepository.save(newUser);
        usersRepository.flush();

        System.out.println("Dodano użytkownika do bazy "+usersRepository.findAll().size());
        return newUser;
    }

    @GetMapping("/get")
    List<User> all() {
        return usersRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    void deleteEmployee(@PathVariable int id) {
        usersRepository.deleteById(id);
    }
}
