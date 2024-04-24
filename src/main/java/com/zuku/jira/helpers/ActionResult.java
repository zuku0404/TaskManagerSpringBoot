package com.zuku.jira.helpers;

public class ActionResult {
    private Status status;
    private String msg;

    public ActionResult(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ActionResult(Status status) {
        this(status, "");
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
