package com.example.storehouse.config;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Модуль для работы с Hibernate, убираем ошибку при сериализации объектов,
    // имеющих при загрузке LAZY-связи (fetch = FetchType.LAZY)
    // https://stackoverflow.com/questions/21708339/avoid-jackson-serialization-on-non-fetched-lazy-objects/21760361#21760361
    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

}
