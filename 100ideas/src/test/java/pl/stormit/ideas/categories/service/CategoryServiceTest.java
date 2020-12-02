package pl.stormit.ideas.categories.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.stormit.ideas.categories.domain.Category;
import pl.stormit.ideas.categories.repository.CategoryRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class CategoryServiceTest {

    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void shouldThrowExceptionDuringAddingWhenAnswerHasId() {
        //given
        Category category = getCategoryWithId();
        //when
        Throwable throwable = Assertions.catchThrowable(() -> categoryService.addCategory(category));
        //then
        assertThat(throwable)
                .hasMessage("The Category to add cannot contain an ID");
    }

    @Test
    void shouldThrowExceptionDuringAddingWhenCategoryHasNoId() {
        //given
        Category category = new Category();
        //when
        Throwable throwable = Assertions.catchThrowable(() -> categoryService.addCategory(category));
        //then
        assertThat(throwable)
                .doesNotThrowAnyException();
    }

    @Test
    void shouldNotThrowExceptionDuringDeletingWhenCategoryHasNoId() {
        //given
        Category category = new Category();

        //when
        Throwable throwable = Assertions.catchThrowable(() -> categoryService.deleteCategory(category));

        //then
        assertThat(throwable)
                .hasMessage("The Category to delete must contain an ID");
    }

    private Category getCategoryWithId() {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("Test");
        return category;
    }
}