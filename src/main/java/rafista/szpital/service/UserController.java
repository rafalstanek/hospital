package rafista.szpital.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import rafista.szpital.model.User;
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

    @PostMapping(value = "/create")
    public User Create(@RequestBody User newUser){
        usersRepository.save(newUser);
        usersRepository.flush();

        System.out.println("Dodano użytkownika do bazy "+usersRepository.findAll().size());
        return newUser;
    }

    @GetMapping("/get")
    public List<User> getAllUser() {
        return usersRepository.findAll();
    }

    @GetMapping("/get/role/{status}")
    public Collection<User> getCasualUser(@PathVariable int status){
        return usersRepository.findByRole(status);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
        boolean exist = usersRepository.existsById(id);
        if(exist){
            usersRepository.deleteById(id);
            System.out.println(response.getStatus());
            return new ResponseEntity<>("User has been deleted!", HttpStatus.OK);
        }
        else{
            System.out.println(response.getStatus());
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody User user, @PathVariable int id) {
       boolean exist = usersRepository.existsById(id);

        if(exist)
        {
            if(user!=null && user.getLogin()!=null && user.getPassword()!=null && user.getTitle()!=null && user.getSpeciality()!=null) {
                User newUser = usersRepository.findById(id).get(0);
                newUser.setLogin(user.getLogin());
                newUser.setPassword(user.getPassword());
                newUser.setTitle(user.getTitle());
                newUser.setSpeciality(user.getSpeciality());
                usersRepository.saveAndFlush(newUser);
                return newUser;
            }
        }

        return null;
    }


}
