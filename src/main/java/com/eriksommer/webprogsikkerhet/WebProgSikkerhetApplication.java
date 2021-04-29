package com.eriksommer.webprogsikkerhet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// Avhengigheten har en del automatiske sikkerhetsmekanismer som vi ønsker slå av
// slik at vi kan bruke våre egne.
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class WebProgSikkerhetApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebProgSikkerhetApplication.class, args);
    }

}
