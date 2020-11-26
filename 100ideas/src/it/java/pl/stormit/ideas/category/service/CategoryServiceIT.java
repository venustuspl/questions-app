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
        assertThat(savedCategory.getName()).isEqualTo("Test");
        assertThat(savedCategory.getId()).isNotNull();
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
        assertThat(savedCategories).isEmpty();
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
