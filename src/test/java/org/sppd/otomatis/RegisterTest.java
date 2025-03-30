package org.sppd.otomatis;

import org.junit.jupiter.api.Test;
import org.sppd.otomatis.entity.Users;
import org.sppd.otomatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class RegisterTest {



    @Test
    public void registerTestSuccess(){
        Users users = Users.builder()
                .username("test")
                .name("Viandra STefani")
                .password("Testing")
                .build();


    }
}
