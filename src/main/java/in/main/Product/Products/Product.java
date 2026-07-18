package in.main.Product.Products;

import java.time.LocalDateTime;


import in.main.Product.SubCategory.SubCategory;
import in.main.Product.Users.User;
import in.main.Product.constants.AppConstants;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private Double price;

    @Column
    private String description;

    @Column
    private int discount;

    @Column
    private String imageURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id")
    private SubCategory subCategory;

    @Column
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;



    @Column
    private int active = AppConstants.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column
    private ProductCondition condition;

    @Enumerated(EnumType.STRING)
    @Column
    private Set<ProductColor> color;

    @Column
    private Date ValidFrom;
    @Column
    private Date ValidTo;

    @Column
    private Date manufactureDate;
    @Column
    private String serialno;
    @Column
    private String warranty;


    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }



}



