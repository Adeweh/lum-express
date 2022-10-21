package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.LoginRequest;
import africa.semicolon.lumexpress.data.dtos.responses.LoginResponse;
import africa.semicolon.lumexpress.data.models.LumExpressUser;

public interface UserService {
    LoginResponse login(LoginRequest loginRequest);

    LumExpressUser getUserByUsername(String email);

}
