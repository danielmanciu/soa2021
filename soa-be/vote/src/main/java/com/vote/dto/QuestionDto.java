package com.vote.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuestionDto {

    private Long questionId;
    private String text;
    private List<AnswerDto> answers;
}
