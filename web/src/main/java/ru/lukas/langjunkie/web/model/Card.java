package ru.lukas.langjunkie.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import ru.lukas.langjunkie.dictionarycollections.dictionary.DictionaryCollection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import java.util.Objects;

/**
 * @author Dmitry Lukashevich
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "front_side")
    private String frontSide;

    @Column(name = "back_side")
    private String backSide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private ImageFileInfo image;

    @Embedded
    private Word word;

    @Enumerated(EnumType.STRING)
    private DictionaryCollection language;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(frontSide, card.frontSide) && Objects.equals(backSide, card.backSide) && Objects.equals(word, card.word) && language == card.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(frontSide, backSide, word, language);
    }

    @Override
    public String toString() {
        return "Card{" +
                "frontSide='" + frontSide + '\'' +
                ", backSide='" + backSide + '\'' +
                ", word=" + word +
                ", language=" + language +
                '}';
    }
}
