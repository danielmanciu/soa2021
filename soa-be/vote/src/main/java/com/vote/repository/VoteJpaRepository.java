package com.vote.repository;

import com.vote.entity.VoteEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteJpaRepository extends JpaRepository<VoteEntity, Long> {
    VoteEntity findByUserId(Long id);
    VoteEntity findByQuestionId(Long id);
    VoteEntity findByUserIdAndQuestionId(Long userId, Long questionId);
}
