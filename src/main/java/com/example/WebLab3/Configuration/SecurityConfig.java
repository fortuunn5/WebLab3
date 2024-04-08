package com.example.WebLab3.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${security.logins}")
    private List<String> logins;
    @Value("${security.passwords}")
    private List<String> passwords;
    @Value("${security.roles}")
    private List<String> roles;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        for(int i=0; i< logins.size(); i++) {
            var user = User.withUsername(logins.get(i))
                    .password("{noop}" + passwords.get(i))
                    .roles(roles.get(i))
                    .build();
            inMemoryUserDetailsManager.createUser(user);
        }
        return inMemoryUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(x-> x.defaultSuccessUrl("/projects",true))
                //.httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(x -> x
                        .requestMatchers(HttpMethod.GET, "/projects/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/projects/{projectId}/tasks/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/projects/{projectId}/tasks/").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/projects/{projectId}/tasks/").authenticated()

                      /*  .requestMatchers(HttpMethod.POST, "/projects/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/projects/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/projects/**").hasRole("ADMIN")*/
                        //.requestMatchers(new AntPathRequestMatcher("/projects"))
                        //.requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                        //.requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                        //.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                        //.requestMatchers(HttpMethod.GET).authenticated()
                        .anyRequest().hasRole("ADMIN"))

                .build();
    }


}
