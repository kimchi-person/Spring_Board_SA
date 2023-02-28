package com.sparta.spring_board_sa.dto;

import com.sparta.spring_board_sa.entity.Comment;
import com.sparta.spring_board_sa.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private String username;
    private int commentLike;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment, User user) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = user.getUsername();
        this.commentLike = comment.getCommentLikeList().size();
        this.createAt = comment.getCreateAt();
        this.modifiedAt = comment.getModifiedAt();
    }

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(comment.getUser().getUsername())
                .commentLike(comment.getCommentLikeList().size())
                .createAt(comment.getCreateAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
