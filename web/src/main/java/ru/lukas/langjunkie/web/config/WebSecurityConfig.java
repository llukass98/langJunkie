package ru.lukas.langjunkie.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.lukas.langjunkie.web.service.UserService;

import javax.sql.DataSource;

/**
 * @author Dmitry Lukashevich
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    /*// language=SQL
    private final String SQL_AUTHORITIES = "SELECT username, role FROM \"user\" " +
            "WHERE username = ?";

    // language=SQL
    private final String SQL_BY_USERNAME = "SELECT username, password, is_active " +
            "FROM \"user\" WHERE username = ?";*/

    public WebSecurityConfig(DataSource dataSource, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    protected void configureGlobal( AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(SQL_BY_USERNAME)
                .authoritiesByUsernameQuery(SQL_AUTHORITIES);
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/signup")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
                .defaultSuccessUrl("/")
                .failureUrl("/signin_failure")
                .permitAll()
                .and()
                .logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/signin");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**",
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/fonts/**");
    }
}
