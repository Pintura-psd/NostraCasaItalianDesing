package ncid.fra.nostracasa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTranslation {

    @Id
    private Long id;

    private String locale;
    private String name;
    private String description;

    @ManyToOne
    private Category category;

}
