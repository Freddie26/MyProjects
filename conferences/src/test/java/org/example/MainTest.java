package org.example;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MainTest {

    @Value("${spring.application.name}")
    private String appName;

    @Test
    public void contextLoads() {
        Assertions.assertThat(appName).isNotEmpty();
    }

}