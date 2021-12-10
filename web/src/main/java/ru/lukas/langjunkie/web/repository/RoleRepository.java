package ru.lukas.langjunkie.web.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lukas.langjunkie.web.model.Role;

import java.util.Optional;

/**
 * @author Dmitry Lukashevich
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
