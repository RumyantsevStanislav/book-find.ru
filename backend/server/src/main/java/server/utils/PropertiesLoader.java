package server.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.PathResource;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties loadProperties() {
        Properties configuration = new Properties();
        try (InputStream inputStream = PropertiesLoader.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            configuration.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Exception on read application.properties");
        }
        return configuration;
    }

    public static Map<String, Object> loadYamlProperties() {
        Yaml yaml = new Yaml();
        InputStream inputStream = PropertiesLoader.class.getClassLoader()
                .getResourceAsStream("application.yaml");
        return yaml.load(inputStream);
    }

    public static Properties loadYamlPropertiesBySpring(){
        try {
            File file = ResourceUtils.getFile("classpath:application.yaml");
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(new PathResource(file.toPath()));
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (FileNotFoundException exception) {
            throw new RuntimeException("Exception on read application.yaml");
        }
    }
}
