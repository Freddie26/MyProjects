package org.example.conf;

import org.example.enums.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PERMITTED_URIS = new String[]{
            "/users/register",
            "/presentations/all",
            "/**/swagger-ui.html",
            "/**/swagger-ui/**",
            "/**/v3/api-docs/**"
    };

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                    .antMatchers(PERMITTED_URIS).permitAll()
                    .antMatchers("/register-to-presentation/*").hasRole(Roles.ROLE_LISTENER.getName())
                    .antMatchers("/presentations/**").hasRole(Roles.ROLE_PRESENTER.getName())
                    .antMatchers("/users", "/users/make-presenter/*").hasRole(Roles.ROLE_ADMIN.getName())
                    .antMatchers("/", "/login").permitAll().anyRequest().authenticated()
                .and()
                    .formLogin()
                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/login");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
