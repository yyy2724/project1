package com.api.shop_project.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // 에러 발생 시 표시할 커스텀 에러 페이지 뷰 이름을 반환
        return "error/404";  // 여기서 "error/404"는 에러 페이지의 Thymeleaf 템플릿 경로입니다.
    }
}
