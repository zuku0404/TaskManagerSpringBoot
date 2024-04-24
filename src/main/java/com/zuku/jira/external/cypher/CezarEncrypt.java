package com.zuku.jira.external.cypher;

import com.zuku.jira.domain.cypher.IEncryptionService;
import org.springframework.stereotype.Service;

@Service
public class CezarEncrypt implements IEncryptionService {
    private final int SHIFT = 4;
    private final int NUMBER_SIGNS_IN_ASCII = 127;

    @Override
    public String encrypt(String text) {
        StringBuilder encryptedPassword = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char sign = text.charAt(i);
            if (sign + SHIFT > NUMBER_SIGNS_IN_ASCII) {
                encryptedPassword.append((char) (sign - (NUMBER_SIGNS_IN_ASCII - SHIFT)));
            } else {
                encryptedPassword.append((char) (sign + SHIFT));
            }
        }
        return encryptedPassword.toString();

    }

    @Override
    public String decrypt(String text) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char sign = text.charAt(i);
            if (sign - SHIFT < 0) {
                password.append((char) (sign + (NUMBER_SIGNS_IN_ASCII - SHIFT)));
            } else {
                password.append((char) (sign - SHIFT));
            }
        }
        return password.toString();
    }
}
