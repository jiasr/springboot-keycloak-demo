package cn.xdevops.demo.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public String home() {
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }

    @GetMapping("/TESTJSON")
    @ResponseBody
    public String jsonView(HttpServletRequest request) throws ServletException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "NAME");
        logger.info("TESTJSON");

        return String.valueOf(jsonObject);
    }



}
