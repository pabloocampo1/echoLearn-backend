package com.EchoLearn_backend.application.usecases.SubCategoryUseCase;

import com.EchoLearn_backend.domain.model.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SubCategoryUseCase {
    SubCategory save(SubCategory subCategory);
    SubCategory update (SubCategory subCategory);
    Page<SubCategory> getAll(Pageable pageable);
    SubCategory findById(Integer id);
    void delete(Integer id);
    Page<SubCategory> getByCategory(Integer id, Pageable pageable);
    List<SubCategory> getByTitle(String title);
    List<SubCategory> getAllById(List<Integer> ids);
    List<Integer> existAllById(List<Integer> ids);
}
