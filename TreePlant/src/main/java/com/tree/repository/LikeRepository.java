package com.tree.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tree.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {}