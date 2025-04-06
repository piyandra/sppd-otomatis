package org.sppd.otomatis;

import org.junit.jupiter.api.Test;
import org.sppd.otomatis.controller.UserController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@WebMvcTest(UserController.class)
class SppdAutomaticApplicationTests {

    @Test
    void contextLoads() {
    }

}
