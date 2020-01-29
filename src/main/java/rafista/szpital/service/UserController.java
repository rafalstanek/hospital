package rafista.szpital.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafista.szpital.repository.UsersRepository;

@EnableScheduling
@Component
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UsersRepository usersRepository;

    @RequestMapping("/tekst")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/test")
    public ResponseEntity isDriverLogged() {
            return new ResponseEntity<String>("{\"tested\":true}", HttpStatus.OK);
    }
}
