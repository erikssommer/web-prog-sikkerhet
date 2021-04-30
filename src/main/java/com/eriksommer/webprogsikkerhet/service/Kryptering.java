package com.eriksommer.webprogsikkerhet.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Kryptering {

    BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder(15);

    public boolean sjekkPassord(String passord, String hashPassword) {

        return bCrypt.matches(passord, hashPassword);
    }

    public String krypterPassord(String passord) {

        return bCrypt.encode(passord);
    }

}
