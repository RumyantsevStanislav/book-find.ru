package server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ServerApplicationTests {
    @Autowired
    private ServerApplication serverApplication;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(serverApplication);
    }

}
