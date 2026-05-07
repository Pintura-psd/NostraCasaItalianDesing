package ncid.fra.nostracasa.controller;

import jakarta.validation.Valid;
import ncid.fra.nostracasa.dto.ProductDTO;
import ncid.fra.nostracasa.model.Product;
import ncid.fra.nostracasa.service.CategoryService;
import ncid.fra.nostracasa.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping("/admin/products")
public class ProductAdminController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductAdminController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String adminProducts(Model model) {

        Locale locale = LocaleContextHolder.getLocale();

        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("locale", locale.getLanguage());
        model.addAttribute("productDTO", new ProductDTO());

        return "admin/products";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute ProductDTO dto,
                             BindingResult result,
                             @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
                             @RequestParam(value = "detail", required = false) MultipartFile detail,
                             @RequestParam(value = "compositions", required = false) List<MultipartFile> compositions,
                             Model model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("products", productService.findAll());

            Locale locale = LocaleContextHolder.getLocale();
            model.addAttribute("locale", locale.getLanguage());

            return "admin/products";
        }

        productService.createProduct(dto, thumbnail, detail, compositions);

        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteById(id);

        return "redirect:/admin/products";
    }

}
