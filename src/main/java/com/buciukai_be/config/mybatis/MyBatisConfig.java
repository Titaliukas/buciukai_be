package com.buciukai_be.config.mybatis;

import com.buciukai_be.AppEntry;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackageClasses = AppEntry.class)
public class MyBatisConfig {}
