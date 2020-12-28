package com.example.storehouse.config;

import com.example.storehouse.model.Permission;
import com.example.storehouse.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(@Qualifier("UserDetailsServiceImplementation") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);

        // разграничиваем права доступа к элементам сервера.
        // пока тестовый вариант, потом всегда можно подправить
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/").permitAll()
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
            .formLogin()
            .defaultSuccessUrl("/api/v1/items");
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


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}
