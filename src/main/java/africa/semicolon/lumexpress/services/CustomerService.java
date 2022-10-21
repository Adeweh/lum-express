package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.CustomerRegistrationRequest;
import africa.semicolon.lumexpress.data.dtos.requests.UpdateCustomerDetails;
import africa.semicolon.lumexpress.data.dtos.responses.CustomerRegistrationResponse;
import africa.semicolon.lumexpress.data.models.Customer;

import java.util.List;

public interface CustomerService {
    CustomerRegistrationResponse register(CustomerRegistrationRequest registerRequest);

    String updateProfile(UpdateCustomerDetails updateCustomerDetails);

    List<Customer> getAllCustomers();
}
