package africa.semicolon.lumexpress.data.dtos.requests;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class CustomerRegistrationRequest {
    @NotNull(message = "country cannot be null")
    @NotEmpty(message = "country cannot be empty, No try me!")
    private String country;
    @Email(message = "invalid email")
    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty, No try me!")
    private String email;
    @NotNull(message = "provide name please")
    @NotEmpty(message = "I said name, No try me!")
    private String firstName;
    @NotNull
    @NotEmpty
    private String password;
}
