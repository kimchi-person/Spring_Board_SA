package com.sparta.spring_board_sa.dto;


import com.sparta.spring_board_sa.entity.Board;
import com.sparta.spring_board_sa.entity.BoardLike;
import com.sparta.spring_board_sa.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private int boardLike;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    private final List<CommentResponseDto> commentList = new ArrayList<>();

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getUsername();
        this.boardLike = board.getBoardLikeList().size();
        this.createAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
        for (Comment comment : board.getCommentList()) {
            this.commentList.add(CommentResponseDto.from(comment));
        }
    }
}
