package cn.edu.whu.lilab.creativity.config.elasticsearch;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchProperties {

    private String host;
}
