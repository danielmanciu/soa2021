package com.vote.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "QUESTIONS", schema = "vote")
@Data
@SequenceGenerator(name = "QuestionSeq", sequenceName = "SEQ_QUESTION", allocationSize = 1)
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QuestionSeq")
    @Column(name = "QUESTION_ID")
    private Long questionId;

    @Column(name = "TEXT")
    private String text;

    @ToString.Exclude
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<AnswerEntity> answers = new ArrayList<AnswerEntity>();

    public void addAnswer(AnswerEntity a) {
        a.setQuestion(this);
        answers.add(a);
    }
}
