package in.main.Product.Products;

import in.main.Product.Exception.ResourceAlreadyExists;
import in.main.Product.Exception.ResourceNotFound;
import in.main.Product.Helper.ProductHelper;
import in.main.Product.SubCategory.SubCategory;
import in.main.Product.SubCategory.SubCategoryRepository;
import in.main.Product.Users.User;
import in.main.Product.Users.UserRepository;
import in.main.Product.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{


        @Autowired
        private ProductRepository repo;

        @Autowired
        private SubCategoryRepository subCategoryRepo;

        @Autowired
        private UserRepository userRepo;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");



       @Override
        public ProductDTO saveProduct(ProductDTO dto , Long loggedUserId) {
           try {
               if(repo.existsByName(dto.getName())){
                   throw new ResourceAlreadyExists("Product Already Exists");
               }
               if(dto.getDiscount() > dto.getPrice()){
                   throw new IllegalArgumentException("Discount cannot be greater than price");
               }
               Date manufactureDate = sdf.parse(dto.getManufactureDate());
               Date validFrom = sdf.parse(dto.getValidFrom());
               Date validTo = sdf.parse(dto.getValidTo());
               Date today = sdf.parse(sdf.format(new Date()));

               if(manufactureDate.after(today)){
                   throw new IllegalArgumentException("Manufacture date must be in past");
               }

               if(validFrom.after(today)){
                   throw new IllegalArgumentException("Valid From cannot be future date");
               }

               if(validTo.before(today)){
                   throw new IllegalArgumentException("Valid To must be today or future"
                   );
               }

               if(validTo.before(validFrom)){
                   throw new IllegalArgumentException("Valid To cannot be before Valid From");
               }

               Product product = new Product();
               product.setName(dto.getName());
               product.setPrice(dto.getPrice());
               product.setDescription(dto.getDescription());
               product.setDiscount(dto.getDiscount());

               if (dto.getImage() != null && !dto.getImage().isEmpty()) {

                   String uploadDir = "E:/uploads/";
                   Path uploadPath = Paths.get(uploadDir);
                   try {
                       if (!Files.exists(uploadPath)) {
                           Files.createDirectories(uploadPath);
                       }
                       String filename = UUID.randomUUID().toString() + "_" + dto.getImage().getOriginalFilename();
                       Path filePath = uploadPath.resolve(filename);
                       Files.copy(dto.getImage().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                       product.setImageURL(filename);
                   }catch (Exception e){
                       throw new IllegalArgumentException(e);
                   }

               }

               product.setCondition(dto.getCondition());
               product.setColor(dto.getColor());
               product.setActive(AppConstants.ACTIVE);
               product.setValidFrom(sdf.parse(dto.getValidFrom()));
               product.setValidTo(sdf.parse(dto.getValidTo()));
               product.setManufactureDate(sdf.parse(dto.getManufactureDate()));

               product.setSerialno(dto.getSerialno());
               product.setWarranty(dto.getWarranty());
               SubCategory subCategory = subCategoryRepo.findById(dto.getSubCategoryId())
                       .orElseThrow(() -> new ResourceNotFound("SubCategory Not Found"));
               product.setSubCategory(subCategory);


               User loggedUser = userRepo.findById(loggedUserId).orElseThrow(() -> new ResourceNotFound("User not found"));
               product.setCreatedBy(loggedUser);
               product.setUpdatedBy(loggedUser);
               System.out.println("SUB CATEGORY ID = " + dto.getSubCategoryId());
               System.out.println("CREATED BY = " + dto.getCreatedBy());
               System.out.println("UPDATED BY = " + dto.getUpdatedBy());

                repo.save(product);

                ProductDTO dto2 = new ProductDTO();
                dto2.setName(product.getName());
                dto2.setPrice(product.getPrice());
                dto2.setActive(product.getActive());
                dto2.setDescription(product.getDescription());
                dto2.setDiscount(product.getDiscount());
                dto2.setImageURL(product.getImageURL());
                dto2.setCondition(product.getCondition());
                dto2.setColor(product.getColor());
               dto2.setValidTo(sdf.format(product.getValidTo()));
               dto2.setValidFrom(sdf.format(product.getValidFrom()));
               dto2.setManufactureDate(sdf.format(product.getManufactureDate()));
                dto2.setSerialno(product.getSerialno());
                dto2.setWarranty(product.getWarranty());
                dto2.setCreatedBy(product.getCreatedBy().getId());
                dto2.setUpdatedBy(product.getUpdatedBy().getId());
                dto2.setSubCategoryId(product.getSubCategory().getId());

                return dto2;

           } catch (Exception e) {
               throw new IllegalArgumentException(e);
           }
        }



        @Override
        public ProductDTO getProduct(Long id) {

           try {

               Product product = repo.findById(id).get();

               ProductDTO dto = new ProductDTO();
               dto.setId(product.getId());
               dto.setName(product.getName());
               dto.setPrice(product.getPrice());
               dto.setDescription(product.getDescription());
               dto.setDiscount(product.getDiscount());

               dto.setCondition(product.getCondition());
               dto.setColor(product.getColor());
               dto.setActive(product.getActive());
               dto.setManufactureDate(sdf.format(product.getManufactureDate())
               );

               dto.setValidFrom(sdf.format(product.getValidFrom())
               );

               dto.setValidTo(sdf.format(product.getValidTo()));
               dto.setSerialno(product.getSerialno());
               dto.setWarranty(product.getWarranty());

               dto.setSubCategoryId(product.getSubCategory().getId());

               dto.setCreatedBy(product.getCreatedBy().getId());

               dto.setUpdatedBy(product.getUpdatedBy().getId());
               dto.setId(product.getId());

               dto.setSubCategoryId(
                       product.getSubCategory().getId()
               );

               dto.setSubCategoryName(product.getSubCategory().getName()
               );

               dto.setCategoryName(product.getSubCategory().getCategory().getName()
               );

               dto.setImageURL(product.getImageURL());


               return dto;
           }catch(Exception e) {
               throw new IllegalArgumentException(e);
           }
        }



        @Override
        public ProductDTO updateProduct(Long id,ProductDTO dto,Long loggedUserId) {
           try {
               Product product = repo.findById(id).get();

               Date manufactureDate = sdf.parse(dto.getManufactureDate());
               Date validFrom = sdf.parse(dto.getValidFrom());
               Date validTo = sdf.parse(dto.getValidTo());

               Date today = sdf.parse(sdf.format(new Date()));

               if(manufactureDate.after(today)){
                   throw new RuntimeException(
                           "Manufacture date must be in past"
                   );
               }

               if(validFrom.after(today)){
                   throw new IllegalArgumentException(
                           "Valid From cannot be future date"
                   );
               }

               if(validTo.before(today)){
                   throw new IllegalArgumentException(
                           "Valid To must be today or future"
                   );
               }

               if(validTo.before(validFrom)){
                   throw new IllegalArgumentException(
                           "Valid To cannot be before Valid From"
                   );
               }
               dto.setId(product.getId());
               product.setName(dto.getName());
               product.setPrice(dto.getPrice());
               product.setDescription(dto.getDescription());
               product.setDiscount(dto.getDiscount());

               product.setCondition(dto.getCondition());
               product.setColor(dto.getColor());
               product.setValidFrom(sdf.parse(dto.getValidFrom()));
               product.setValidTo(sdf.parse(dto.getValidTo()));
               product.setManufactureDate(sdf.parse(dto.getManufactureDate()));
               product.setSerialno(dto.getSerialno());
               product.setWarranty(dto.getWarranty());
               product.setActive(dto.getActive());

               SubCategory subCategory = subCategoryRepo.findById(dto.getSubCategoryId()).get();
               product.setSubCategory(subCategory);

               User loggedUser = userRepo.findById(loggedUserId).orElseThrow(() -> new ResourceNotFound("User not found"));

               product.setUpdatedBy(loggedUser);

               product.setUpdatedAt(LocalDateTime.now());
               if (dto.getImage() != null && !dto.getImage().isEmpty()) {
                   String uploadDir = "E:/uploads/";
                   Path uploadPath = Paths.get(uploadDir);
                   if (!Files.exists(uploadPath)) {
                       Files.createDirectories(uploadPath);
                   }

                   String filename =UUID.randomUUID().toString() + "_" + dto.getImage().getOriginalFilename();

                   Path filePath = uploadPath.resolve(filename);
                   Files.copy(dto.getImage().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING
                   );

                   product.setImageURL(filename);
                   System.out.println("Saved Image = " + filename);
               }

               repo.save(product);

               ProductDTO dto2 = new ProductDTO();
                dto2.setName(product.getName());
                dto2.setPrice(product.getPrice());
                dto2.setActive(product.getActive());
                dto2.setDescription(product.getDescription());
                dto2.setDiscount(product.getDiscount());
                dto2.setImageURL(product.getImageURL());


                dto2.setCondition(product.getCondition());
                dto2.setColor(product.getColor());
               dto2.setValidTo(sdf.format(product.getValidTo()));
               dto2.setValidFrom(sdf.format(product.getValidFrom()));
               dto2.setManufactureDate(sdf.format(product.getManufactureDate()));
                dto2.setSerialno(product.getSerialno());
                dto2.setWarranty(product.getWarranty());
                dto2.setCreatedBy(product.getCreatedBy().getId());
                dto2.setUpdatedBy(product.getUpdatedBy().getId());
                dto2.setSubCategoryId(product.getSubCategory().getId());

                return dto2;
           }catch(Exception e) {
               throw new IllegalArgumentException(e);
            }
        }

        @Override
        public void deleteProduct(Long id) {
        try {
            Product product = repo.findById(id).orElseThrow(() -> new ResourceNotFound("Id is not found"));
            product.setActive(AppConstants.DELETED);
            repo.save(product);
            ProductDTO dto = new ProductDTO();
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setActive(product.getActive());
            dto.setDescription(product.getDescription());
            dto.setDiscount(product.getDiscount());
            dto.setImageURL(product.getImageURL());
            dto.setCondition(product.getCondition());
            dto.setColor(product.getColor());
            dto.setValidTo(sdf.format(product.getValidTo()));
            dto.setValidFrom(sdf.format(product.getValidFrom()));
            dto.setManufactureDate(sdf.format(product.getManufactureDate()));
            dto.setSerialno(product.getSerialno());
            dto.setWarranty(product.getWarranty());
            dto.setSubCategoryId(product.getSubCategory().getId());
            dto.setUpdatedBy(product.getUpdatedBy().getId());



        }catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
        }

    @Override

    public List<ProductDTO> getAllProducts() {
    try {
        List<Product> products = repo.findByActiveNot(9);

        List<ProductDTO> dtoList = new ArrayList<>();

        for (Product p : products) {
            ProductDTO dto = new ProductDTO();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setDescription(p.getDescription());
            dto.setPrice(p.getPrice());
            dto.setDiscount(p.getDiscount());
            dto.setActive(p.getActive());
            dto.setSerialno(p.getSerialno());
            dto.setManufactureDate(p.getManufactureDate().toString());
            dto.setImageURL(p.getImageURL());


            dto.setSubCategoryId(p.getSubCategory().getId());
            dto.setSubCategoryName(p.getSubCategory().getName());
            dto.setCategoryName(p.getSubCategory().getCategory().getName());

            dto.setImageURL(p.getImageURL());

            dtoList.add(dto);
        }

        return dtoList;
    }catch(Exception e) {
        throw new IllegalArgumentException(e);
    }
    }

    @Override
    public ByteArrayInputStream exportProducts(String keyword) {
           try {

               List<Product> products ;
               if(keyword == null || keyword.trim().isEmpty()){
                   products = repo.findByActiveNot(9);
               }
               else{
                   products = repo.searchByKeyword(keyword);
               }
               if(products.isEmpty()){
                   throw new ResourceNotFound("Product Not Found");
               }


               return ProductHelper.productToExcel(products);
           } catch (Exception e) {
               throw new IllegalArgumentException(e);
           }
    }

    @Override
    public List<ProductDTO> searchProducts(String keyword) {
        List<Product> list = repo.searchByKeyword(keyword);
        return list.stream().map(sub -> {
            ProductDTO dto = new ProductDTO();
            dto.setId(sub.getId());
            dto.setName(sub.getName());
            dto.setDescription(sub.getDescription());
            dto.setImageURL(sub.getImageURL());
            dto.setCategoryName(sub.getSubCategory().getCategory().getName());
            dto.setPrice(sub.getPrice());
            dto.setDiscount(sub.getDiscount());

            return dto;
        }).toList();
    }

}

