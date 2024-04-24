package com.zuku.jira.domain.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String loginPattern = "^(?=.*[a-zA-Z])(?=.*[0-9])";
    private static final int loginLength = 6;
    private static final String passwordPattern = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])";
    private static final int passwordLength = 8;
    private static final int boardNameLength = 8;

    public static String loginValidate(String login) {
        long numberOfWhiteSpaceInLogin = login.chars()
                .filter(Character::isWhitespace)
                .count();

        if (numberOfWhiteSpaceInLogin > 0) {
            if (login.length() < loginLength) {
                return ValidationDescription.LOGIN_LENGTH.getDescription();
            } else {
                Pattern pattern = Pattern.compile(loginPattern);
                Matcher matcher = pattern.matcher(login);
                if (matcher.matches()) {
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

        if (numberOfWhiteSpaceInPassword > 0) {
            if (password.length() < passwordLength) {
                return ValidationDescription.PASSWORD_LENGTH.getDescription();
            } else {
                Pattern pattern = Pattern.compile(passwordPattern);
                Matcher matcher = pattern.matcher(password);
                if (matcher.matches()) {
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
        if (name.length() < boardNameLength) {
            return ValidationDescription.BOARD_LENGTH.getDescription();
        } else {
            return ValidationDescription.BOARD_SUCCESS.getDescription();
        }
    }
}
