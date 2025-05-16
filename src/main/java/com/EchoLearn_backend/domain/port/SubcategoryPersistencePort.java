package com.EchoLearn_backend.domain.port;


import com.EchoLearn_backend.domain.model.Category;
import com.EchoLearn_backend.domain.model.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface SubcategoryPersistencePort {
    SubCategory getById(Integer id);
    Boolean existById(Integer id);
    SubCategory save(SubCategory subCategory);
    void delete(Integer id);
    Page<SubCategory> getAll(Pageable pageable);
    Page<SubCategory> getAllAvailable(Pageable pageable);
    Page<SubCategory> getByCategory(Category category, Pageable pageable);

}
