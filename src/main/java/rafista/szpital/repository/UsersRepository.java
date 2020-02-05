package rafista.szpital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rafista.szpital.model.User;

import java.util.Collection;
import java.util.List;

public interface UsersRepository extends JpaRepository<User, Integer> {
    List<User> findById(int id);

    @Query(value = "SELECT * FROM USER u WHERE u.id = :id",  nativeQuery = true)
    User findUserById(int id);

    @Query(value = "SELECT * FROM USER u WHERE u.login = :login",  nativeQuery = true)
    User existsByLogin(String login);

    @Query(value = "SELECT * FROM USER u WHERE u.role = :status",  nativeQuery = true)
    Collection<User> findByRole(@Param("status") int userStatus);
}
