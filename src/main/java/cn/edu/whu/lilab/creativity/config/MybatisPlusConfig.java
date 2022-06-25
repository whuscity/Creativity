package cn.edu.whu.lilab.creativity.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.ManagedBean;

@Configuration
@MapperScan("cn.edu.whu.lilab.creativity.mapper")
public class MybatisPlusConfig {
}
