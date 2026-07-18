package in.main.Product.Users;

import in.main.Product.constants.AppConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private LocalDateTime createdAt;

    @Column
    private Integer active = AppConstants.ACTIVE;



    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();

    }
}
