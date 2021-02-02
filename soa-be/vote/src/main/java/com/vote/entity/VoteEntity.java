package com.vote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "VOTES", schema = "vote")
@Data
@IdClass(VoteId.class)
public class VoteEntity {

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Id
    @Column(name = "QUESTION_ID")
    private Long questionId;

    @Column(name = "ANSWER_ID")
    private Long answerId;
}