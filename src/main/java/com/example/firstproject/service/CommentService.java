package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        /**
         * 1. 댓글 조회
         * 2. Entity ➙ DTO 변환
         * 3. 결과 반환
         */
//        // 1.
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//        // 2.
//        List<CommentDto> commentDtos = new ArrayList<CommentDto>();
//        for (int i = 0; i<comments.size(); i++) {
//            Comment comment = comments.get(i);
//            CommentDto commentDto = CommentDto.createCommentDto(comment);
//            commentDtos.add(commentDto);
//        }
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());

    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto commentDto) {
        /**
         * 1. 게시글 조회 및 예외 발생
         * 2. 댓글 엔티티 생성
         * 3. 댓글 엔티티를 DB에 저장
         * 4. DTO를 변환해 반환
         */
        // 1.
        Article article = articleRepository.findById(articleId)
                .orElseThrow(()-> new IllegalArgumentException("댓글 생성을 실패했습니다" + "대상 게시글이 없습니다."));
        // 2.
        Comment comment = Comment.createComment(commentDto, article);
        // 3.
        Comment created = commentRepository.save(comment);
        // 4.
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto updated(Long id, CommentDto commentDto) {
        Comment target = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글 수정을 실패했습니다" + "대상 댓글이 없습니다."));
        target.patch(commentDto);
        Comment updated = commentRepository.save(target);
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        /**
         * 1. 댓글 조회 및 예외 발생
         * 2. 댓글 삭제
         * 3. 삭제 댓글을 DTO로 변환 및 반환
         */
        Comment target = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글 삭제 실패했습니다" + "댓글 대상이 업습니다."));
        commentRepository.delete(target);
        return CommentDto.createCommentDto(target);
    }
}
