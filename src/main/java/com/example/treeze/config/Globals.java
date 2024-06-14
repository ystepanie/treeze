package com.example.treeze.config;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Data
@Setter(AccessLevel.NONE)
@Component
public class Globals {
    final Logger log = LogManager.getLogger(getClass());

    private boolean local;
    private boolean dev;

    private String apiKey;
    private String apiUrl;

    @Autowired
    @Getter(AccessLevel.NONE)
    private ResourceLoader resourceLoader;

    @Getter(AccessLevel.NONE)
    private Properties properties;

    @PostConstruct
    public void init() {
        String active = "local"; // 현재 local로 고정
        String resourcePath = String.format("classpath:global/globals-%s.properties", active);
        log.info("resourcePath : {}", resourcePath);
        try {
            Resource resource = resourceLoader.getResource(resourcePath);
            properties = PropertiesLoaderUtils.loadProperties(resource);

            apiKey = getValue("openai.api.key");
            apiUrl = getValue("openai.api.url");
        } catch (Exception e) {
            log.debug("resourcePath : {} 파일을 불러오는 중 오류가 발생했습니다.", resourcePath);
            log.error("error", e);
        }
    }

    private String getValue(String key) {
        String value = properties.getProperty(key);
        if (StringUtils.isNotEmpty(value)) {
            return value.trim();
        }
        return null;
    }
}
