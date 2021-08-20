package ru.dromran.testtz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class TesttzApplication {

    public static void main(String[] args) {
        SpringApplication.run(TesttzApplication.class, args);
        Thread.setDefaultUncaughtExceptionHandler(TesttzApplication::logUncaughtException);
    }

    private static void logUncaughtException(Thread thread, Throwable error) {
        log.error("Unhandled exception caught in {}. Message: {}", thread.getName(), error.getMessage(), error);
    }

}
