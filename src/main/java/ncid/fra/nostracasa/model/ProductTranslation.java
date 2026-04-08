package ncid.fra.nostracasa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_translation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String locale;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
