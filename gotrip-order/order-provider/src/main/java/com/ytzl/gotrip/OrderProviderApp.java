package com.ytzl.gotrip;

import com.ytzl.gotrip.mapper.GotripOrderMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackageClasses = GotripOrderMapper.class)

public class OrderProviderApp {


}
