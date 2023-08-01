package com.pifrans.ecommerce;

import com.pifrans.ecommerce.support.DatabaseService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EcommerceApplicationTestsContext {

    @Autowired
    private DatabaseService databaseService;


    @PostConstruct
    public void init() {
        try {
            databaseService.create();
        } catch (Exception ignored) {

        }
    }
}
