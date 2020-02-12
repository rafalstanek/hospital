package rafista.szpital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rafista.szpital.model.Ward;

import java.util.List;

public interface WardsRepository extends JpaRepository<Ward, Integer> {
    @Query(value = "SELECT * FROM WARD w WHERE w.hospital_id = :id",  nativeQuery = true)
    List<Ward> findByHospital(int id);

    @Query(value = "SELECT * FROM WARD w WHERE w.name = :name",  nativeQuery = true)
    List<Ward> findByName(String name);
}
