package in.main.Product.Products;

import in.main.Product.Users.UserDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> saveProduct(@Valid @ModelAttribute ProductDTO dto, HttpSession session)    {
        try {
            UserDTO user = (UserDTO) session.getAttribute("LoggedInUser");
            {
                if (user == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
            ProductDTO response = service.saveProduct(dto, user.getId());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @ModelAttribute ProductDTO dto, HttpSession session) {
    try {
        UserDTO user = (UserDTO) session.getAttribute("LoggedInUser");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ProductDTO response = service.updateProduct(id, dto, user.getId());

        return ResponseEntity.ok(response);
    }catch(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error:"+ e.getMessage());
    }
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id,HttpSession session) {
        try {
            UserDTO user = (UserDTO) session.getAttribute("LoggedInUser");
            {
                if (user == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Log in first");
                }
            }
            service.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error"+e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDTO>> getAllProducts(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("LoggedInUser");
        {
            if(user == null){
                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            service.getAllProducts();
            return ResponseEntity.status(HttpStatus.OK).body(service.getAllProducts());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id,HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("LoggedInUser");
        {
            if(user == null){
                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            service.getProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(service.getProduct(id));
        }
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> downloadExcel(@RequestParam(required = false,defaultValue = "") String keyword, HttpSession session) {

        try {
            UserDTO dto1 = (UserDTO) session.getAttribute("LoggedInUser");

            if (dto1 == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<ProductDTO> list = service.searchProducts(keyword);

            String fileName = "Product.xlsx";

            if (keyword != null && !keyword.trim().isEmpty() && list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            ByteArrayInputStream stream = service.exportProducts(keyword);

            InputStreamResource file = new InputStreamResource(stream);

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    )
                    .body(file);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String keyword){
        try{
            List<ProductDTO> list = service.searchProducts(keyword);
            return ResponseEntity.status(HttpStatus.OK).body(list);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}
