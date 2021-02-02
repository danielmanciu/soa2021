package com.vote.dto;

import lombok.Data;

@Data
public class VoteDto {

    private Long userId;
    private Long questionId;
    private Long answerId;
}
