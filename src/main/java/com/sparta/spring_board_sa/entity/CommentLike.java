package com.sparta.spring_board_sa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Comment comment;

    @ManyToOne
    private User user;

    public CommentLike(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }
}
