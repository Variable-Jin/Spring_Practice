package com.example.firstproject.controller;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j

public class ArticleApiController {

    @Autowired
    private ArticleRepository articleRepository;
    /**
     * GET
     * POST
     * PATCH
     * DELETE
     */
    // 모든 게시글 조회
    @GetMapping("/api/articles")
    public List<Article> index() {         // index 메서드 정의 ( Article 묶음 반환 ➙ List )
        return articleRepository.findAll();
    }

    // 특정 게시글 조회
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {        // index ➙ show 메서드로 변경, URI id를 매개변수로 받기
        return articleRepository.findById(id).orElse(null);
    }

    // 게시글 생성
    @PostMapping("/api/articles")
    // REST API 데이터 생성 시
    // ➙ JSON 데이터를 받으려면 어노테이션(@RequestBody)를 추가해야 DTO를 매개변수로 받을 수 있음
    public Article create(@RequestBody ArticleForm articleForm) {   // create 메서드 정의
        Article article = articleForm.toEntity();       // DTO ➙ Entity로 변환
        return articleRepository.save(article);
    }

    // 게시글 UPDATE
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm articleForm) {
        // 1) DTO ➙ Entity로 변환
        Article article = articleForm.toEntity();
        log.info("id: {}, article: {}", id, article.toString());
        // 2) target 조회
        Article target = articleRepository.findById(id).orElse(null);
        // 3) 잘못된 요청 처리
        if (target == null || id != article.getId()) {
            // 400, 잘못된 요청 응답
            log.info("잘못된 요청입니다. id: {}, article: {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 4) 업데이트 및 정상(200) 응답 받기
        target.patch(article);          // 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target);       // 수정 내용 DB에 최종 저장
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // 게시글 DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        // 1) 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        // 2) 잘못된 요청 처리
        if (target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 3) 대상 삭제
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
