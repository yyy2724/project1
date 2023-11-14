package com.api.shop_project.exception;

import lombok.Getter;

@Getter
public class InvalidRequest extends ShopException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public String filedName;
    public String message;

    public InvalidRequest() {super(MESSAGE);}

    public InvalidRequest(String filedName, String message) {
        super(MESSAGE);
        addValidation(filedName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
