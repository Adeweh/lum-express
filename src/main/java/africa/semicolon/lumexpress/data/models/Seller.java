package africa.semicolon.lumexpress.data.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Seller extends LumExpressUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private  Store store;
    @OneToMany
    private Set<Address> address;
}
