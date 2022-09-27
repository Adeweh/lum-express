package africa.semicolon.lumexpress.data.dtos.responses;


import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CustomerRegistrationResponse {
    private Long userId;
    private String message;
    private int code;
}
