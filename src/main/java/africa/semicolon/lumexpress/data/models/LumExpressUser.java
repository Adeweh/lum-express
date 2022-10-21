package africa.semicolon.lumexpress.data.models;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class LumExpressUser {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String imageUrl;
    private  boolean isEnabled;
    @OneToMany
    @Cascade(CascadeType.ALL)
    private List<Notification> messages = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();


}
