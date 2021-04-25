package com.wild.spring.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

import javax.sql.DataSource;

@EnableWebSecurity // класс отвественный за конфигурацию Security
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // подключиться к БД через dataSource
        auth.jdbcAuthentication().dataSource(dataSource);

//        UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//        auth.inMemoryAuthentication().
//                withUser(userBuilder
//                        .username("wild")
//                        .password("wild")
//                        .roles("EMPLOYEE")).
//                withUser(userBuilder
//                        .username("elena")
//                        .password("elena")
//                        .roles("HR")).
//                withUser(userBuilder
//                        .username("ivan")
//                        .password("ivan")
//                        .roles("MANAGER", "HR"));

    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // доступ к url имеет несколько пользователей
                .antMatchers("/").hasAnyRole("EMPLOYEE", "MANAGER", "HR")
                .antMatchers("/hr_info").hasRole("HR")
                // доступ к url начинающийся с...  имеет один пользователь
                .antMatchers("/manager_info/**").hasRole("MANAGER")
                // форма логина и пароля будет запрашиваться у всех
                .and().formLogin().permitAll();
    }
}
