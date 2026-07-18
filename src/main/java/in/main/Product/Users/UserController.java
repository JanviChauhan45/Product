package in.main.Product.Users;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO) {
        UserDTO user = userService.createUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody UserDTO dto,
            HttpSession session) {
        try {
            System.out.println("Before Login Session ID = " + session.getId());
            UserDTO dto1 = userService.Login(dto);
            session.setAttribute("LoggedInUser", dto1);
            return ResponseEntity.ok(dto1);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
                }
            }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        try {
            session.invalidate();
            return ResponseEntity.ok("Logged Out");
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO, HttpSession session) {
        Long loggedUserId = (Long) session.getAttribute("LoggedInUser");
        try {
            if (loggedUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Please login first");
            }
            User updated = userService.updateUser(id, userDTO);

            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        UserDTO user = userService.deleteUser(id);
        return "Deleted Successfully ";
    }

}
