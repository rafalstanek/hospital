package rafista.szpital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rafista.szpital.model.Hospital;

import java.util.Collection;
import java.util.List;

public interface HospitalsRepository extends JpaRepository<Hospital, Integer> {
    List<Hospital> findById(int id);

    @Query(value = "SELECT * FROM HOSPITAL u WHERE u.id = :id",  nativeQuery = true)
    Hospital findHospitalById(int id);

    @Query(value = "SELECT * FROM HOSPITAL u WHERE u.name = :name",  nativeQuery = true)
    Collection<Hospital> findByName(@Param("name") String hospitalName);
}
