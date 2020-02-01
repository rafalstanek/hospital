package rafista.szpital.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

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
    private Date DateStart;

    @Column(name = "END")
    private Date DateEnd;

    @Column(name = "CHANGEABLE")
    private boolean Changeable;

    public Duty(){

    }

    public Duty(int idHospital, int idUser, Date dateStart, Date dateEnd, boolean changeable) {
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

    public Date getDateStart() {
        return DateStart;
    }

    public void setDateStart(Date dateStart) {
        DateStart = dateStart;
    }

    public Date getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        DateEnd = dateEnd;
    }

    public boolean isChangeable() {
        return Changeable;
    }

    public void setChangeable(boolean changeable) {
        Changeable = changeable;
    }
}
