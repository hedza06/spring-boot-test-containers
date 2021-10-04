package com.hedza06.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class TestcontainersApplicationTests {

    @Test
    void contextLoads()
    {
        log.info("Context is loaded!");
        assertThat(true).isTrue();
    }
}
