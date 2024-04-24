package com.zuku.jira.helpers;

public enum ActionResultDescription {
    LOGIN_NOT_EXIST ("login not exist"),
    LOGIN_EXIST ("login already exists"),
    NO_USER_LOGGED_IN("some user must be logged in to perform this action"),
    USER_NOT_EXIST("can not find user"),
    INCORRECT_PASSWORD ("password incorrect"),
    FAIL_SAVE_CHANGES_DB("something went wrong with saving changes to the database"),
    SUCCESS(""),
    BOARD_NAME_EXIST ("board name already exists"),
    BOARD_NOT_EXIST ("board not exist"),
    TASK_NAME_EXIST ("task name already exists"),
    TASK_NOT_EXIST ("task not exist");

    private String description;

    ActionResultDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
