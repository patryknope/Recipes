package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.businesslayer.User;
import recipes.exceptions.UserExistsException;
import recipes.persistence.UserRepository;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository users, PasswordEncoder passwordEncoder) {
        this.userRepository = users;
        this.passwordEncoder = passwordEncoder;
    }

   public void add(User user) {

        if (userRepository.existsById(user.getEmail())) {
            throw new UserExistsException();
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
       userRepository.save(user);
    }


}

