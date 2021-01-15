package com.example.storehouse.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.endpoints.base_path}" + "${app.endpoints.authentication.base_url}" + "/login")
    String authenticationUrl;

    private final JwtConfigurer jwtConfigurer;

    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        // разграничиваем права доступа к элементам сервера.
        // пока тестовый вариант, потом всегда можно подправить
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers(authenticationUrl).permitAll()
// Если убрать @EnableGlobalMethodSecurity(prePostEnabled = true),
// то настройка выглядит так, как показано ниже
//            .antMatchers(HttpMethod.GET,"/api/v1/**").
//                hasAuthority( Permission.DB_USERS_READ.getPermission())
//            .antMatchers(HttpMethod.POST,"/api/v1/**").
//                hasAuthority(Permission.DB_USERS_WRITE.getPermission())
//            .antMatchers(HttpMethod.PUT, "/api/v1/**").
//                hasAuthority(Permission.DB_USERS_WRITE.getPermission())
//            .antMatchers(HttpMethod.PUT, "/api/v1/**").
//                hasAuthority(Permission.DB_USERS_WRITE.getPermission())
            .anyRequest()
            .authenticated()
            .and()
            .apply(jwtConfigurer);
//            .formLogin()
//            .defaultSuccessUrl("/api/v1/items");
    }

// Не используем, так как подгружаем пользователей из базы.
// Данный вариант был ознокомительным.
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager(
//            User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .authorities(Role.ADMIN.getAuthorities())
//                .build(),
//            User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("user"))
//                .authorities(Role.USER.getAuthorities())
//                .build()
//        );
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


}
