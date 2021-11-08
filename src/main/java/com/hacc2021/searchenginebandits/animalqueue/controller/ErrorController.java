package com.hacc2021.searchenginebandits.animalqueue.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.hacc2021.searchenginebandits.animalqueue.service.OwnerService;
import lombok.RequiredArgsConstructor;

@Controller
@EnableAutoConfiguration
@RequiredArgsConstructor
public class ErrorController {

    @GetMapping("/error")
    public String handleError(final HttpServletRequest request, final Model model) {
        request.setAttribute("statusCode", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        return "error";
    }
}
