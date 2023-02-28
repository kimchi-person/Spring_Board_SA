package com.sparta.spring_board_sa.repository;

import com.sparta.spring_board_sa.entity.Board;
import com.sparta.spring_board_sa.entity.BoardLike;
import com.sparta.spring_board_sa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    Optional<BoardLike> findByBoardAndUser (Board board, User user);
}
