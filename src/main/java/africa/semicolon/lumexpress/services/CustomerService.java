package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.CustomerRegistrationRequest;
import africa.semicolon.lumexpress.data.dtos.requests.LoginRequest;
import africa.semicolon.lumexpress.data.dtos.requests.UpdateCustomerDetail;
import africa.semicolon.lumexpress.data.dtos.responses.CustomerRegistrationResponse;
import africa.semicolon.lumexpress.data.dtos.responses.LoginResponse;

public interface CustomerService {
    CustomerRegistrationResponse register(CustomerRegistrationRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    String completeProfile(UpdateCustomerDetail updateCustomerDetail);
}
