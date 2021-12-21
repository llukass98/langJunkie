package ru.lukas.langjunkie.web.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.lukas.langjunkie.web.model.User;

import java.util.Optional;

/**
 * @author Dmitry Lukashevich
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
