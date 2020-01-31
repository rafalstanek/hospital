package rafista.szpital.model;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "HOSPITAL")
public class Hospital {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int Id;

    @Column(name = "NAME")
    private String Name;

    @Column(name = "CITY")
    private String City;

    @Column(name = "ADDRESS")
    private String Address;

    public Hospital(String name, String city, String address) {
        Name = name;
        City = city;
        Address = address;
    }

    public Hospital(){};

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
