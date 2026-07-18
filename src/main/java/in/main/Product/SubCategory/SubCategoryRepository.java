package in.main.Product.SubCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {

    Page<SubCategory> findAll(Pageable pageable);
    boolean existsByName(String name);
    List<SubCategory> findByActiveNot(Integer active);
   List<SubCategory> findByNameContainingIgnoreCase(String keyword);

    @Query("SELECT s FROM SubCategory s WHERE " +
            "(LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND s.active != 9")
    List<SubCategory> searchByKeyword(@Param("keyword") String keyword);

    boolean existsByCategory_Id(Long categoryId);

}
