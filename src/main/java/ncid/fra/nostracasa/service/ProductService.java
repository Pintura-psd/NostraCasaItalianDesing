package ncid.fra.nostracasa.service;

import ncid.fra.nostracasa.dto.ProductDTO;
import ncid.fra.nostracasa.model.*;
import ncid.fra.nostracasa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    // 🔥 BASE REAL Y ESTABLE DE ARCHIVOS
    private final Path rootLocation;

    public ProductService(ProductRepository productRepository,
                          CategoryService categoryService,
                          @Value("${file.upload-dir:uploads/productos_images}") String uploadDir) {

        this.productRepository = productRepository;
        this.categoryService = categoryService;

        this.rootLocation = Paths.get(uploadDir)
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear carpeta base de uploads", e);
        }
    }

    // ---------------------------
    // QUERIES (NO TOCADO)
    // ---------------------------

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Map<String, List<Product>> getProductsByCategoryGrouped(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);

        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::getDisplayTypeName,
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    // ---------------------------
    // CREATE PRODUCT
    // ---------------------------

    public void createProduct(ProductDTO dto,
                              MultipartFile thumbnail,
                              MultipartFile detail,
                              List<MultipartFile> compositions) throws IOException {

        System.out.println("thumbnail = " + (thumbnail != null ? thumbnail.getOriginalFilename() : "null"));
        System.out.println("detail = " + (detail != null ? detail.getOriginalFilename() : "null"));
        System.out.println("compositions size = " + (compositions != null ? compositions.size() : "null"));

        Product product = new Product();

        product.setPrice(dto.getPrice());
        product.setMaterial(dto.getMaterial());
        product.setType(dto.getType());
        product.setSize(dto.getSize());
        product.setThickness(dto.getThickness());
        product.setFinishes(dto.getFinishes());
        product.setPiecesPerBox(dto.getPiecesPerBox());
        product.setBoxesPerPallet(dto.getBoxesPerPallet());
        product.setM2_per_box(dto.getM2PerBox());
        product.setM2_per_pallet(dto.getM2PerPallet());
        product.setWeight_per_box(dto.getWeightPerBox());
        product.setWeight_per_pallet(dto.getWeightPerPallet());

        Category category = categoryService.findById(dto.getCategoryId());
        product.setCategory(category);

        // 1️⃣ Guardar producto primero
        product = productRepository.save(product);

        // 2️⃣ Carpeta del producto
        Path productFolder = rootLocation.resolve(String.valueOf(product.getId()));
        Files.createDirectories(productFolder);

        List<ImageProduct> images = new ArrayList<>();

        // 3️⃣ IMÁGENES
        if (thumbnail != null && !thumbnail.isEmpty()) {
            ImageProduct img = saveImage(thumbnail, product, productFolder, "thumbnail");
            img.setProduct(product);
            images.add(img);
        }

        if (detail != null && !detail.isEmpty()) {
            ImageProduct img = saveImage(detail, product, productFolder, "detail");
            img.setProduct(product);
            images.add(img);
        }

        if (compositions != null) {
            for (MultipartFile file : compositions) {
                if (file != null && !file.isEmpty()) {
                    ImageProduct img = saveImage(file, product, productFolder, "composition");
                    img.setProduct(product);
                    images.add(img);
                }
            }
        }

        product.setImageProducts(images);

        // 4️⃣ TRADUCCIONES
        List<ProductTranslation> translations = new ArrayList<>();

        translations.add(createTranslation("es", dto.getNameEs(), dto.getDescriptionEs(), product));
        translations.add(createTranslation("en", dto.getNameEn(), dto.getDescriptionEn(), product));

        product.setTranslations(translations);

        // 5️⃣ GUARDADO FINAL
        productRepository.save(product);
    }

    // ---------------------------
    // SAVE IMAGE
    // ---------------------------

    private ImageProduct saveImage(MultipartFile file,
                                   Product product,
                                   Path folder,
                                   String type) {

        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path target = folder.resolve(filename);

            // 🔥 escritura segura
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            ImageProduct img = new ImageProduct();
            img.setUrlImage("/productos_images/" + product.getId() + "/" + filename);
            img.setType(type);
            img.setProduct(product);

            return img;

        } catch (IOException e) {
            throw new RuntimeException("Error guardando imagen: " + file.getOriginalFilename(), e);
        }
    }

    // ---------------------------
    // DELETE PRODUCT + FILES
    // ---------------------------

    public void deleteById(Long id) {

        productRepository.findById(id).ifPresent(product -> {

            Path folder = rootLocation.resolve(String.valueOf(id));

            try {
                if (Files.exists(folder)) {
                    Files.walk(folder)
                            .sorted(Comparator.reverseOrder())
                            .forEach(path -> {
                                try {
                                    Files.delete(path);
                                } catch (IOException ignored) {}
                            });
                }
            } catch (IOException e) {
                throw new RuntimeException("Error borrando archivos", e);
            }

            productRepository.delete(product);
        });
    }

    // ---------------------------
    // TRANSLATION HELPER
    // ---------------------------

    private ProductTranslation createTranslation(String locale,
                                                 String name,
                                                 String description,
                                                 Product product) {

        ProductTranslation t = new ProductTranslation();
        t.setLocale(locale);
        t.setName(name);
        t.setDescription(description);
        t.setProduct(product);
        return t;
    }
}