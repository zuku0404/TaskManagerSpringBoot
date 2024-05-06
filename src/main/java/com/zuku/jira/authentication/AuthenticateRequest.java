package com.zuku.jira.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class
AuthenticateRequest {
    private String login;
    private String password;
}
