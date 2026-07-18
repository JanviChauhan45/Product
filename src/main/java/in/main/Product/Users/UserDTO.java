package in.main.Product.Users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    @NotBlank(message = "User Name is required")
    @Pattern(
            regexp = "^[A-Za-z0-9_ ]+$",
            message = "Username can contain only letters, numbers and underscore"
    )
    @Size(min = 10,max = 30)
    private String name;

        @NotBlank(message ="Password is required")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d\\s])[^\\s]{8,}$",
                message = "Password must contain at least 8 characters, one letter, one number, one special character and no spaces"
        )
   @Size(min = 8 , message = "Minimum 8 characters are required" ,max = 20)
    private String password;


    @NotBlank(message = "Email is required")
   @Email(message = "Invalid email format" )
    @Pattern(
            regexp = "^[a-zA-Z][a-zA-Z0-9._-]*@(gmail|yahoo|outlook)\\.com$",
           message = "Email must start with a letter and use valid domain")
    @Size(min= 10 , max = 60)
    private String email;
    private LocalDateTime createdAt;
}
