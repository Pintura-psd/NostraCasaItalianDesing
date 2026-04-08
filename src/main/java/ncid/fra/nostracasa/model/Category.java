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

//    @Column(name = "name", nullable = false, length = 255)
//    private String name;
//
//    @Column(name = "description", nullable = false, length = 255)
//    private String description;

    @Column(name = "urlThum", length = 255)
    private String urlThum;

    //Relaciones
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CategoryTranslation> translations;

    public CategoryTranslation getTranslation(String locale) {
        if (translations == null || translations.isEmpty()) {
            return null;
        }

        return translations.stream()
                .filter(t -> t.getLocale() != null && t.getLocale().startsWith(locale))
                .findFirst()
                .orElse(translations.get(0));
    }

    public String getName(String locale) {
        CategoryTranslation t = getTranslation(locale);
        return t != null ? t.getName() : "Sin nombre";
    }

    public String getDescription(String locale) {
        CategoryTranslation t = getTranslation(locale);
        return t != null ? t.getDescription() : "Sin descripción";
    }
}
