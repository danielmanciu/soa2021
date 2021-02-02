package com.vote.entity;


import java.io.Serializable;

public class VoteId implements Serializable {
    private Long userId;

    private Long questionId;

    // default constructor

    public VoteId() {}

    public VoteId(Long userId, Long questionId) {
        this.userId = userId;
        this.questionId = questionId;
    }
}