package com.EchoLearn_backend.infraestructure.adapter.projection;

import java.util.List;

public interface ExamHomeProjection {
    // to do:
    Long getId_exam();
    String getTitle();
    String getDescription();
    String getLevel();
    Integer getDuration();
    Integer getPoints();
}


