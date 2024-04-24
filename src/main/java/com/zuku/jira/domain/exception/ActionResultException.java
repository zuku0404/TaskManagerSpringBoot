package com.zuku.jira.domain.exception;

import com.zuku.jira.helpers.ActionResult;

public class ActionResultException extends Exception {
    public ActionResultException(ActionResult message) {
        super(message.getStatus() + " " + message.getMsg());
    }
}
