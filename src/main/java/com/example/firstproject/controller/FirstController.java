package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller     // 어노테이션 -> package import

public class FirstController {

    @GetMapping("/hi")      // 특정 URL("/hi")의 GET 요청 처리 어노테이션
    public String niceToMeetYou(Model model) {
        model.addAttribute("noun", "Java");    // model 객체 "noun"값 웹 브라우저로 출력
        return "greetings";     // greetings.mustache 파일 반환
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("noun", "SpringBoot");
        return "goodbye";
    }
}