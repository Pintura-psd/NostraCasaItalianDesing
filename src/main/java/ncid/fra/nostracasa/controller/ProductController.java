package ncid.fra.nostracasa.controller;

import jakarta.validation.Valid;
import ncid.fra.nostracasa.dto.SearchProductDTO;
import ncid.fra.nostracasa.model.Product;
import ncid.fra.nostracasa.service.CategoryService;
import ncid.fra.nostracasa.service.ProductService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;


@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/category/{categoryId}/products")
    public String listByCategory(@PathVariable Long categoryId,
                                 Model model) {
        model.addAttribute("category", categoryService.findById(categoryId));
        model.addAttribute("products", productService.findByCategory(categoryId));
        model.addAttribute("productsByType", productService.getProductsByCategoryGrouped(categoryId));

        model.addAttribute("selectedPage", "category");
        model.addAttribute("selectedCategoryId", categoryId);
        return "product/list";
    }

    @GetMapping("/category/{categoryId}/products/{productId}")
    public String productDetail(@PathVariable Long categoryId,
                                @PathVariable Long productId,
                                Model model) {

        model.addAttribute("product", productService.findById(productId));
        return "product/detail";
    }

}
