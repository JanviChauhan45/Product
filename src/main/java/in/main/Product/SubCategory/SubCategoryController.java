package in.main.Product.SubCategory;

import in.main.Product.PageRequestDTO;
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
@RequestMapping("/subcategory")
public class SubCategoryController {

    @Autowired
    private SubCategoryService service;


    @PostMapping("/create")
    public ResponseEntity<?> saveSubCategory(@Valid @RequestBody SubCategoryDTO dto, HttpSession session) {
        try {
            UserDTO loggedUser = (UserDTO) session.getAttribute("LoggedInUser");

            if (loggedUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .build();
            }

            SubCategoryDTO response = service.saveSubCategory(dto, loggedUser.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSubCategories(HttpSession session) {
        try {

            UserDTO dto1 = (UserDTO) session.getAttribute("LoggedInUser");
            if (dto1 == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<SubCategoryDTO> list = service.getAllSubCategories();
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error" + e.getMessage());
        }
    }


    // GET
    @GetMapping("/{id}")
    public ResponseEntity<?> getSubCategory(@Valid @PathVariable Long id, HttpSession session) {
    try {
        UserDTO dto1 = (UserDTO) session.getAttribute("LoggedInUser");
        if (dto1 == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        SubCategoryDTO dto = service.getSubCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error"+ e.getMessage());
    }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubCategory(@Valid @PathVariable Long id, @RequestBody SubCategoryDTO dto, HttpSession session) {
        try {
            UserDTO loggedUser = (UserDTO) session.getAttribute("LoggedInUser");
            if (loggedUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            SubCategoryDTO response = service.updateSubCategory(id, dto, loggedUser.getId());
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error" + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubCategory(@Valid @PathVariable Long id, HttpSession session) {
        try {
            UserDTO dto1 = (UserDTO) session.getAttribute("LoggedInUser");
            if (dto1 == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error" + e.getMessage());
        }
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> downloadExcel(
            @RequestParam(required = false, defaultValue = "") String keyword,
            HttpSession session) {

        try {
            UserDTO dto1 = (UserDTO) session.getAttribute("LoggedInUser");
            if (dto1 == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<SubCategoryDTO> list = service.searchSubCategories(keyword);

            if (keyword != null && !keyword.trim().isEmpty() && list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            ByteArrayInputStream stream = service.exportSubCategories(keyword);
            InputStreamResource file = new InputStreamResource(stream);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=Subcategories.xlsx")
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(file);

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String keyword) {
        try {
            List<SubCategoryDTO> result = service.searchSubCategories(keyword);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Search failed: " + e.getMessage());
        }
    }


}

