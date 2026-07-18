package in.main.Product.Users;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO Login(UserDTO userDTO);
    User updateUser(Long id,UserDTO userDTO);
    UserDTO deleteUser(Long id);
}
