package ncid.fra.nostracasa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url_image", nullable = false, length = 255)
    private String urlImage;

    @Column(name = "type", nullable = false, length = 255)
    private String type;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
