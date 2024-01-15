package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ArticleForm {

    private String title;
    private String content;

//    @Override
//    public String toString() {
//        return "ArticleForm{" +     // Article 문자열의 시작
//                "title='" + title + '\'' +      // ' 각 필드의 값 <+>연산자를 이용하여 문자열 이어붙임
//                ", content='" + content + '\'' +
//                '}';                // 전체 문자열 생성
//    }

    public Article toEntity() {
        return new Article(null,title, content);
    }
}
