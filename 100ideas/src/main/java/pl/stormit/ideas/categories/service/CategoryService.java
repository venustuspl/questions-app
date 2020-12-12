package pl.stormit.ideas.categories.service;

import org.springframework.stereotype.Service;
import pl.stormit.ideas.categories.domain.Category;
import pl.stormit.ideas.categories.repository.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category addCategory(Category category) {
        if (category.getId() != null) {
            throw new IllegalStateException("The Category to add cannot contain an ID");
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Category categoryToDelete) {
        UUID categoryToDeleteId = categoryToDelete.getId();
        if (categoryToDelete.getId() == null) {
            throw new IllegalStateException("The Category to delete must contain an ID");
        }
        Category category = categoryRepository.findById(categoryToDeleteId)
                .orElseThrow(() -> new NoSuchElementException("The Question object with id " + categoryToDeleteId + " does not exist in DB"));

        categoryRepository.delete(category);
    }

    public List<Category> getAllCategoriesByCategoryId(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new NoSuchElementException("The Category object with id " + categoryId + " does not exist in DB")
                );
        return categoryRepository.findAllById(category.getId());
    }

    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("The Category object with id " + id + " does not exist in DB")
                );
    }

    @Transactional
    public Category updateCategory(Category categoryToUpdate) {
        UUID categoryToUpdatedId = categoryToUpdate.getId();
        Category category = categoryRepository.findById(categoryToUpdatedId)
                .orElseThrow(() -> new NoSuchElementException("The Question object with id " + categoryToUpdatedId + " does not exist in DB"));
        category.setName(categoryToUpdate.getName());
        return category;
    }

    public List<Category> getAllQuestions() {
        return categoryRepository.findAll();
    }
}