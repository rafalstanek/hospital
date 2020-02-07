package rafista.szpital.model;

import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "USER")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int Id;

    @Column(name = "FIRST_NAME")
    private String FirstName;

    @Column(name = "LAST_NAME")
    private String LastName;

    @Column(name = "LOGIN")
    private String Login;

    @Column(name = "PASSWORD")
    private String Password;

    @Column(name = "TITLE")
    private String Title;

    @Column(name = "ROLE")
    private int Role;

    @Column(name = "SPECIALITY")
    private String Speciality;

    public User(String firstName, String lastName, String login, String password, String title, int role, String speciality) {
        FirstName = firstName;
        LastName = lastName;
        Login = login;
        Password = password;
        Title = title;
        Role = role;
        Speciality = speciality;
    }

    public User() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Integer getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }
}
