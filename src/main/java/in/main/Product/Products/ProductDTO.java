package in.main.Product.Products;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private long id;
    @NotBlank(message = "Product name is required")
    @Size(min = 3,max = 50,message = "Product Name must be between 3 and 50 characters")
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price should not be negative or 0")
    private Double price;
    @Size(min = 10,max = 255,message = "Description must be between 10 and 255 characters")
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "Discount is required")
    @PositiveOrZero(message = "Discount cannot be zero or negative")
    private int discount;

    private MultipartFile image;
    private String imageURL;
    private String categoryName;
    private Long subCategoryId;
    private String subCategoryName;
    private Long createdBy;
    private Long updatedBy;
    private ProductCondition condition;
    private Set<ProductColor> color;
    @NotNull(message = "Discount Valid date is required")
    private String validFrom;
    @NotNull(message = "Discount Valid  to date is required")
    private String validTo;
    @NotNull(message = "Manufacturing date is required")
    private String manufactureDate;
    @NotBlank(message = "Product Serial Number is required")
    private String serialno;
    @NotBlank(message = "Warrenty is required")
    private String warranty;
    private Integer active;

}
