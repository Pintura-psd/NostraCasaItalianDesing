package ncid.fra.nostracasa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "urlThum", length = 255)
    private String urlThum;

    //Relaciones
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
