package com.example.storehouse;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = StorehouseApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class StorehouseApplicationTests {

    @Value("${management.endpoints.web.base-path}")
    private String actuatorsPath;

    @Value("${info.app.version}")
    private String appVersion;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    @SneakyThrows
    void shouldHaveHealthCheckHandlerAndCheckIsUP() {
        mvc.perform(get(actuatorsPath + "/health"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("UP"))
        ;
    }

    @Test
    @SneakyThrows
    void shouldHaveInfoHandlerWithAppVersionAndApiVersion() {
        mvc.perform(get(actuatorsPath + "/info"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.app.version").value(appVersion))
            .andExpect(jsonPath("$.api.*.version").isNotEmpty())
        ;
    }

}
