package in.main.Product.Category;

import in.main.Product.Exception.BusinessException;
import in.main.Product.Users.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO dto, HttpSession  session) {
        UserDTO dto1 = (UserDTO) session.getAttribute("LoggedInUser");
        if(dto1 == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       }
        System.out.println("ACTIVE = " + dto.getActive());
        CategoryDTO response = service.saveCategory(dto,dto1.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id,HttpSession  session) {
        UserDTO dto1 = (UserDTO) session.getAttribute("LoggedInUser");
        if(dto1 == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
       CategoryDTO response = service.getCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO dto ,HttpSession  session) {
        UserDTO dto1 = (UserDTO) session.getAttribute("LoggedInUser");
        if(dto1 == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CategoryDTO response = service.updateCategory(id, dto,dto1.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id,HttpSession session) {
        try {
            UserDTO dto1 = (UserDTO) session.getAttribute("LoggedInUser");
            if (dto1 == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            service.deleteCategory(id);
            return ResponseEntity.ok().body("Category has been deleted");
        }catch(BusinessException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(
            HttpSession session) {

        UserDTO dto1 = (UserDTO) session.getAttribute("LoggedInUser");

        if(dto1 == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(service.getAllCategories());
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> downloadExcel(@RequestParam(required = false,defaultValue = "") String keyword, HttpSession session ) {
       try {
           UserDTO dto1 = (UserDTO) session.getAttribute("LoggedInUser");

           if (dto1 == null) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
           }

           List<CategoryDTO> list = service.search(keyword);
           if(keyword != null && !keyword.isEmpty() && list.isEmpty()){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
           }
           String fileName = "categories.xlsx";
           ByteArrayInputStream stream = service.exportCategories(keyword);
           InputStreamResource file = new InputStreamResource(stream);
           return ResponseEntity.ok()
                   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                   .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                   )
                   .body(file);
       }catch(Exception e){
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);


       }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> searchCategory(
            @RequestParam String keyword
    ){
        try{
            List<CategoryDTO> result = service.search(keyword);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}