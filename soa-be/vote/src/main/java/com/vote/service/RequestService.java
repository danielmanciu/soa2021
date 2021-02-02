package com.vote.service;

import com.vote.repository.AnswerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestService {
    @Autowired
    private AnswerRepository answerRepository;
}
