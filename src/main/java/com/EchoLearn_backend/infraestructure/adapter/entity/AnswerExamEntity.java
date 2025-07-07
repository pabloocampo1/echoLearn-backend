package com.EchoLearn_backend.infraestructure.adapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answer_exam_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerExamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_question", referencedColumnName = "id_question", nullable = false)
    @JsonIgnore
    private QuestionsExamEntity question;

    @Column(nullable = false)
    private String answerText;

    @Column(nullable = false)
    private Boolean isCorrect;
}
