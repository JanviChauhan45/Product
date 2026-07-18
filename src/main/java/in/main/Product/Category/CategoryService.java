package in.main.Product.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;


public interface CategoryService {
    CategoryDTO saveCategory(CategoryDTO dto,long loggedUser);
    public CategoryDTO getCategory(Long id);
    CategoryDTO updateCategory(Long id, CategoryDTO dto,long loggedUser);
    void deleteCategory(Long id);
    public List<CategoryDTO> getAllCategories();
    ByteArrayInputStream exportCategories(String keyword);
    List<CategoryDTO> search(String keyword);
}
