package ru.lukas.langjunkie.web.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lukas.langjunkie.web.model.Card;
import ru.lukas.langjunkie.web.model.Word;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
}
