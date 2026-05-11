package com.az.nida.platform.common.util;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Year;

@Component
public class UniqueIdGenerator {

    private static final String CHARACTERS = "0123456789";
    private static final int RANDOM_PART_LENGTH = 4;
    private static final SecureRandom RANDOM = new SecureRandom();


    public String generate() {
        int year = Year.now().getValue();
        String randomPart = generateRandomDigits();
        return String.format("NDA-%d-%s", year, randomPart);
    }

    private String generateRandomDigits() {
        StringBuilder sb = new StringBuilder(RANDOM_PART_LENGTH);
        for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}