package rafista.szpital.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "DUTY")
public class Duty {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int Id;

    @Column(name = "ID_HOSPITAL")
    private int IdHospital;

    @Column(name = "ID_USER")
    private int IdUser;

    @Column(name = "START")
    private Timestamp DateStart;

    @Column(name = "END")
    private Timestamp DateEnd;

    @Column(name = "CHANGEABLE")
    private boolean Changeable;

    @ManyToOne
    private User User;

    @ManyToOne
    private Hospital hospital;

    public Duty(){

    }

    public Duty(int idHospital, int idUser, Timestamp dateStart, Timestamp dateEnd, boolean changeable) {
        IdHospital = idHospital;
        IdUser = idUser;
        DateStart = dateStart;
        DateEnd = dateEnd;
        Changeable = changeable;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdHospital() {
        return IdHospital;
    }

    public void setIdHospital(int idHospital) {
        IdHospital = idHospital;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public Timestamp getDateStart() {
        return DateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        DateStart = dateStart;
    }

    public Timestamp getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        DateEnd = dateEnd;
    }

    public boolean isChangeable() {
        return Changeable;
    }

    public void setChangeable(boolean changeable) {
        Changeable = changeable;
    }

    public rafista.szpital.model.User getUser() {
        return User;
    }

    public void setUser(rafista.szpital.model.User user) {
        User = user;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}
