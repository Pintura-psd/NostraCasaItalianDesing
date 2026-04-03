package ncid.fra.nostracasa.controller;


import ncid.fra.nostracasa.model.Category;
import ncid.fra.nostracasa.service.CategoryService;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@org.springframework.web.bind.annotation.ControllerAdvice
public class GlobalControllerAdvice {

    private final CategoryService categoryService;

    public GlobalControllerAdvice(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ModelAttribute("categories")
    public List<Category>
    populateCategories() {
        return categoryService.findAll();
    }
}
