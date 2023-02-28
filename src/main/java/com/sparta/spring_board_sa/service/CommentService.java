package com.sparta.spring_board_sa.service;


import com.sparta.spring_board_sa.dto.CommentRequestDto;
import com.sparta.spring_board_sa.dto.CommentResponseDto;
import com.sparta.spring_board_sa.dto.ResponseDto;
import com.sparta.spring_board_sa.entity.*;
import com.sparta.spring_board_sa.repository.BoardRepository;
import com.sparta.spring_board_sa.repository.CommentLikeRepository;
import com.sparta.spring_board_sa.repository.CommentRepository;
import com.sparta.spring_board_sa.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final CommentLikeRepository commentLikeRepository;


    // 코멘트 남기기
    public CommentResponseDto createComment(Long id,
                                            CommentRequestDto commentRequestDto,
                                            UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto, board, user));

        return new CommentResponseDto(comment, user);
    }

    // 코멘트 수정하기
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto,
                                            UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (user.getRole() == UserRoleEnum.ADMIN || user.getId().equals(comment.getUser().getId())) {
            comment.update(commentRequestDto);
            return new CommentResponseDto(comment, user);
        }
        throw new IllegalArgumentException("존재하지 않는 댓글이거나, 댓글 작성자만 삭제 가능합니다.");
    }
    
    // 댓글 삭제하기
    public ResponseDto<String> deleteComment(Long id, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (user.getRole() == UserRoleEnum.ADMIN || user.getId().equals(comment.getUser().getId())) {
            commentRepository.delete(comment);
            return ResponseDto.success("댓글 삭제 완료");
        }
        throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
    }

    public ResponseDto<String> likeComment(Long id, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        Optional<CommentLike> commentLike = commentLikeRepository.findByCommentAndUser(comment, user);
        if (commentLike.isPresent()) {
            commentLikeRepository.deleteById(commentLike.get().getId());
            return ResponseDto.success("댓글 좋아요 삭제");
        }
        commentLikeRepository.save(new CommentLike(comment, user));
        return ResponseDto.success("댓글 좋아요 성공");

    }
}

