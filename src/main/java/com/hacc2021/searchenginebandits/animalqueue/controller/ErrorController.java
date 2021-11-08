package com.hacc2021.searchenginebandits.animalqueue.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor
@Slf4j
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(final HttpServletRequest request) {
        final Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        log.error("Catched error {}", statusCode);

        final ModelAndView mav = new ModelAndView("error");
        mav.addObject("statusCode", statusCode);
        mav.setViewName("error");
        return mav;
    }
}
