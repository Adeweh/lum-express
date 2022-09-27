package africa.semicolon.lumexpress.data.dtos.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String message;
    private int code;
}
