package com.example.firstproject.controller;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Service

public class ArticleApiController {

    @Autowired
    private ArticleService articleService;

    /**
     * GET
     * POST
     * PATCH
     * DELETE
     */
    // 모든 게시글 조회
    @GetMapping("/api/articles")
    public List<Article> index() {         // index 메서드 정의 ( Article 묶음 반환 ➙ List )
        return articleService.index();
    }

    // 특정 게시글 조회
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {        // index ➙ show 메서드로 변경, URI id를 매개변수로 받기
        return articleService.show(id);
    }

    // 게시글 생성
    @PostMapping("/api/articles")
    // REST API 데이터 생성 시
    // ➙ JSON 데이터를 받으려면 어노테이션(@RequestBody)를 추가해야 DTO를 매개변수로 받을 수 있음
    public ResponseEntity<Article> create(@RequestBody ArticleForm articleForm) {   // create 메서드 정의
        Article created = articleService.create(articleForm);       // 서비스로 게시글 작성
        // article ➙ created 객체 이름 변경
        return (created != null) ?                                  // 생성하면 정상, 실패하면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    // 게시글 UPDATE
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm articleForm) {
        Article updated = articleService.update(id, articleForm);
        return (updated != null) ?                                  // 서비스를 통해 게시글 수정
                ResponseEntity.status(HttpStatus.OK).body(updated) :    // 수정되면 정상, 안되면 오류 반환
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 게시글 DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ?                                  // 서비스를 통해 게시글 삭제
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :  // 삭제 결과에 따라 응답 처리
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest
            (@RequestBody List<ArticleForm> articleForms) {
        List<Article> createdList = articleService.createArticles(articleForms);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
