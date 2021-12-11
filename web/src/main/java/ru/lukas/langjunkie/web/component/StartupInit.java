package ru.lukas.langjunkie.web.component;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import ru.lukas.langjunkie.web.model.Role;
import ru.lukas.langjunkie.web.model.User;
import ru.lukas.langjunkie.web.repository.RoleRepository;
import ru.lukas.langjunkie.web.repository.UserRepository;

import javax.annotation.PostConstruct;

/**
 * @author Dmitry Lukashevich
 */
@RequiredArgsConstructor
@Component
public class StartupInit {

    private final PlatformTransactionManager transactionManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${langjunkie.admin.default.username}")
    private String adminUsername;
    @Value("${langjunkie.admin.default.password}")
    private String adminPassword;

    @PostConstruct
    public void init() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                addRoles();
                addAdmin();
            }
        });
    }

    private void addRoles() {
        Role adminRole = roleRepository.findByName(RoleRepository.ROLE_ADMIN).orElseGet(Role::new);
        Role userRole = roleRepository.findByName(RoleRepository.ROLE_USER).orElseGet(Role::new);

        if (adminRole.getId() == null) {
            adminRole.setId(1L);
            adminRole.setAuthority(RoleRepository.ROLE_ADMIN);
            roleRepository.save(adminRole);
        }

        if (userRole.getId() == null) {
            userRole.setId(2L);
            userRole.setAuthority(RoleRepository.ROLE_USER);
            roleRepository.save(userRole);
        }
    }

    private void addAdmin() {
        User admin = userRepository.findByUsername(adminUsername);

        if (admin == null) {
            Role role = roleRepository.findByName(RoleRepository.ROLE_ADMIN).orElseThrow();
            role.setAuthority(RoleRepository.ROLE_ADMIN);

            admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setEmail("admin@admin.com");
            admin.setFullName("Administrator");
            admin.setRole(role);

            userRepository.save(admin);
        }
    }
}
