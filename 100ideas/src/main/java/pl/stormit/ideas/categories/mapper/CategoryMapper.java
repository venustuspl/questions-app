package pl.stormit.ideas.categories.mapper;

import org.springframework.stereotype.Component;
import pl.stormit.ideas.categories.domain.Category;
import pl.stormit.ideas.categories.domain.CategoryAddedRequest;
import pl.stormit.ideas.categories.domain.CategoryResponse;
import pl.stormit.ideas.categories.domain.CategoryUpdatedRequest;
import pl.stormit.ideas.categories.service.CategoryService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    private final CategoryService categoryService;

    public CategoryMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public CategoryResponse mapToCategoryResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setName(category.getName());
        categoryResponse.setId(category.getId().toString());
        return categoryResponse;
    }

    public List<CategoryResponse> mapToCategoryResponseList(List<Category> categories) {
        return categories.stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    public Category mapCategoryRequestToCategory(CategoryAddedRequest categoryAddedRequest) {
        Category category = new Category();
        category.setName(categoryAddedRequest.getName());
        category.setParent(categoryService.getCategoryById(UUID.fromString(categoryAddedRequest.getCategoryId())));
        return category;
    }

    public Category mapCategoryUpdatedRequestToCategory(CategoryUpdatedRequest categoryUpdatedRequest) {
        Category category = categoryService.getCategoryByName(categoryUpdatedRequest.getName());
        category.setName(categoryUpdatedRequest.getName());
        return category;
    }
}

