package com.vote.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "ANSWERS", schema = "vote")
@Data
@SequenceGenerator(name = "AnswerSeq", sequenceName = "SEQ_ANSWER", allocationSize = 1)
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AnswerSeq")
    @Column(name = "ANSWER_ID")
    private Long answerId;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "VOTES")
    private Long votes;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private QuestionEntity question;

}
