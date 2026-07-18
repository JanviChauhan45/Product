package in.main.Product.SubCategory;

import in.main.Product.Category.Category;
import in.main.Product.Category.CategoryRepository;
import in.main.Product.Exception.BusinessException;
import in.main.Product.Exception.ResourceAlreadyExists;
import in.main.Product.Exception.ResourceNotFound;
import in.main.Product.Helper.SubCategoryHelper;
import in.main.Product.PageRequestDTO;
import in.main.Product.Users.User;
import in.main.Product.Users.UserRepository;
import in.main.Product.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class SubCategoryImpl implements SubCategoryService{


        @Autowired
        private SubCategoryRepository repo;

        @Autowired
        private CategoryRepository categoryRepo;

        @Autowired
        private UserRepository userRepo;


        @Override

        public SubCategoryDTO saveSubCategory(SubCategoryDTO dto, Long loggedUserId) {

            try {

                if (repo.existsByName(dto.getName())) {
                    throw new ResourceAlreadyExists("SubCategory already exists");
                }

                SubCategory subCategory = new SubCategory();

                subCategory.setName(dto.getName());
                subCategory.setDescription(dto.getDescription());

                Category category = categoryRepo.findById(dto.getCategoryId())
                        .orElseThrow(() ->
                                new ResourceNotFound("Category not found " + dto.getCategoryId()));

                if (category.getActive() != AppConstants.ACTIVE) {
                    throw new BusinessException("Cannot create SubCategory under an Inactive Category");
                }

                subCategory.setCategory(category);
                subCategory.setActive(AppConstants.ACTIVE);

                User loggedUser = userRepo.findById(loggedUserId)
                        .orElseThrow(() ->
                                new ResourceNotFound("User not found"));

                subCategory.setCreatedBy(loggedUser);
                subCategory.setUpdatedBy(loggedUser);

                repo.save(subCategory);

                SubCategoryDTO dto1 = new SubCategoryDTO();

                dto1.setId(subCategory.getId());
                dto1.setName(subCategory.getName());
                dto1.setDescription(subCategory.getDescription());
                dto1.setCategoryId(subCategory.getCategory().getId());
                dto1.setCategoryName(subCategory.getCategory().getName());
                dto1.setActive(subCategory.getActive());

                dto1.setCreatedBy(subCategory.getCreatedBy().getId());
                dto1.setUpdatedBy(subCategory.getUpdatedBy().getId());

                return dto1;

            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

    @Override

    public List<SubCategoryDTO> getAllSubCategories() {

        List<SubCategory> subCategories = repo.findByActiveNot(9);

        return subCategories.stream().map(sub -> {

            SubCategoryDTO dto = new SubCategoryDTO();

            dto.setId(sub.getId());
            dto.setName(sub.getName());
            dto.setDescription(sub.getDescription());
            dto.setActive(sub.getActive());

            dto.setCategoryId(sub.getCategory().getId());
            dto.setCategoryName(sub.getCategory().getName());

            return dto;

        }).toList();
    }


    @Override
    public PageRequestDTO<SubCategoryDTO> getSubCategories(PageRequestDTO<?> requestDTO) {

        Pageable pageable = PageRequest.of(
                requestDTO.getPageNumber(),
                requestDTO.getPageSize()
        );

        Page<SubCategory> page = repo.findAll(pageable);

        List<SubCategoryDTO> dtoList = page.getContent().stream().map(subCategory -> {

            SubCategoryDTO dto = new SubCategoryDTO();

            dto.setId(subCategory.getId());
            dto.setName(subCategory.getName());
            dto.setDescription(subCategory.getDescription());
            dto.setActive(subCategory.getActive());

            dto.setCategoryId(subCategory.getCategory().getId());
            dto.setCategoryName(subCategory.getCategory().getName());

            return dto;

        }).toList();

        PageRequestDTO<SubCategoryDTO> response = new PageRequestDTO<>();

        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }

    @Override
    public ByteArrayInputStream exportSubCategories(String keyword) {
       List<SubCategory> subCategories;
        if(keyword == null || keyword.trim().isEmpty()){
           subCategories =  repo.findByActiveNot(9);
        }else  {
            subCategories = repo.searchByKeyword(keyword);
        }

        if(subCategories.isEmpty()){
            throw new ResourceNotFound("SubCategory Not Found");
        }



        System.out.println("Subcategories size: " + subCategories.size());

        return SubCategoryHelper.subcategoryToExcel(subCategories);
    }

    @Override
    public List<SubCategoryDTO> searchSubCategories(String keyword) {
        List<SubCategory> list = repo.searchByKeyword(keyword);
        return list.stream().map(sub -> {

            SubCategoryDTO dto = new SubCategoryDTO();

            dto.setId(sub.getId());
            dto.setName(sub.getName());
            dto.setDescription(sub.getDescription());
            dto.setActive(sub.getActive());

            dto.setCategoryId(sub.getCategory().getId());
            dto.setCategoryName(sub.getCategory().getName());

            return dto;

        }).toList();

    }


    public SubCategoryDTO getSubCategory(Long id) {
    try {
        SubCategory subCategory = repo.findById(id).get();

        SubCategoryDTO dto = new SubCategoryDTO();

        dto.setName(subCategory.getName());

        dto.setDescription(subCategory.getDescription());
        dto.setActive(subCategory.getActive());

        dto.setCreatedBy(subCategory.getCreatedBy().getId());

        dto.setUpdatedBy(subCategory.getUpdatedBy().getId());
        dto.setId(subCategory.getId());
        dto.setCategoryName(subCategory.getCategory().getName());
        dto.setCategoryId(subCategory.getCategory().getId());
        dto.setCategoryName(subCategory.getCategory().getName());

        return dto;
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(e);
    }
        }




    public SubCategoryDTO updateSubCategory(Long id, SubCategoryDTO dto, Long loggedUserId) {

        try {
            SubCategory subCategory = repo.findById(id).orElseThrow(() -> new ResourceNotFound("SubCategory not found"));
            subCategory.setName(dto.getName());
            subCategory.setDescription(dto.getDescription());
            subCategory.setActive(AppConstants.ACTIVE);

            Category category = categoryRepo.findById(dto.getCategoryId()).orElseThrow(() -> new ResourceNotFound("Category not found"));

            subCategory.setCategory(category);

            User loggedUser = userRepo.findById(loggedUserId).orElseThrow(() -> new ResourceNotFound("User not found"));
            subCategory.setUpdatedBy(loggedUser);
            repo.save(subCategory);

            SubCategoryDTO dto1 = new SubCategoryDTO();

            dto1.setId(subCategory.getId());
            dto1.setName(subCategory.getName());
            dto1.setDescription(subCategory.getDescription());
            dto1.setCategoryId(subCategory.getCategory().getId());
            dto1.setCategoryName(subCategory.getCategory().getName());
            dto1.setActive(subCategory.getActive());
            dto1.setCreatedBy(subCategory.getCreatedBy().getId());
            dto1.setUpdatedBy(subCategory.getUpdatedBy().getId());

            return dto1;

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }



    public void delete(Long id) {

        try {
            SubCategory subCategory = repo.findById(id).orElseThrow(() -> new ResourceNotFound("SubCategory not found " + id));
            subCategory.setActive(AppConstants.DELETED);
            repo.save(subCategory);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    }




