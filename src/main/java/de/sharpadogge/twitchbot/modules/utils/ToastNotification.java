package de.sharpadogge.twitchbot.modules.utils;

public class ToastNotification {

    private String type;

    private String title;

    private String msg;

    public ToastNotification(String type, String title, String msg) {
        this.type = type;
        this.title = title;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
