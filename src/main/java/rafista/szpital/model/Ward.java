package rafista.szpital.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "WARD")
public class Ward {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ID_HOSPITAL")
    private int hospital_id;

    @Column(name = "NAME")
    private String name;

    public Ward(int hospital_id, String name) {
        this.hospital_id = hospital_id;
        this.name = name;
    }

    public Ward(){}

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
