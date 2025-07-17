package com.EchoLearn_backend.infraestructure.adapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exam_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ExamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_exam;

    @ManyToMany
    @JoinTable(
            name = "exam_subcategory",
            joinColumns = @JoinColumn(name = "id_exam"),
            inverseJoinColumns = @JoinColumn(name = "id_subcategory")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<SubCategoryExamEntity> subCategories;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 600)
    private String description;

    @Column(nullable = false)
    private String level;

    @Column(nullable = false)
    private Integer duration;

   @Column(nullable = false)
   private Integer points;

    @Column(nullable = false)
    private Boolean available;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "exam",  cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
   // @JsonIgnore
    List<QuestionsExamEntity> questionsExamEntities ;
}

