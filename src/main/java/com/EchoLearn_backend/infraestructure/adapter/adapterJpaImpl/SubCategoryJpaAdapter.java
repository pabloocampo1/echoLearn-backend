package com.EchoLearn_backend.infraestructure.adapter.adapterJpaImpl;

import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.domain.model.SubCategory;
import com.EchoLearn_backend.domain.port.SubcategoryPersistencePort;
import com.EchoLearn_backend.infraestructure.adapter.entity.CategoryExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.entity.SubCategoryExamEntity;
import com.EchoLearn_backend.infraestructure.adapter.mapper.CategoryMapper;
import com.EchoLearn_backend.infraestructure.adapter.mapper.SubcategoryDboMapper;
import com.EchoLearn_backend.infraestructure.adapter.repository.CategoryRepository;
import com.EchoLearn_backend.infraestructure.adapter.repository.SubcategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class SubCategoryJpaAdapter implements SubcategoryPersistencePort {

    private final SubcategoryRepository subcategoryRepository;
    private final SubcategoryDboMapper subcategoryDboMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SubCategoryJpaAdapter(SubcategoryRepository subcategoryRepository, SubcategoryDboMapper subcategoryDboMapper, CategoryMapper categoryMapper, CategoryRepository categoryRepository) {

        this.subcategoryRepository = subcategoryRepository;
        this.subcategoryDboMapper = subcategoryDboMapper;
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public SubCategory getById(@Valid Integer id) {
        SubCategoryExamEntity subCategory = this.subcategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("the sub category with that id, no exist. " + id));
        return this.subcategoryDboMapper.toDomain(subCategory);
    }

    @Override
    public Boolean existById(@Valid Integer id) {
        return this.subcategoryRepository.existsById(id);
    }

    @Override
    public SubCategory save(@Valid SubCategory subCategory) {
        System.out.println("llego a la funcion del adpater");
        // pasamos de subcategoria del dominio a uan entidad para poder guardarla
        SubCategoryExamEntity subCategoryExamEntity = this.subcategoryDboMapper.toDbo(subCategory);
        // Obtenemos la categoria con la cual se va a relacionar
        CategoryExamEntity category = this.categoryRepository.findById(subCategory.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("that category no exist: " + subCategory.getCategory()));
        // asignamos esa categoria
        subCategoryExamEntity.setCategory(category);
        // guardamos la subcategoria y retornamos la subcategoria de dominio
        return this.subcategoryDboMapper.toDomain(this.subcategoryRepository.save(subCategoryExamEntity));
    }

    @Override
    public void delete(Integer id) {
        this.subcategoryRepository.deleteById(id);
    }

    @Override
    public Page<SubCategory> getAll(Pageable pageable) {

        Page<SubCategoryExamEntity> entityPage = this.subcategoryRepository.findAll(pageable);
        return entityPage.map(this.subcategoryDboMapper::toDomain);
    }


    @Override
    public Page<SubCategory> getAllAvailable(Pageable pageable) {
        Page<SubCategoryExamEntity> entityPage = this.subcategoryRepository.findAll(pageable);
        return entityPage.map(this.subcategoryDboMapper::toDomain);
    }

    @Override
    public Page<SubCategory> getByCategory(@Valid Category category, Pageable pageable) {
        CategoryExamEntity categoryExamEntity = this.categoryMapper.toDbo(category);
        Page<SubCategoryExamEntity> entityPage = this.subcategoryRepository.findByAvailableTrueAndCategory(categoryExamEntity, pageable );

        return entityPage.map(this.subcategoryDboMapper::toDomain) ;
    }
}
