package ru.lukas.langjunkie.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;

/**
 * @author Dmitry Lukashevich
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @OneToMany(mappedBy = "user")
    private List<Card> cards;

    @Column(columnDefinition = "varchar default 'ROLE_USER'")
    private String role;

    @Column(columnDefinition = "boolean default true", name = "is_active")
    private Boolean isActive;
}
