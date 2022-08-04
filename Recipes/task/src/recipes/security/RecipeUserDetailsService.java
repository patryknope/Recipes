package recipes.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.businesslayer.User;
import recipes.persistence.UserRepository;


@Service
@AllArgsConstructor
public class RecipeUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public RecipeUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new RecipeUserDetails(user);
    }
}

