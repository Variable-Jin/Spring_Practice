package com.example.firstproject.dto;

import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class CommentDto {
    private Long id;
//    @JsonProperty("article_id")
    private Long articleId;     // 댓글의 부모 id
    private String nickname;
    private String body;


    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}
