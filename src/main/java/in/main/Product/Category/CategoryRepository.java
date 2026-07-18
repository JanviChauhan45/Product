package in.main.Product.Category;

import in.main.Product.SubCategory.SubCategory;
import in.main.Product.constants.AppConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("""
            SELECT c FROM Category c
            WHERE LOWER (c.name)
            LIKE LOWER(CONCAT('%',:search,'%'))
            """)
    List<Category> searchCategory(@Param("search") String keyword);
    boolean existsByName(String name);
    List<Category> findByActiveNot(Integer active);
    boolean existsByNameIgnoreCase(String name);
    List<Category> findByActive(Integer active);
}
