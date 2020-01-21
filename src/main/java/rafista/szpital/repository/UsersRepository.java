package rafista.szpital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rafista.szpital.model.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Integer> {
    List<User> findById(int id);
}
