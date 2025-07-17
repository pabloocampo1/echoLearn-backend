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

import javax.swing.text.html.parser.Entity;
import java.util.List;

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
        // pasamos de subcategoria del dominio a uan entidad para poder guardarla
        SubCategoryExamEntity subCategoryExamEntity = this.subcategoryDboMapper.toDbo(subCategory);

        // Obtenemos la categoria con la cual se va a relacionar
        List<CategoryExamEntity> categories = (List<CategoryExamEntity>) this.categoryRepository.findAllById(subCategory.getCategories());

        // validar que si esten todas las categorias
        if (categories.size() != subCategory.getCategories().size()) {
            throw new IllegalArgumentException("One o more categories no exist.");
        }

        subCategoryExamEntity.setCategories(categories);

        // guardamos la subcategoria para poder asiganarla a la relacion padre
        SubCategoryExamEntity subCategoryExamSaved = this.subcategoryRepository.save(subCategoryExamEntity);

        // Limpiar relaciones antiguas
        List<CategoryExamEntity> allCategories = (List<CategoryExamEntity>) this.categoryRepository.findAll(); // O solo las necesarias
        allCategories.forEach(cat -> {
            if (cat.getSubcategories().contains(subCategoryExamSaved) &&
                    !categories.contains(cat)) {
                cat.getSubcategories().remove(subCategoryExamEntity);
                categoryRepository.save(cat);
            }
        });

        // Añadir nuevas relaciones (esto cubre creación y edición)
        categories.forEach(category -> {
            if (!category.getSubcategories().contains(subCategoryExamEntity)) {
                category.getSubcategories().add(subCategoryExamEntity);
                categoryRepository.save(category);
            }
        });

        return this.subcategoryDboMapper.toDomain(subCategoryExamSaved);
    }

    @Override
    public void delete(Integer id) {
        SubCategoryExamEntity subCategoryExam = this.subcategoryRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException( "Subcategory not  exists."));


        // delete relation with category if there are
        for (CategoryExamEntity categoryExam : subCategoryExam.getCategories()) {
            categoryExam.getSubcategories().remove(subCategoryExam);
            this.categoryRepository.save(categoryExam);
        }
        subCategoryExam.getCategories().clear();


        // delete relation with exam if there are
        this.subcategoryRepository.deleteRelationshipsWithExams(id);
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
        Page<SubCategoryExamEntity> subCategoryExamEntities = this.subcategoryRepository.findAvailableByCategoryId(category.getId_category(), pageable);
        return subCategoryExamEntities.map(this.subcategoryDboMapper::toDomain);
    }

    @Override
    public List<SubCategory> getByTitle(String title) {
        List<SubCategoryExamEntity> subCategoryExamEntityList = this.subcategoryRepository.findAllByTitleContainingIgnoreCase(title);
        List<SubCategory> subCategoryList = subCategoryExamEntityList.stream().map(this.subcategoryDboMapper::toDomain).toList();
        return subCategoryList;
    }

    @Override
    public List<SubCategory> findAllById(List<Integer> ids) {
        List<SubCategoryExamEntity> entities = this.subcategoryRepository.findAllById(ids);
        List<SubCategory> subCategoriesToReturn = entities.stream().map(this.subcategoryDboMapper::toDomain).toList();
        return subCategoriesToReturn;
    }

    @Override
    public List<Integer> existAllById(List<Integer> ids) {
       // Boolean exist = this.subcategoryRepository.
        return List.of();
    }
}
