package ru.lukas.langjunkie.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lukas.langjunkie.web.model.User;

/**
 * @author Dmitry Lukashevich
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   // void saveUser(User user);
}
