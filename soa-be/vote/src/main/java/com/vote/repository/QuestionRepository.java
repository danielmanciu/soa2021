package com.vote.repository;

import com.vote.dto.QuestionDto;
import com.vote.entity.QuestionEntity;
import com.vote.mapper.QuestionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionRepository {
    private QuestionJpaRepository questionJpaRepository;
    private QuestionMapper questionMapper;

    @Autowired
    public QuestionRepository(final QuestionJpaRepository questionJpaRepository, final QuestionMapper questionMapper) {
        this.questionJpaRepository = questionJpaRepository;
        this.questionMapper = questionMapper;
    }

    public QuestionDto save(final QuestionDto clientToSave) {
        QuestionEntity savedQuestion = questionJpaRepository.save(questionMapper.Dto2Entity(clientToSave));
        return questionMapper.Entity2Dto(savedQuestion);
    }

    public QuestionDto findQuestionById(final Long id) {
        QuestionEntity questionEntity = questionJpaRepository.getOne(id);
        return questionMapper.Entity2Dto(questionEntity);
    }

    public List<QuestionDto> findQuestions() {
        List<QuestionEntity> questions = questionJpaRepository.findAll();
        return questionMapper.EntityList2DtoList(questions);
    }

    public List<QuestionDto> findAll() {
        List<QuestionEntity> allQuestions = questionJpaRepository.findAll();
        return questionMapper.EntityList2DtoList(allQuestions);
    }

}
