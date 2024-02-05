package com.example.firstproject.controller;


import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import com.example.firstproject.service.CommentService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j      // 로깅 기능 위한 어노테이션
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

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
        List<CommentDto> commentsDtos = commentService.comments(id);
        model.addAttribute("article", articleEntity);                       // 2.
        model.addAttribute("commentDtos", commentsDtos);      // comment 목록 model 등록
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

    @PostMapping("/articles/update")
    public String update(ArticleForm articleForm) {             // DTO 매개변수로 받기
        Article articleEntity = articleForm.toEntity();         // 1. DTO → Entity로 변환
        log.info(articleForm.toString());
        /**
         * 2. 엔티티를 DB에 저장하기
         * 2-1) DB에서 기존 데이터 가져오기
         * 2-2) 기존 데이터 값 갱신 (Entity DB 저장)
         */
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);    // 2-1)
        if (target != null) {                                                                    // 2-2)
            articleRepository.save(articleEntity);
        }
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");
        /**
         * 1. 삭제 대상 가져오기
         * 2. 대상 엔티티 삭제
         * 3. 결과 페이지 리다이렉트
         */
        Article target = articleRepository.findById(id).orElse(null);       // 1.
        log.info(target.toString());
        if (target != null) {                                                     // 2.
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        return "redirect:/articles";                                              // 3.
    }


}