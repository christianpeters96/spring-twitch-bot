package de.sharpadogge.twitchbot.modules.utils;

public class ToastResponse <T> {

    private T body;

    private ToastNotification notification;

    public ToastResponse() {
    }

    public ToastResponse(T body, ToastNotification notification) {
        this.body = body;
        this.notification = notification;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public ToastNotification getNotification() {
        return notification;
    }

    public void setNotification(ToastNotification notification) {
        this.notification = notification;
    }
}
