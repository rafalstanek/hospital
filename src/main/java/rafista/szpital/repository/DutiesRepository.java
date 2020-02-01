package rafista.szpital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rafista.szpital.model.Duty;

import java.util.List;

public interface DutiesRepository extends JpaRepository<Duty, Integer> {
    List<Duty> findById(int id);
}
