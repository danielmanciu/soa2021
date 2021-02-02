package com.vote.controller;

import com.vote.dto.AnswerDto;
import com.vote.dto.QuestionDto;
import com.vote.dto.VoteDto;
import com.vote.repository.AnswerRepository;
import com.vote.repository.QuestionRepository;
import com.vote.repository.VoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
public class QuestionController {

    @Autowired
    final private QuestionRepository questionRepository;
    @Autowired
    final private AnswerRepository answerRepository;
    @Autowired
    final private VoteRepository voteRepository;

    public QuestionController(final QuestionRepository c, final AnswerRepository r, final VoteRepository v) {
        this.questionRepository = c;
        this.answerRepository = r;
        this.voteRepository = v;
    }

    @GetMapping("/first")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<QuestionDto> getFirstQuestion() {
        List<QuestionDto> questionsDto = questionRepository.findAll();
        if (!questionsDto.isEmpty()) {
            QuestionDto questionDto = questionsDto.get(0);
            return new ResponseEntity<>(questionDto, HttpStatus.OK);
        } else {
            log.error("No questions found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/vote")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<AnswerDto>> addQuestion(@RequestBody VoteDto voteDto) {
        final VoteDto repoVote = voteRepository.findByUserIdAndQuestionId(voteDto.getUserId(), voteDto.getQuestionId());
        if (repoVote != null) {
            answerRepository.decrementVotes(repoVote.getAnswerId());
        }

        VoteDto vote = voteRepository.save(voteDto);
        answerRepository.incrementVotes(vote.getAnswerId());
        final List<AnswerDto> responseAnswer = questionRepository.findQuestionById(voteDto.getQuestionId()).getAnswers();
        return new ResponseEntity<>(responseAnswer, HttpStatus.OK);
    }
}