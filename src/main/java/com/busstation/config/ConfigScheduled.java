package com.busstation.config;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ConfigScheduled {
    @Scheduled(fixedDelay = 1000000000) // thực hiện sau mỗi 1 giây
    public void doSomething() {

    }
}
