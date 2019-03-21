package com.ytzl.gotrip;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.ytzl.gotrip.mapper.GotripHotelMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackageClasses = GotripHotelMapper.class)
@EnableDubboConfiguration
public class HotelProviderApp {

    public static void main(String[] args) {
        SpringApplication.run(HotelProviderApp.class,args);
    }
}
