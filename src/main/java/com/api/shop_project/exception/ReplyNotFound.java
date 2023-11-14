package com.api.shop_project.exception;

import lombok.Getter;

@Getter
public class ReplyNotFound extends ShopException {

    private static final String MESSAGE = "댓글이 존재하지 않습니다.";

    public ReplyNotFound() {
        super(MESSAGE);
    }

    public ReplyNotFound(String message, Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
