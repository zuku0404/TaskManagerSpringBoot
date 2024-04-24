package com.zuku.jira.domain.cypher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


public interface IEncryptionService {
    String encrypt(String text);
    String decrypt(String text);
}
