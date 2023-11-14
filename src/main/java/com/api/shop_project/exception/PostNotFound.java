package com.api.shop_project.exception;

import lombok.Getter;

@Getter
public class PostNotFound extends ShopException {

    private static final String MESSAGE = "존재하지 않는 페이지입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
