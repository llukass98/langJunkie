package ru.lukas.langjunkie.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.lukas.langjunkie.web.dto.CardViewDto;
import ru.lukas.langjunkie.web.model.Card;

import java.util.Optional;

/**
 * @author Dmitry Lukashevich
 */
@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

    @Query( "SELECT CASE WHEN c.image IS NOT NULL THEN true ELSE false END " +
            "FROM Card c WHERE c.id = :id" )
    boolean hasImage(@Param("id") Long id);

    @Query("FROM Card c LEFT JOIN FETCH c.user LEFT JOIN FETCH c.image WHERE c.id = :id")
    Optional<Card> findByIdEagerFetch(@Param("id") Long id);

    Page<CardViewDto> findAllCardViewDtoByUserId(Long id, Pageable pageable);
}
