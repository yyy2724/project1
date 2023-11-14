package com.api.shop_project.exception;

import lombok.Getter;

@Getter
public class InternalServerError extends ShopException{

    private static final String MESSAGE = "서버 내부 오류가 발생했습니다. 관리자에게 문의 부탁드립니다.";

    public InternalServerError() {
        super(MESSAGE);
    }

    public InternalServerError(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}
