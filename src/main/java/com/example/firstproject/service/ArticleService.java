package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }
    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm articleForm) {
        Article article = articleForm.toEntity();           // dto ➙ Entity로 변환한 후 article에 저장
        if (article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);             // article DB에 저장
    }

    public Article update(Long id, ArticleForm articleForm) {
        // 1) DTO ➙ Entity로 변환
        Article article = articleForm.toEntity();
        log.info("id: {}, article: {}", id, article.toString());
        // 2) target 조회
        Article target = articleRepository.findById(id).orElse(null);
        // 3) 잘못된 요청 처리
        if (target == null || id != article.getId()) {
            // 400, 잘못된 요청 응답
            log.info("잘못된 요청입니다. id: {}, article: {}", id, article.toString());
            return null;                                         // 응답은 컨트롤러가 하기 때문에 여기선 null 반환
        }
        // 4) 업데이트 및 정상(200) 응답 받기
        target.patch(article);          // 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target);       // 수정 내용 DB에 최종 저장
        return updated;                                         // 응답은 컨트롤러가 하기 때문에 여기선 수정 데이터만 반환
    }

    public Article delete(Long id) {
        // 1) 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        // 2) 잘못된 요청 처리
        if (target == null) {
            return null;
        }
        // 3) 대상 삭제
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> articleForms) {
        /**
         * 1. dto 묶음을 엔티티 묶음으로 변환
         * 2. 엔티티 묶음을 DB에 저장
         * 3. 강제 예외 발생시키기
         * 4. 결과 값 반환하기
         */
        List<Article> articleList = articleForms.stream()
                .map(articleForm -> articleForm.toEntity())
                .collect(Collectors.toList());                  // 1.
        articleList.stream()
                .forEach(article -> articleRepository.save(article));       // 2.
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제에 실패했습니다"));      // 3.
        return articleList;         // 4.

    }
}
