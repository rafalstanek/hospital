package rafista.szpital.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

        @Id
        @Column(name = "ID")
        private int id;

        @Column(name = "VERIFIED")
        private String name;
}
