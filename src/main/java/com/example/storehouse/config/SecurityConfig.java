package com.example.storehouse.config;


import static com.example.storehouse.util.UsersUtil.PASSWORD_ENCODER;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.endpoints.base_path}" + "${app.endpoints.authentication.base_url}" + "/login")
    private String authenticationUrl;

    @Value("${management.endpoints.web.base-path}")
    private String appHealthCheckUrl;

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
        // other public endpoints of your API may be appended to this array
    };

    @Qualifier("UserDetailsServiceImplementation")
    private final UserDetailsService userDetailsService;

    private final JwtConfig jwtConfigurer;

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
            .antMatchers(appHealthCheckUrl + "/**").permitAll()
            .antMatchers(authenticationUrl).anonymous()
            .anyRequest()
            .authenticated()
            .and()
            .apply(jwtConfigurer);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(PASSWORD_ENCODER);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
