package com.sparta.spring_board_sa.repository;

import com.sparta.spring_board_sa.entity.Comment;
import com.sparta.spring_board_sa.entity.CommentLike;
import com.sparta.spring_board_sa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentAndUser (Comment comment, User user);
}
