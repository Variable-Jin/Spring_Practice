package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne             // Comment - Article 다대일 설정
    @JoinColumn(name = "article_id")        // 외래키 생성, Article E - PK mapping
    private Article article;       // 부모 게시글

    @Column
    private String nickname;
    @Column
    private String body;           // 본문 댓글


    public static Comment createComment(CommentDto commentDto, Article article) {
        /**
         * 예외 발생
         * 엔티티 생성 및 반환
         */
        if (commentDto.getId() != null)
            throw new IllegalArgumentException("댓글 생성을 실패했습니다. 댓글의 id가 없어야 합니다.");
        if (commentDto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성을 실패했습니다. 게시글의 id가 없어야 합니다.");

        return new Comment(
                commentDto.getId(),         // 댓글 id
                article,                    // 부모 게시글
                commentDto.getNickname(),   // 댓글 닉네임
                commentDto.getBody()        // 댓글 본문
        );
    }

    public void patch(CommentDto commentDto) {
        // 예외 발생
        if (this.id != commentDto.getId())
            throw new IllegalArgumentException("댓글 수정을 실패했습니다. 대상 댓글이 없습니다.");

        // 객체 갱신
        if (commentDto.getNickname() != null)           // 수정할 닉네임 데이터가 있다면
            this.nickname = commentDto.getNickname();   // 내용 반영
        if (commentDto.getBody() != null)               // 수정할 본문 데이터가 있다면
            this.body = commentDto.getBody();           // 내용 반영

    }
}
