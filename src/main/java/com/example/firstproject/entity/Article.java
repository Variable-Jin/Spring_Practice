package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // id 자동 생성(DB)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

    public void patch(Article article) {
        if (article.title !=null)
            this.title = article.title;
        if (article.content !=null)
            this.content = article.content;
    }


//    @Override
//    public String toString() {
//        return "Article{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }


}
