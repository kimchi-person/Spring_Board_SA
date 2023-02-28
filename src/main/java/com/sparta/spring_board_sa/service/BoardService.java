package com.sparta.spring_board_sa.service;


import com.sparta.spring_board_sa.dto.BoardRequestDto;
import com.sparta.spring_board_sa.dto.BoardResponseDto;
import com.sparta.spring_board_sa.dto.ResponseDto;
import com.sparta.spring_board_sa.entity.Board;
import com.sparta.spring_board_sa.entity.BoardLike;
import com.sparta.spring_board_sa.entity.User;
import com.sparta.spring_board_sa.entity.UserRoleEnum;
import com.sparta.spring_board_sa.repository.BoardLikeRepository;
import com.sparta.spring_board_sa.repository.BoardRepository;
import com.sparta.spring_board_sa.repository.UserRepository;
import com.sparta.spring_board_sa.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardLikeRepository boardLikeRepository;


    // 게시글 작성하기
    @Transactional
    public BoardResponseDto createPost(BoardRequestDto boardRequestDto, UserDetailsImpl userDetails) {
            User user = userDetails.getUser();
            Board board = new Board(boardRequestDto, user);
            boardRepository.save(board);
            return new BoardResponseDto(board);
        }

    // 게시글 전부조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getPosts() {
        List<Board> list = boardRepository.findAllByOrderByCreateAtDesc();

        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for (Board board : list) {
            boardResponseDtoList.add(new BoardResponseDto(board));
        }
        if (boardResponseDtoList.isEmpty()) {
            throw new IllegalArgumentException("게시글이 아무것도 존재하지 않습니다.");
        }else {
            return boardResponseDtoList;
        }
    }

    // 특정 게시글 조회
    @Transactional
    public BoardResponseDto getPost(Long id) {
        return new BoardResponseDto(boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        ));
    }

    // 특정 게시글 업데이트
    @Transactional
    public BoardResponseDto updatePost(Long id, BoardRequestDto boardRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(board.getUser().getUsername())) {
            board.update(boardRequestDto);
            return new BoardResponseDto(board);
        }
        return null;
    }

    // 특정 게시글 삭제
    public ResponseDto<String> deletePost(Long id, UserDetailsImpl userDetails) {
            User user = userDetails.getUser();
            Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
            );
            if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(board.getUser().getUsername())){
                boardRepository.delete(board);
                return ResponseDto.success("게시글 삭제완료");
            }else {
                throw new IllegalArgumentException("게시글이 이미 삭제 되었거나, 게시글 작성자만 삭제 가능합니다.");
            }
    }

    // 게시글 좋아요
    public ResponseDto<String> likeBoard(Long id, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        Optional<BoardLike> boardLike = boardLikeRepository.findByBoardAndUser(board, user);
        if (boardLike.isPresent()) {
            boardLikeRepository.deleteById(boardLike.get().getId());
            return ResponseDto.success("게시글 좋아요 취소");
        }
        boardLikeRepository.save(new BoardLike(board, user));
        return ResponseDto.success("게시글 좋아요 성공");
    }



}



