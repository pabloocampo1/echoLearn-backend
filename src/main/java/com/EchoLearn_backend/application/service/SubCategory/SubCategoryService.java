package com.EchoLearn_backend.application.service.SubCategory;

import com.EchoLearn_backend.application.usecases.SubCategoryUseCase.SubCategoryUseCase;
import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.domain.model.SubCategory;
import com.EchoLearn_backend.domain.port.CategoryExamPersistencePort;
import com.EchoLearn_backend.domain.port.SubcategoryPersistencePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService implements SubCategoryUseCase {
    @Autowired
    private final SubcategoryPersistencePort subcategoryPersistencePort;

    @Autowired
    private final CategoryExamPersistencePort categoryExamPersistencePort;

    public SubCategoryService(SubcategoryPersistencePort subcategoryPersistencePort, CategoryExamPersistencePort categoryExamPersistencePort) {
        this.subcategoryPersistencePort = subcategoryPersistencePort;
        this.categoryExamPersistencePort = categoryExamPersistencePort;
    }

    @Override
    @Transactional
    public SubCategory save(SubCategory subCategory) {
        try {
            this.validateCategory(subCategory.getCategories());
            return this.subcategoryPersistencePort.save(subCategory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    @Transactional
    public SubCategory update(SubCategory subCategory) {
        try {
            // validar si la subcategoria existe
            this.validateSubCategoryExists(subCategory.getId_subcategory());
            return this.subcategoryPersistencePort.save(subCategory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<SubCategory> getAll(Pageable pageable) {
        return this.subcategoryPersistencePort.getAll(pageable);
    }

    @Override
    public SubCategory findById(Integer id) {
        return this.subcategoryPersistencePort.getById(id);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        try{
            if (!this.subcategoryPersistencePort.existById(id)){
                throw new IllegalArgumentException("that id no exist to delete: "+ id);
            }
            this.subcategoryPersistencePort.delete(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<SubCategory> getByCategory(Integer id, Pageable pageable) {
       try{
           Category category = this.categoryExamPersistencePort.findById(id);
           return this.subcategoryPersistencePort.getByCategory(category, pageable);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public List<SubCategory> getByTitle(@Valid String title) {
        return this.subcategoryPersistencePort.getByTitle(title);
    }

    @Override
    public List<SubCategory> getAllById(List<Integer> ids) {
        return this.subcategoryPersistencePort.findAllById(ids);
    }

    @Override
    public List<Integer> existAllById(List<Integer> ids) {

        return this.subcategoryPersistencePort.existAllById(ids) ;
    }

    private void validateCategory(List<Integer> ids){
       ids.forEach((id) -> {
           if (id == null || id <= 0) {
               throw new IllegalArgumentException("Not there are one category");
           }
           if (!this.categoryExamPersistencePort.existById(id)) {
               throw new IllegalArgumentException("Not there are one category");
           }
       });

    }

    private void validateSubCategoryExists(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de subcategoría inválido");
        }
        if (!this.subcategoryPersistencePort.existById(id)) {
            throw new IllegalArgumentException("No existe subcategoría con ID: " + id);
        }
    }


}
