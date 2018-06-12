package com.excilys.computerdatabase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.computerdatabase.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/static/images/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/static/css/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated().and().authorizeRequests().antMatchers("/login")
                .permitAll().and().formLogin().loginPage("/login").loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true).permitAll().and().logout().logoutSuccessUrl("/login").permitAll()
                .and().csrf().disable();
    }

}