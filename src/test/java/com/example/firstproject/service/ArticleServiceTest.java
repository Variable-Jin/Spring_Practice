package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest     // springboot랑 연동해 test
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;      // Service 객체 주입

    @Test
    /**
     * 1. 예상데이터
     * 2. 실제데이터
     * 3. 비교 및 검증
     */

    void index() {

        /** 1. data.sql 객체로 저장
         * Arrays.asList() 메서드를 이용해서 정적 리스트로 만들어 반환
         */
        Article a = new Article(1L, "오늘의 운동", "26");
        Article b = new Article(2L, "오늘의 일기", "카페를 두 군데나 다녀왔다 ^3^");
        Article c = new Article(3L, "오늘의 저녁메뉴", "바삭한 치킨");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));

        /** 2. articleService.index() 메서드 호출
         * ➙ 결과를 List<Article> 타입의 articles에 받아오기
         * 모든 게시글 조회 요청하고 결과로 반환되는 게시글의 묶음을 받아오기
         */
        List<Article> articles = articleService.index();


        /** 3. assertEquals(x, y) 메서드를 이용해 일치하면 JUnit이 통과
         * ➙ x에는 예상 데이터를 문자열로 변환한 expected.toString() 넣기
         * ➙ y에는 실제 데이터를 문자열로 변환한 articles.toString() 넣기
         */
        assertEquals(expected.toString(), articles.toString());

    }

    @Test
    void show_success_exist_id_input() {
        // 1. 예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "오늘의 운동", "26");
        // 2. 실제 데이터
        Article article = articleService.show(id);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    void show_failed_notexist_id_input() {
        // 1. 예상 데이터
        // id = -1인 값은 없기 때문에 db에서 조회시 null값 return
        Long id = -1L;
        Article expected = null;
        // 2. 실제 데이터
        Article article = articleService.show(id);
        // 3. 비교 및 검증
        // null값 return 이므로 toString() 호출 불가
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_success_title_content_dto_input() {
        // 1. 예상 데이터
        // 새로운 게시글 생성
        String title = "오늘의 기분";
        String content = "50점";
        ArticleForm articleForm = new ArticleForm(null, title, content);    // dto 생성
        Article expected = new Article(4L, title, content);
        // 2. 실제 데이터
        Article article = articleService.create(articleForm);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_failed_with_id_dto_input() {
        // 1. 예상 데이터
        // 새로운 게시글 생성
        Long id = 4L;
        String title = "오늘의 기분";
        String content = "50점";
        ArticleForm articleForm = new ArticleForm(id, title, content);    // dto 생성
        Article expected = null;
        // 2. 실제 데이터
        Article article = articleService.create(articleForm);
        // 3. 비교 및 검증
        assertEquals(expected, article);
    }


    // update 1
    @Test
    @Transactional
    void update_success_exist_id_title_content_dto() {
        // 1. 예상 데이터
        Long id = 1L;
        String title = "Today's 운동 ";
        String content = "필라테스";
        ArticleForm articleForm = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, content);
        // 2. 실제 데이터
        Article article = articleService.update(id, articleForm);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    // update 2
    @Test
    @Transactional
    void update_success_exist_id_title_dto() {
        // 1. 예상 데이터
        Long id = 1L;
        String title = "운동하는날";
        String content = null;
        ArticleForm articleForm = new ArticleForm(id, title, content);
        Article expected = new Article(1L, "운동하는날", "26");          // ?
        // 2. 실제 데이터
        Article article = articleService.update(id, articleForm);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_failed_notexist_id_dto() {
        // 1. 예상 데이터
        Long id = -1L;
        String title = "Today's 운동 ";
        String content = "필라테스";
        ArticleForm articleForm = new ArticleForm(id, title, content);
        Article expected = null;
        // 2. 실제 데이터
        Article article = articleService.update(id, articleForm);
        // 3. 비교 및 검증
        assertEquals(expected, article);
    }


    // delete
    @Test
    @Transactional
    void delete_success_exist_id_input() {
        // 1. 예상 데이터
        Long id = 2L;
        Article expected = new Article(id, "오늘의 일기", "카페를 두 군데나 다녀왔다 ^3^");
        // 2. 실제 데이터
        Article article = articleService.delete(id);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_failed_notexist_id_input() {
        // 1. 예상 데이터
        Long id = -2L;
        Article expected = null;
        // 2. 실제 데이터
        Article article = articleService.delete(id);
        // 3. 비교 및 검증
        assertEquals(expected, article);
    }
}