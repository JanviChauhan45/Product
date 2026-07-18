package in.main.Product.Users;



import in.main.Product.Exception.ResourceAlreadyExists;
import in.main.Product.Exception.ResourceNotFound;
import in.main.Product.Exception.UnauthorizedException;
import in.main.Product.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        try {

            if (userRepository.existsByEmailIgnoreCase(userDTO.getEmail())) {
                throw new ResourceAlreadyExists("Email already exists");
            }
            if (userRepository.existsByName(userDTO.getName())) {
                throw new ResourceAlreadyExists("Username already exists");
            }

            User user = new User();

            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(encodedPassword);
            user.setCreatedAt(LocalDateTime.now());
            user.setActive(AppConstants.ACTIVE);
            userRepository.save(user);

            UserDTO userDTO1 = new UserDTO();
            userDTO1.setId(user.getId());
            userDTO1.setName(user.getName());
            userDTO1.setEmail(user.getEmail());
            userDTO1.setPassword(user.getPassword());
            userDTO1.setCreatedAt(user.getCreatedAt());
            return userDTO1;
        }catch(Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public UserDTO Login(UserDTO userDTO) {
        try {
            User user =userRepository.findByName(userDTO.getName()).orElseThrow(()-> new ResourceNotFound("Usernot found "));
            boolean isPasswordMatch = passwordEncoder.matches(userDTO.getPassword(), user.getPassword());
            if(!isPasswordMatch) {
                throw new ResourceNotFound("Passwords do not match");
            }

            UserDTO userDTO2 = new UserDTO();
            userDTO2.setId(user.getId());
            userDTO2.setName(user.getName());
            userDTO2.setPassword(user.getPassword());
            userDTO2.setCreatedAt(user.getCreatedAt());
            userDTO2.setEmail(user.getEmail());
            return userDTO2;

        }catch (IllegalArgumentException e) {
            throw e;
        }catch(Exception e){
            throw new UnauthorizedException("Error while loging");
        }


    }

    @Override
    public User updateUser(Long id,UserDTO userDTO) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            user.setName(userDTO.getName());
            return userRepository.save(user);
        }catch(Exception e){
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public UserDTO deleteUser(Long id) {
        try {
            User users = userRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Id not found"));
            users.setActive(AppConstants.DELETED);
            User saved = userRepository.save(users);
            UserDTO userDTO = new UserDTO();
            userDTO.setName(users.getName());
            userDTO.setEmail(users.getEmail());
            userDTO.setPassword(users.getPassword());
            return userDTO;
        }catch(Exception e){
            throw new IllegalArgumentException(e);
        }
    }
}
