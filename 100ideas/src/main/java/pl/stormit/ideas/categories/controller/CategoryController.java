package pl.stormit.ideas.categories.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.stormit.ideas.categories.domain.Category;
import pl.stormit.ideas.categories.domain.CategoryAddedRequest;
import pl.stormit.ideas.categories.mapper.CategoryMapper;
import pl.stormit.ideas.categories.service.CategoryService;

import java.util.UUID;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/{id}")
    public String getCategory(Model model, @PathVariable UUID id) {
        model.addAttribute("categories", categoryMapper.mapToCategoryResponseList(categoryService.getAllCategoriesByCategoryId(id)));
        model.addAttribute("categoryToAdd", new CategoryAddedRequest());
        model.addAttribute("exception", model.containsAttribute("exception"));
        model.addAttribute("exceptionEdit", model.containsAttribute("exceptionEdit"));
        return "category/categories";
    }

    @PostMapping("/add")
    public String addCategory(CategoryAddedRequest categoryAddedRequest, RedirectAttributes redirectAttributes) {
        try {
            categoryService.addCategory(categoryMapper.mapCategoryRequestToCategory(categoryAddedRequest));
        } catch (Exception exception) {
            redirectAttributes
                    .addFlashAttribute("exception", true)
                    .addFlashAttribute("message", exception.getMessage());
            return "redirect:/categories/add";
        }
        return "redirect:/categories/";
    }

    @GetMapping("/{id}/delete")
    public String deleteCategory(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        Category category = categoryService.getCategoryById(id);
        try {
            categoryService.deleteCategory(category);
        } catch (Exception exception) {
            redirectAttributes
                    .addFlashAttribute("exceptionEdit", true)
                    .addFlashAttribute("message", exception.getMessage());
            return "redirect:/categories/" + category.getId();
        }
        return "redirect:/categories/";
    }
}
