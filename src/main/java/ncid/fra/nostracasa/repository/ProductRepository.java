package ncid.fra.nostracasa.repository;

import ncid.fra.nostracasa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository <Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p JOIN p.translations t " +
            "WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "AND t.locale = :locale")
    List<Product> findByTranslatedName(@Param("name") String name,
                                       @Param("locale") String locale);
}
