package com.example.UCESAPI.auth.config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"JWT_SECRET_KEY=testSecretKey"})
public class WebSecurityTest {

    @Autowired
    private WebSecurity webSecurity;

    @Test
    public void testWebSecurityConfiguration() {
        assertThat(webSecurity).isNotNull();
        assertThat(webSecurity).isInstanceOf(WebSecurityConfigurerAdapter.class);
    }

}
