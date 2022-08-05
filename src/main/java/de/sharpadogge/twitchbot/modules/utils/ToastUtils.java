package de.sharpadogge.twitchbot.modules.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ToastUtils {

    public static ResponseEntity<ToastResponse<Object>> createResponse(Object body, HttpStatus status, ToastNotification notification) {
        ToastResponse<Object> response = new ToastResponse<>(body, notification);
        return new ResponseEntity<>(response, status);
    }
}
