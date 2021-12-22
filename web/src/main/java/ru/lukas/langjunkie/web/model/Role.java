package ru.lukas.langjunkie.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Dmitry Lukashevich
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role implements GrantedAuthority {

    public static String ROLE_PREFIX = "ROLE_";

    @Id
    private Long id;

    private String name;

    @Override
    public String getAuthority() {
        return ROLE_PREFIX + getName();
    }

    public void setAuthority(String authority) {
        name = authority;
    }
}
