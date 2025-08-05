package com.EchoLearn_backend.application.usecases;

import com.EchoLearn_backend.domain.model.AnswerModel;
import com.EchoLearn_backend.domain.model.ExamModel;
import com.EchoLearn_backend.domain.model.QuestionModel;
import com.EchoLearn_backend.infraestructure.rest.dto.ExamDtos.*;

import java.util.List;

public interface ExamUseCase {
    ExamModel saveSingleExam(ExamModel examModel);
    ExamModel getById(Long id);
    ExamModel saveExam(ExamCreateDto examCreateDto);
    List<ExamModel> getAll();
    List<ExamHomeDto> getAllExamForHome();
    List<ExamHomeDto> getAllExamBySubcategory(Integer id);
    ExamResult evaluateExamResult(ExamSubmissionDTO examResultRequest);
    // question and answers useCase
    QuestionModel saveQuestion(QuestionModel questionModel);
    AnswerModel saveAnswer(AnswerModel answerModel);
    List<QuestionModel> getAllQuestionByExamId(Long id);
    ExamQuestionsResponse getQuestionOfOneExam(Long exam_id);
    Boolean existExamByExamId(Long exam_id);



}
