package pl.stormit.ideas.categories.service;


import pl.stormit.ideas.categories.domain.Category;
import pl.stormit.ideas.categories.repository.CategoryRepository;

import javax.transaction.Transactional;
import java.util.UUID;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void addCategory (Category category){
        categoryRepository.save(category);
    }
    public void deleteById(UUID id){
        categoryRepository.deleteById(id);
    }
}
