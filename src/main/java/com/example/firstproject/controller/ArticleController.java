package com.example.firstproject.controller;


import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Slf4j      // 로깅 기능 위한 어노테이션
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm articleForm) {
        log.info(articleForm.toString());
//        System.out.println(articleForm.toString());
        /**
         * 1. DTO를 엔터티로 변환
         * 2. Repository로 Entity를 DB에 저장
         */
        Article article = articleForm.toEntity();      // 1.
        log.info(article.toString());
//        System.out.println(article.toString());      DTO가 Entity로 변환되는지 확인 출력
        Article saved = articleRepository.save(article);   // 2.
        log.info(saved.toString());
//        System.out.println(saved.toString());        article이 DB에 저장되는지 확인 출력
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")       // article url에서 {id} 조회 요청
    public String show(@PathVariable Long id, Model model) {     // id 값을 매개변수로 받기
        /**
         * PathVariable : URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져오는
         * 어노테이션
         */
        log.info("id = " + id);            // id 값을 문자열로 변환 후 "id="라는 문자열과 결합하여 로그 메세지 만들기
        /**
         * 1) id 조회 -> 데이터 가져오기
         * 2) 모델에 데이터 등록
         * 3) 뷰 페이지 반환
         */
       Article articleEntity = articleRepository.findById(id).orElse(null);       // 1.
        model.addAttribute("article", articleEntity);                       // 2.
        return "articles/show";                                                   // 3.
    }

    @GetMapping("/articles")
    public String index(Model model) {
        /**
         * 1. 모든 데이터 가져오기
         * 2. 모델에 데이터 등록
         * 3. 뷰 페이지 설정
         */
        List<Article> articleList = articleRepository.findAll();                // 1.
        model.addAttribute("articleList", articleList);             // 2.
        return "articles/index";                                                // 3.
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {                   // 2. @PathVariable 이용해서 id를 매개변수로 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);   // 1. DB에서 수정할 데이터 가져오기
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }

}