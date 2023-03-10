package com.sparta.spring_board_sa.controller;

import com.sparta.spring_board_sa.dto.CommentRequestDto;
import com.sparta.spring_board_sa.dto.CommentResponseDto;
import com.sparta.spring_board_sa.dto.ResponseDto;
import com.sparta.spring_board_sa.repository.CommentLikeRepository;
import com.sparta.spring_board_sa.security.UserDetailsImpl;
import com.sparta.spring_board_sa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 코멘트 남기기
    @PostMapping("/comment/{id}")
    public CommentResponseDto createComment(@PathVariable Long id,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id, commentRequestDto, userDetails);
    }

    // 코멘트 수정
    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, commentRequestDto, userDetails);
    }

    // 코멘트 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseDto<String> deleteComment(@PathVariable Long id,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails);
    }

    // 코멘트 좋아요
    @PostMapping("/comment/like/{id}")
    public ResponseDto<String> likeComment(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.likeComment(id, userDetails);
    }
}

