package com.EchoLearn_backend.application.usecases;

import com.EchoLearn_backend.domain.model.AnswerModel;
import com.EchoLearn_backend.domain.model.ExamModel;
import com.EchoLearn_backend.domain.model.QuestionModel;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamCreateDto;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamHomeDto;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamResult;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.ExamResultRequest;

import java.util.List;

public interface ExamUseCase {
    ExamModel saveSingleExam(ExamModel examModel);
    ExamModel getById(Long id);
    ExamModel saveExam(ExamCreateDto examCreateDto);
    List<ExamModel> getAll();
    List<ExamHomeDto> getAllExamForHome();
    ExamResult evaluateExamResult(ExamResultRequest examResultRequest);
    // question and answers useCase
    QuestionModel saveQuestion(QuestionModel questionModel);
    AnswerModel saveAnswer(AnswerModel answerModel);



}
