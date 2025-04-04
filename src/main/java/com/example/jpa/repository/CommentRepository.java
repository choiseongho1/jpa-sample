package com.example.jpa.repository;

import com.example.jpa.entity.Comment;
import com.example.jpa.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {}
