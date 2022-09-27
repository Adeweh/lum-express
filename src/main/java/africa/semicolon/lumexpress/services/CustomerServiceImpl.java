package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.CustomerRegistrationRequest;
import africa.semicolon.lumexpress.data.dtos.requests.LoginRequest;
import africa.semicolon.lumexpress.data.dtos.requests.UpdateCustomerDetail;
import africa.semicolon.lumexpress.data.dtos.responses.CustomerRegistrationResponse;
import africa.semicolon.lumexpress.data.dtos.responses.LoginResponse;
import africa.semicolon.lumexpress.data.models.Address;
import africa.semicolon.lumexpress.data.models.Cart;
import africa.semicolon.lumexpress.data.models.Customer;
import africa.semicolon.lumexpress.data.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final ModelMapper mapper=new ModelMapper();
    private final CartService cartService;
    @Override
    public CustomerRegistrationResponse register(CustomerRegistrationRequest registerRequest) {
        Customer customer = mapper.map(registerRequest, Customer.class);
        customer.setCart(new Cart());
        Address customerAddress = new Address();
        customerAddress.setCountry(registerRequest.getCountry());
        customer.getAddresses().add(customerAddress);

        Customer savedCustomer = customerRepository.save(customer);
        log.info("customer to be saved in db:: {}", savedCustomer);

        return registrationResponseBuilder(savedCustomer);
    }
    private CustomerRegistrationResponse registrationResponseBuilder(Customer customer) {
        return CustomerRegistrationResponse.builder().message("success").userId(customer.getId()).code(201).build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public String completeProfile(UpdateCustomerDetail updateCustomerDetail) {
        return null;
    }
}
