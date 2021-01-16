package com.example.storehouse.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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

    private static final String[] AUTH_WHITELIST = {
        // -- swagger ui
        "/**",
        //"/api/v1/docs/**",
        //"/swagger-resources",
        //"/swagger-resources/**",
        //"/configuration/ui",
        //"/swagger-ui.html/**",
        //"/swagger-ui/index.html?configUrl=/api/v1/docs/swagger-config",
        //"/webjars/**",
        "/check/info"
        // other public endpoints of your API may be appended to this array
    };

    private final JwtConfigurer jwtConfigurer;

    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // разграничиваем права доступа к элементам сервера.
        // пока тестовый вариант, потом всегда можно подправить
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(AUTH_WHITELIST).permitAll()  // whitelist Swagger UI resources
            .antMatchers(authenticationUrl).permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .apply(jwtConfigurer);
    }

// Не используем, так как подгружаем пользователей из базы.
// Данный вариант был ознакомительным.
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
