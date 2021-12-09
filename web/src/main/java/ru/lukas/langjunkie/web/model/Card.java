package ru.lukas.langjunkie.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Dmitry Lukashevich
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "front_side")
    private String frontSide;

    @Column(name = "back_side")
    private String backSide;

    private String language;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    private Word word;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
