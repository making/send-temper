package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@EnableScheduling
public class SendTemperApplication {
    @Autowired
    SendTemperConfig config;
    RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication.run(SendTemperApplication.class, args);
    }

    @Scheduled(fixedRate = 3_000)
    void send() throws InterruptedException, TimeoutException, IOException {
        ProcessResult result = new ProcessExecutor()
                .command(config.getCommand())
                .timeout(10, TimeUnit.SECONDS)
                .readOutput(true)
                .execute();
        if (result.getExitValue() == 0) {
            restTemplate.exchange(RequestEntity
                    .post(config.getTarget())
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(result.outputUTF8()), Void.class);
        }
    }
}
