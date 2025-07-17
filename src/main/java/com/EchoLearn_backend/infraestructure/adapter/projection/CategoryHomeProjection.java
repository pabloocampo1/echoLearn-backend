package com.EchoLearn_backend.infraestructure.adapter.projection;

import java.time.LocalDateTime;

public interface CategoryHomeProjection {
    Integer getId();
    String getTitle();
    Boolean getAvailable();
    String getDescription();
     LocalDateTime getCreateDate();
    String getImageUrl();
    Integer getTotalSubcategories();
}
