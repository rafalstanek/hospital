package rafista.szpital.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@Component
@RestController
@RequestMapping("/api")
public class Controller {

    @RequestMapping("/tekst")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/test")
    public ResponseEntity isDriverLogged() {
            return new ResponseEntity<String>("{\"tested\":true}", HttpStatus.OK);
    }
}
