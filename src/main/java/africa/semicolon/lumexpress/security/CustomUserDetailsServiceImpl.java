package africa.semicolon.lumexpress.security;

import africa.semicolon.lumexpress.data.models.LumExpressUser;
import africa.semicolon.lumexpress.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LumExpressUser foundUser = userService.getUserByUsername(username);
        return new SecureUser(foundUser);
    }
}