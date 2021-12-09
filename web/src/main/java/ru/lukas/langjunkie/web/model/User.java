package ru.lukas.langjunkie.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Enter correct email address")
    private String email;

    @NotBlank(message = "Full name field is mandatory")
    @Column(name = "full_name")
    private String fullName;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Card> cards;

    private String role;

    @Column(name = "is_active")
    private Boolean isActive;
}
