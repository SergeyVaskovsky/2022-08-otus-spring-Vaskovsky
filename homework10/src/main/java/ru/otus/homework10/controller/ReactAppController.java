package ru.otus.homework10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReactAppController {

    /*@RequestMapping("/api/**")
    public ApiResult api(HttpServletRequest request, HttpServletResponse response){
        return apiProxy.proxy(request, response);
    }

    @RequestMapping(value="/**", method=GET)
    public String index(){
        return "index.html";
    }*/

    @RequestMapping(value = {
            "/",
            "/book",
            "/book/new",
            "/book/bundle.js",
            "/book/bundle.min.js"})
    public String getIndex(HttpServletRequest request) {
        if (request.getRequestURI().endsWith("bundle.js")) {
            return "bundle.js";
        } else if (request.getRequestURI().endsWith("bundle.min.js")) {
            return "./bundle.min.js";
        } else {
            return "/index.html";
        }
    }

}
