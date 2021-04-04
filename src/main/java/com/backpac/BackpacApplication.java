package com.backpac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

import java.util.Iterator;

@Slf4j
@SpringBootApplication(scanBasePackages = "com")
public class BackpacApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BackpacApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Iterator<String> itr = args.getOptionNames().iterator();
        while(itr.hasNext()) {
            String key = itr.next();
            Object val = args.getOptionValues(key);
            log.info("key:" + key);
        }
    }
}
