package in.main.Product.SubCategory;

import in.main.Product.PageRequestDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface SubCategoryService {

    public SubCategoryDTO updateSubCategory(Long id, SubCategoryDTO dto, Long loggedUserId);
    public void delete(Long id);
    SubCategoryDTO getSubCategory(Long id);
    public SubCategoryDTO saveSubCategory(SubCategoryDTO dto,Long loggedUserId);
    List<SubCategoryDTO> getAllSubCategories();
    public PageRequestDTO<SubCategoryDTO> getSubCategories(PageRequestDTO<?> requestDTO);
    ByteArrayInputStream exportSubCategories(String keyword);
    List<SubCategoryDTO> searchSubCategories(String keyword);

}
