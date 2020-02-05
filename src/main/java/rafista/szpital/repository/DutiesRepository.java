package rafista.szpital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rafista.szpital.model.Duty;

import java.util.List;

public interface DutiesRepository extends JpaRepository<Duty, Integer> {
    List<Duty> findById(int id);

    @Query(value = "SELECT * FROM DUTY u WHERE u.id = :id",  nativeQuery = true)
    Duty findDutyById(int id);

    @Query(value = "SELECT * FROM DUTY u WHERE u.id_user = :userId",  nativeQuery = true)
    List<Duty> findByUser(int userId);

    @Query(value = "SELECT * FROM DUTY u WHERE u.id_user != :userId AND u.changeable = 'true' ORDER BY u.START DESC",  nativeQuery = true)
    List<Duty> findAllChangeableDuty(int userId);

    @Query(value = "SELECT * FROM DUTY u WHERE u.id_user != :userId AND u.changeable = 'true' AND u.id_hospital = :hospitalId ORDER BY u.START DESC",  nativeQuery = true)
    List<Duty> findAllChangeableDutyByHospital(int userId, int hospitalId);
}
