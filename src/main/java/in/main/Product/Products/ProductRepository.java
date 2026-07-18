package in.main.Product.Products;

import in.main.Product.SubCategory.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findById(Long id);
    boolean existsByName(String name);

    List<Product> findByActiveNot(Integer active);
    @Query("""
SELECT p
FROM Product p
WHERE LOWER(p.name)
LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

}
