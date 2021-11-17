package com.markerhub.config;

import com.markerhub.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContextStartup implements ApplicationRunner {

    @Autowired
    RedisService redisService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 项目启动初始化城市的坐标
        redisService.getCityList().forEach(e ->
                redisService.addLocation(e.getName(), e.getLng(), e.getLat())
        );

        log.info("城市坐标初始化成功～～");
    }
}
