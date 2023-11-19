package com.example.trend_project.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class WebErrorController implements ErrorController {

    @GetMapping("/error")
    public String handlerError(HttpServletRequest request, HttpSession session, Model model) {
        var user = session.getAttribute("user");

        model.addAttribute("user", user);

        //들어온 에러의 status 저장
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusCode != null) {
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404error";
            }
        }
        return "error/error";
    }
}
