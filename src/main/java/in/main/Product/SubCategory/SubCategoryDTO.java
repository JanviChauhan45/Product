package in.main.Product.SubCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryDTO {
    private Long id;
    @NotBlank(message = "SubCategory Name is required")
    @Size(min = 3,max = 50)
    private String name;
    @NotBlank(message = "Description is required")
    @Size(min = 10,max = 255)
    private String description;
    @NotNull(message = "Category is required")
    private Long categoryId;
    private String categoryName;
    private Long createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer active;

   private Long updatedBy;


}