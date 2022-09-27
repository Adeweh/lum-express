package africa.semicolon.lumexpress.data.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated
    private  PaymentType paymentType;
    private BigDecimal amount;
}
