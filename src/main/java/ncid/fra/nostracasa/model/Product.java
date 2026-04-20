package ncid.fra.nostracasa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @Column(name = "description", length = 2000)
//    private String description;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "material")
    private String material;

    @Column(name = "type")
    private String type;

    @Column(name = "size")
    private String size;

    @Column(name = "thickness")
    private Double thickness;

    @Column(name = "finishes")
    private String finishes;

    @Column(name = "pieces_per_box")
    @Min(1)
    private int piecesPerBox;

    @Column(name = "box_per_pallet")
    private int boxesPerPallet;

    @Column(name = "m2_per_box")
    private double m2_per_box;

    @Column(name = "m2_per_pallet")
    private Double m2_per_pallet;

    @Column(name = "weight_per_box")
    private Double weight_per_box;

    @Column(name = "weight_per_pallet")
    private Double weight_per_pallet;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ImageProduct> imageProducts;

    @OneToMany(mappedBy = "product")
    private List<DetailOrder> detailOrders;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductTranslation> translations;

    //filtro de imagenes.

    public String getThumbnail() {
        if (imageProducts == null) return null;

        return imageProducts.stream()
                .filter(img -> "thumbnail".equals(img.getType()))
                .map(ImageProduct::getUrlImage)
                .findFirst()
                .orElse(null);
    }

    public List<String> getComposiciones() {
        if (imageProducts == null) return List.of();

        return imageProducts.stream()
                .filter(img -> "composition".equals(img.getType()))
                .map(ImageProduct::getUrlImage)
                .toList();
    }

    public String getDetail() {
        if (imageProducts == null) return null;

        return imageProducts.stream()
                .filter(img -> "detail".equals(img.getType()))
                .map(ImageProduct::getUrlImage)
                .findFirst()
                .orElse(null);
    }

    public ProductTranslation getTranslation(String locale) {
        if (translations == null || translations.isEmpty()) {
            return null;
        }

        return translations.stream()
                .filter(t -> t.getLocale() != null && t.getLocale().startsWith(locale))
                .findFirst()
                .orElse(translations.get(0));
    }
    public String getName(String locale) {
        ProductTranslation t = getTranslation(locale);
        return t != null ? t.getName() : "Sin nombre";
    }

    public String getDescription(String locale) {
        ProductTranslation t = getTranslation(locale);
        return t != null ? t.getDescription() : "Sin descripción";
    }

    public String getDisplayTypeName() {
        if (type != null) {
            return Arrays.stream(type.split("_"))
                    .map(s -> s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase())
                    .collect(Collectors.joining(" "));
        }
        return "Sin tipo";
    }

}
