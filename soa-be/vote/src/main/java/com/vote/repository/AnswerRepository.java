package com.vote.repository;

import com.vote.dto.AnswerDto;
import com.vote.entity.AnswerEntity;
import com.vote.entity.QuestionEntity;
import com.vote.mapper.AnswerMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerRepository {

    final private AnswerJpaRepository answerJpaRepository;
    final private AnswerMapper answerMapper;

    @Autowired
    public AnswerRepository(final AnswerJpaRepository answerJpaRepository, final AnswerMapper answerMapper) {
        this.answerJpaRepository = answerJpaRepository;
        this.answerMapper = answerMapper;
    }

    public AnswerDto save(final AnswerDto answer) {
        AnswerEntity savedQuestion = answerJpaRepository.save(answerMapper.Dto2Entity(answer));
        return answerMapper.Entity2Dto(savedQuestion);
    }

    public void delete(final AnswerDto r) {
        answerJpaRepository.delete(answerMapper.Dto2Entity(r));
    }

    public List<AnswerDto> findAll() {
        List<AnswerEntity> allQuestions = answerJpaRepository.findAll();
        return answerMapper.EntityList2DtoList(allQuestions);
    }

    public void incrementVotes(Long id) {
        AnswerEntity answer = answerJpaRepository.getOne(id);
        answer.setVotes(answer.getVotes() + 1);
        answerJpaRepository.save(answer);
    }

    public void decrementVotes(Long id) {
        AnswerEntity answer = answerJpaRepository.getOne(id);
        answer.setVotes(answer.getVotes() - 1);
        answerJpaRepository.save(answer);
    }

    public AnswerDto findById(Long id) {
        AnswerEntity answer = answerJpaRepository.getOne(id);
        return answerMapper.Entity2Dto(answer);
    }
}
