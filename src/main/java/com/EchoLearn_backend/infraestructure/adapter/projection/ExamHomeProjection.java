package com.EchoLearn_backend.infraestructure.adapter.projection;

import java.util.List;

public interface ExamHomeProjection {
    Long getId_exam();
    String getTitle();
    String getDescription();
    String getLevel();
    Integer getDuration();
    Integer getPoints();
    Integer getTotalQuestion();
}


