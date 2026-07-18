package in.main.Product.Products;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ProductService {

    public ProductDTO saveProduct(ProductDTO dto,Long loggedUserId);
    public ProductDTO getProduct(Long id);
    public ProductDTO updateProduct(Long id, ProductDTO dto,Long loggedUserId);
    public void deleteProduct(Long id);
    public List<ProductDTO> getAllProducts();
    ByteArrayInputStream exportProducts(String keyword);
    List<ProductDTO> searchProducts(String keyword);
}
