package com.EchoLearn_backend.infraestructure.adapter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Table(name = "approved_exam")
@Data
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovedExam {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id
    private Long approved_exam_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approveExam_user", referencedColumnName = "id_user")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "approvedExam_exam", referencedColumnName = "id_exam")
    private ExamEntity examEntity;

    @Column(nullable = false)
    private Boolean isApproved;

    @CreatedDate
    private LocalDateTime createDate;

    @Column(nullable = false)
    private Integer score;

}
