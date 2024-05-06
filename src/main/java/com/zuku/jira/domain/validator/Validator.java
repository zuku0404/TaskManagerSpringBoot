package com.zuku.jira.domain.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String LOGIN_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d).+";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).+";
    static final int LOGIN_LENGTH = 6;
    static final int PASSWORD_LENGTH = 8;
    static final int BOARD_NAME_LENGTH = 8;

    private Validator(){}

    public static String loginValidate(String login) {
        long numberOfWhiteSpaceInLogin = login.chars()
                .filter(Character::isWhitespace)
                .count();

        if (numberOfWhiteSpaceInLogin == 0) {
            if (login.length() < LOGIN_LENGTH) {
                return ValidationDescription.LOGIN_LENGTH.getDescription();
            } else {
                Pattern pattern = Pattern.compile(LOGIN_PATTERN);
                Matcher matcher = pattern.matcher(login);
                if (!matcher.matches()) {
                    return ValidationDescription.LOGIN_WITHOUT_NUMBER_AND_LETTER.getDescription();
                } else {
                    return ValidationDescription.LOGIN_SUCCESS.getDescription();
                }
            }
        } else {
            return ValidationDescription.LOGIN_WHITESPACE.getDescription();
        }
    }

    public static String passwordValidate(String password) {
        long numberOfWhiteSpaceInPassword = password.chars()
                .filter(Character::isWhitespace)
                .count();

        if (numberOfWhiteSpaceInPassword == 0) {
            if (password.length() < PASSWORD_LENGTH) {
                return ValidationDescription.PASSWORD_LENGTH.getDescription();
            } else {
                Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
                Matcher matcher = pattern.matcher(password);
                if (!matcher.matches()) {
                    return ValidationDescription.PASSWORD_WITHOUT_NUMBER_AND_LETTER_AND_SPECIAL_CHARACTER.getDescription();
                } else {
                    return ValidationDescription.PASSWORD_SUCCESS.getDescription();
                }
            }
        } else {
            return ValidationDescription.PASSWORD_WHITESPACE.getDescription();
        }
    }

    public static String boardValidator(String name) {
        if (name.length() < BOARD_NAME_LENGTH) {
            return ValidationDescription.BOARD_LENGTH.getDescription();
        } else {
            return ValidationDescription.BOARD_SUCCESS.getDescription();
        }
    }
}
