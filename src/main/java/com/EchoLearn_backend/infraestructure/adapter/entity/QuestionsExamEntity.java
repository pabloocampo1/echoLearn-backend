package com.EchoLearn_backend.infraestructure.adapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "question_exam")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionsExamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_exam", referencedColumnName = "id_exam" , nullable = false)
    @JsonIgnore
    private ExamEntity exam;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private Boolean available;

    @Column(nullable = false)
    private String type;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<AnswerExamEntity> answers;

}
