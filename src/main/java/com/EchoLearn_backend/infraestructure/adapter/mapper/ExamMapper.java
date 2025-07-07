package com.EchoLearn_backend.infraestructure.adapter.mapper;

import com.EchoLearn_backend.application.mapper.SubcategoryMapper;
import com.EchoLearn_backend.application.service.SubCategory.SubCategoryService;
import com.EchoLearn_backend.application.usecases.SubCategoryUseCase.SubCategoryUseCase;
import com.EchoLearn_backend.domain.model.ExamModel;
import com.EchoLearn_backend.domain.model.QuestionModel;
import com.EchoLearn_backend.domain.model.SubCategory;
import com.EchoLearn_backend.infraestructure.adapter.entity.ExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.entity.SubCategoryExamEntity;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExamMapper {

    public ExamEntity modelToEntity(ExamModel examModel) {

        return ExamEntity.
                builder()
                .id_exam(examModel.getId_exam())
                .level(examModel.getLevel())
                .duration(examModel.getDuration())
                .title(examModel.getTitle())
                .description(examModel.getDescription())
                .available(examModel.getAvailable())
                .points(examModel.getPoints())
                .build();
    }

    public ExamModel dtoToModel(ExamCreateDto examCreateDto) {
        return ExamModel
                .builder()
                .level(examCreateDto.getLevel())
                .duration(examCreateDto.getDuration())
                .points(examCreateDto.getPoints())
                .available(examCreateDto.getAvailable())
                .title(examCreateDto.getTitle())
                .description(examCreateDto.getDescription())
                .subCategories(examCreateDto.getSubcategories())
                .build();
    }

    public ExamModel entityToModel(ExamEntity examEntity, List<SubCategoryExamEntity> list) {
        List<Integer> subCategoriesToModel = list
                .stream().map(SubCategoryExamEntity::getId_subcategory).toList();

        return ExamModel
                .builder()
                .id_exam(examEntity.getId_exam())
                .level(examEntity.getLevel())
                .available(examEntity.getAvailable())
                .points(examEntity.getPoints())
                .duration(examEntity.getDuration())
                .title(examEntity.getTitle())
                .description(examEntity.getDescription())
                .subCategories(subCategoriesToModel)
                .build();
    }
}
