package com.yunho.shopping.controller.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.MediaType;

import java.util.Map;

// error.html으로 Spring Boot가 자동으로 렌더링하므로 이 클래스는 현재 필요없음
// 추가적인 특정 예외 처리가 필요할 때 다시 사용할 예정
@Deprecated
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        return showErrorPage();
    }

    @RequestMapping(path = "/error", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView handleError() {
        return showErrorPage();
    }

    private ModelAndView showErrorPage() {
        return new ModelAndView("/error", Map.of());
    }
}
