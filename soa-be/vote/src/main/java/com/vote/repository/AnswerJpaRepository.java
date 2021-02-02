package com.vote.repository;

import com.vote.entity.AnswerEntity;
import com.vote.entity.QuestionEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerJpaRepository extends JpaRepository<AnswerEntity,Long> {
    List<AnswerEntity> findAnswerEntityByQuestion(QuestionEntity question);
}
