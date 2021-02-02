package com.vote.service;

import com.vote.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

}
