package com.EchoLearn_backend.infraestructure.adapter.adapterJpaImpl;

import com.EchoLearn_backend.Exception.BadRequestException;
import com.EchoLearn_backend.Exception.ResourceNotFoundException;
import com.EchoLearn_backend.application.usecases.SubCategoryUseCase.SubCategoryUseCase;
import com.EchoLearn_backend.domain.model.ExamModel;
import com.EchoLearn_backend.domain.model.SubCategory;
import com.EchoLearn_backend.domain.port.ExamPersistencePort;
import com.EchoLearn_backend.domain.port.QuestionPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.ExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.entity.SubCategoryExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.mapper.ExamMapper;
import com.EchoLearn_backend.infraestructure.adapter.mapper.SubcategoryDboMapper;
import com.EchoLearn_backend.infraestructure.adapter.projection.ExamHomeProjection;
import com.EchoLearn_backend.infraestructure.adapter.repository.ExamRepositoryJpa;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamHomeDto;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamQuestionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExamJpaAdapter implements ExamPersistencePort {
    private final ExamRepositoryJpa examRepositoryJpa;
    private final ExamMapper examMapper;
    private final SubCategoryUseCase subCategoryUseCase;
    private final SubcategoryDboMapper subcategoryDboMapper;


    @Autowired
    public ExamJpaAdapter(ExamRepositoryJpa examRepositoryJpa, ExamMapper examMapper, SubCategoryUseCase subCategoryUseCase, SubcategoryDboMapper subcategoryDboMapper ) {
        this.examRepositoryJpa = examRepositoryJpa;
        this.examMapper = examMapper;
        this.subCategoryUseCase = subCategoryUseCase;
        this.subcategoryDboMapper = subcategoryDboMapper;

    }
    @Override
    public ExamModel save(ExamModel examModel) {
        ExamEntity examEntity = this.examMapper.modelToEntity(examModel);
        List<SubCategory> subCategories = this.subCategoryUseCase.getAllById(examModel.getSubCategories());
        List<SubCategoryExamEntity> subCategoryExamEntityList = subCategories.stream().map(this.subcategoryDboMapper::toDbo).toList();
        for (SubCategoryExamEntity s : subCategoryExamEntityList) {
            if (s.getId_subcategory() == null) {
                throw new BadRequestException("SubCategory not valid");
            }
        }

        examEntity.setSubCategories(subCategoryExamEntityList);
        ExamEntity create = this.examRepositoryJpa.save(examEntity);

        return this.examMapper.entityToModel(examEntity, create.getSubCategories());
    }

    @Override
    public ExamModel getById(Long id) {
        ExamEntity examEntity = this.examRepositoryJpa.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("exam no found : "+ id));
        List<SubCategoryExamEntity> subCategoryExamEntityList = examEntity.getSubCategories();
        return this.examMapper.entityToModel(examEntity, subCategoryExamEntityList);
    }

    @Override
    public List<ExamModel> getAll() {
        List<ExamEntity> examEntityList = this.examRepositoryJpa.findAll();

        return examEntityList.stream().map((exam) -> {
            return this.examMapper.entityToModel(exam, exam.getSubCategories());
        }).toList();
    }

    @Override
    public List<ExamHomeDto> getAllExamForHome() {
        List<ExamHomeProjection> examHomeProjectionList = this.examRepositoryJpa.findAllExamForHome();
         return examHomeProjectionList.stream().map((exam) -> {
            return new ExamHomeDto(exam.getId_exam(), exam.getTitle(), exam.getDescription(), exam.getLevel(), exam.getDuration(), exam.getPoints(), exam.getTotalQuestion());
        }).toList();
    }

    @Override
    public List<ExamHomeDto> findAllBySubcategory(Integer id) {
        return this.examRepositoryJpa.findAllBySubcategory(id)
                .stream()
                .map(exam -> new ExamHomeDto(exam.getId_exam(), exam.getTitle(), exam.getDescription(), exam.getLevel(), exam.getDuration(), exam.getPoints(), exam.getTotalQuestion())).toList();
    }


    @Override
    public Boolean exitsExam(Long exam_id) {
        return this.examRepositoryJpa.existsById(exam_id);
    }
}
