package ncid.fra.nostracasa.model;

import jakarta.persistence.*;
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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "material")
    private String material;

    @Column(name = "type")
    private String type;

    @Column(name = "typeName")
    private String typeName;

    @Column(name = "size")
    private String size;

    @Column(name = "thickness")
    private Double thickness;

    @Column(name = "finishes")
    private String finishes;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ImageProduct> imageProducts;

    @OneToMany(mappedBy = "product")
    private List<DetailOrder> detailOrders;

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

    public String getDisplayTypeName() {
        if (type != null) {
            return Arrays.stream(type.split("_"))
                    .map(s -> s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase())
                    .collect(Collectors.joining(" "));
        }
        return "Sin tipo";
    }

}
