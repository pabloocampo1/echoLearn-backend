package com.EchoLearn_backend.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovedExamModel {
    private Long approved_exam_id;
    private User user;
    private ExamModel examModel;
    private Boolean isApproved;
    private LocalDateTime createDate;
    private Integer score;
}
