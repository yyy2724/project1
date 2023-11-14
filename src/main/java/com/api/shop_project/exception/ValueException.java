package com.api.shop_project.exception;

import lombok.Getter;

@Getter
public class ValueException extends ShopException {

    private static final String MESSAGE = "잘못된 요청입니다. 고객센터의 문의바랍니다.";

    public ValueException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}
