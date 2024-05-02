package ru.boyurig.buysell.configurations;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.boyurig.buysell.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
               .authorizeHttpRequests(authz -> authz
                       .requestMatchers("/login",
                               "/",
                               "/index",
                               "/registration",
                               "/products",
                               "/product/{id}",
                               "/user/**",
                               "/css/**",
                               "/js/**",
                               "/images/**").permitAll()
                       .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                       .requestMatchers("/user, /user/**").hasRole("USER")
                       .anyRequest().authenticated()
               )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"));

        return http.build();
    }

//    @Bean
//    AuthenticationManager authentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//        return auth.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }


}
