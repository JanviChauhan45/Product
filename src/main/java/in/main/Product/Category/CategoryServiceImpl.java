package in.main.Product.Category;

import in.main.Product.Exception.BusinessException;
import in.main.Product.Exception.IllegalArgumentException;
import in.main.Product.Exception.ResourceAlreadyExists;
import in.main.Product.Exception.ResourceNotFound;
import in.main.Product.Helper.CategoryHelper;
import in.main.Product.SubCategory.SubCategory;
import in.main.Product.SubCategory.SubCategoryRepository;
import in.main.Product.Users.User;
import in.main.Product.Users.UserRepository;
import in.main.Product.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository repo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SubCategoryRepository subRepo;




    public CategoryDTO saveCategory(CategoryDTO dto , long loggedUserId) {
    try {
//        if(repo.existsByNameIgnoreCase(dto.getName())){
//            throw new ResourceAlreadyExists("Category Name already exists");
//        }
        if(repo.existsByName(dto.getName())){
            throw new ResourceAlreadyExists("Category Name already exists");
        }

        Category category = new Category();

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category.setActive(AppConstants.ACTIVE);
        User loggedUser = userRepo.findById(loggedUserId).orElseThrow(() -> new ResourceNotFound("User not found"));
        category.setCreatedBy(loggedUser);
        category.setUpdatedBy(loggedUser);


        repo.save(category);

        CategoryDTO dto2 = (CategoryDTO) dto;
        dto2.setId(dto2.getId());
        dto2.setName(dto2.getName());
        dto2.setDescription(dto2.getDescription());
        dto2.setActive(dto2.getActive());
        return dto2;
    }catch(IllegalArgumentException e){
        throw  e;
    }
    }



    public CategoryDTO getCategory(Long id) {
        try {
            Category category = repo.findById(id).orElseThrow(() -> new ResourceNotFound("Category not Found"));
            CategoryDTO dto = new CategoryDTO();
            dto.setId(category.getId());
            dto.setName(category.getName());
            dto.setDescription(category.getDescription());
            dto.setActive(category.getActive());
            return dto;
        } catch (IllegalArgumentException e) {
            throw  e;
        }
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        try {

            List<Category> categories = repo.findByActiveNot(9);

            return categories.stream().map(category -> {

                CategoryDTO dto = new CategoryDTO();

                dto.setId(category.getId());
                dto.setName(category.getName());
                dto.setDescription(category.getDescription());
                dto.setActive(category.getActive());

                return dto;

            }).toList();
        }catch(IllegalArgumentException e){
            throw  e;
        }
    }

    @Override
    public ByteArrayInputStream exportCategories(String keyword) {
    try {
        List<Category> categories = repo.findByActiveNot(9);

        if(keyword == null || keyword.trim().isEmpty()){
            categories = repo.findByActiveNot(9);
        }
        else{
            categories = repo.searchCategory(keyword);
        }
        if(categories.isEmpty()){
            throw new ResourceNotFound("Category not found");
        }
        return CategoryHelper.categoryToExcel(categories);
    }catch(IllegalArgumentException e){
        throw  e;
    }
    }


    public CategoryDTO updateCategory(Long id, CategoryDTO dto , long  loggedUserId) {
        try {
            Category category = repo.findById(id).orElseThrow(() -> new ResourceNotFound("Category not Found"));

            category.setName(dto.getName());
            category.setDescription(dto.getDescription());
            category.setActive(dto.getActive());
            category.setUpdatedAt(LocalDateTime.now());
            User loggedUser = userRepo.findById(loggedUserId)
                    .orElseThrow(() ->
                            new ResourceNotFound("User not found"));
            category.setUpdatedBy(loggedUser);

            repo.save(category);
            CategoryDTO dto2 = (CategoryDTO) dto;
            dto2.setId(category.getId());
            dto2.setName(dto2.getName());
            dto2.setDescription(dto2.getDescription());
            dto2.setActive(category.getActive());
            return dto2;

        }catch(IllegalArgumentException e){
            throw e;
        }
    }


    @Override
    public void deleteCategory(Long id) {
    try {
        Category category = repo.findById(id).orElseThrow(() -> new ResourceNotFound("Category not Found"));

        boolean exists = subRepo.existsByCategory_Id(id);

        if (exists) {
            throw new BusinessException(
                    "Cannot delete category because subcategories exist.");
        }

        category.setActive(AppConstants.DELETED);
        repo.save(category);

    }catch(IllegalArgumentException e){
        throw e;
    }
    }

    @Override
    public List<CategoryDTO> search(String keyword){
        List<Category> list = repo.searchCategory(keyword);
        return list.stream().map(category -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(category.getId());
            dto.setName(category.getName());
            dto.setDescription(category.getDescription());
            dto.setActive(category.getActive());
            return dto;
        }).toList();

    }



}
