package com.vote.dto;

import lombok.Data;

@Data
public class AnswerDto {

    private Long answerId;
    private String text;
    private Long votes;
}
