package in.main.Product.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    @NotBlank(message = "Category Name is required")
    @Size(min = 3,max = 50,message = "Category Name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 10,max = 255,
            message = "Description must be between 10 and 255 characters")
    private String description;

    private Integer active;
}
