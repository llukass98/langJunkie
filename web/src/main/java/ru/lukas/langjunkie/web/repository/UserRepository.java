package ru.lukas.langjunkie.web.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import ru.lukas.langjunkie.web.dto.UserViewDto;
import ru.lukas.langjunkie.web.model.User;

import java.util.Optional;

/**
 * @author Dmitry Lukashevich
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @EntityGraph(attributePaths = { "cards" })
    Optional<User> findByUsername(String username);

    Optional<UserViewDto> findViewByUsername(String username);
}
