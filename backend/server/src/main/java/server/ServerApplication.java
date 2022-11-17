package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    // TODO: 17.11.2022 figure out how to get environment properties
    @Override
    public void run(String... args) throws Exception {
        logger.info("{}", env.getProperty("JAVA_HOME"));
        logger.info("{}", env.getProperty("app.name"));
    }
    //$ mvn -q spring-boot:run

}
