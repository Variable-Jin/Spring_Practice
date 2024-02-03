package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("article_show_all_comment_Query")
    void findByArticleId() {
        {

            /**
             * Case 1: 4번 게시글의 모든 댓글 조회
             * 1) 입력 데이터 조회
             * 2) 실제 데이터
             * 3) 예상 데이터
             * 4) 비교 및 검증
             */
            Long articleId = 4L;
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            Article article = new Article(4L, "가고싶은 곳", "댓글1");
            Comment a = new Comment(1L, article, "kuku", "시라카와고");
            Comment b = new Comment(2L, article, "melon", "타이페이");
            Comment c = new Comment(3L, article, "uto", "New York");
            List<Comment> expected = Arrays.asList(a, b, c);
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력");
        }

        {

            /**
             * Case 1: 4번 게시글의 모든 댓글 조회
             * 1) 입력 데이터 조회
             * 2) 실제 데이터
             * 3) 예상 데이터
             * 4) 비교 및 검증
             */
            Long articleId = 1L;
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            Article article = new Article(1L, "오늘의 운동", "26");
            List<Comment> expected = Arrays.asList();
            assertEquals(expected.toString(), comments.toString(), "1번 댓글 출력 안함");
        }

    }

    @Test
    @DisplayName("article_show_all_nickname_Query")
    void findByNickname() {
        {
            /**
             * Case 1: melon nickname의 모든 댓글 조회
             * 1) 입력 데이터 조회
             * 2) 실제 데이터
             * 3) 예상 데이터
             * 4) 비교 및 검증
             */
            String nickname = "melon";
            List<Comment> comments = commentRepository.findByNickname(nickname);
            Comment a = new Comment(2L, new Article(4L, "가고싶은 곳", "댓글1"), nickname, "타이페이");
            Comment b = new Comment(5L, new Article(5L, "먹고싶은 것", "댓글2"), nickname, "당근케이크");
            Comment c = new Comment(8L, new Article(6L, "사고싶은 것", "댓글3"), nickname, "에어팟맥스");
            List<Comment> expected = Arrays.asList(a, b, c);
            assertEquals(expected.toString(), comments.toString(), "melon nickname의 모든 댓글 조회");
        }
    }
}

