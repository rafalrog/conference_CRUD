package com.sda.conference_room.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/api/organization").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/conferenceroom/**").authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll().and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .cors()
                .and()
                .csrf()
                .disable();
        http.headers().frameOptions().disable();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
