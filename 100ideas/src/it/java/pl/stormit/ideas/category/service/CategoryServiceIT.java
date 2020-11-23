package pl.stormit.ideas.category.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.stormit.ideas.categories.domain.Category;
import pl.stormit.ideas.categories.repository.CategoryRepository;
import pl.stormit.ideas.categories.service.CategoryService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CategoryServiceIT {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldSaveNewCategoryInDB() {

        //given
        Category categoryToSave=getCategoryToSave();

        //when
        Category savedCategory= categoryService.addCategory(categoryToSave);

        //then
        assertEquals(categoryToSave,savedCategory);
        assertNotNull(categoryToSave.getId());
    }

    @Test
    void shouldDeleteCategoryFromDB(){
        //given
        Category savedCategory = categoryRepository.save(getCategoryToSave());
        List<Category>savedCategories = (List<Category>) categoryRepository.findAll();
        assertThat(savedCategories.size()).isOne();
        //when
        categoryService.deleteCategory(savedCategory);
        //then
        savedCategories = (List<Category>) categoryRepository.findAll();
        assertThat(savedCategories.size()).isZero();
    }

    private Category getCategoryToSave() {
        Category category=new Category();
        category.setName("Test");
        return category;
    }

    @BeforeEach
    @AfterEach
    void clear() {
        categoryRepository.deleteAll();
    }
}

