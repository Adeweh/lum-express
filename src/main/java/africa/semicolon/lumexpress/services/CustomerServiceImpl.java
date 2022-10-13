package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.CustomerRegistrationRequest;
import africa.semicolon.lumexpress.data.dtos.requests.EmailNotificationRequest;
import africa.semicolon.lumexpress.data.dtos.requests.UpdateCustomerDetails;
import africa.semicolon.lumexpress.data.dtos.responses.CustomerRegistrationResponse;
import africa.semicolon.lumexpress.data.models.Address;
import africa.semicolon.lumexpress.data.models.Cart;
import africa.semicolon.lumexpress.data.models.Customer;
import africa.semicolon.lumexpress.data.models.VerificationToken;
import africa.semicolon.lumexpress.data.repositories.CustomerRepository;
import africa.semicolon.lumexpress.exceptions.UserNotFoundException;
import africa.semicolon.lumexpress.services.notification.EmailNotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    private final  VerificationTokenService verificationTokenService;
    private final ModelMapper mapper=new ModelMapper();
    private final EmailNotificationService emailNotificationService;
    @Override
    public CustomerRegistrationResponse register(CustomerRegistrationRequest registerRequest) {
        Customer customer = mapper.map(registerRequest, Customer.class);
        customer.setCart(new Cart());
        setCustomerAddress(registerRequest, customer);

        Customer savedCustomer = customerRepository.save(customer);
        log.info("customer to be saved in db:: {}", savedCustomer);
        var token= verificationTokenService.createToken(savedCustomer.getEmail());

        emailNotificationService.sendHtmlMail(buildEmailNotificationRequest(token, savedCustomer.getFirstName()));

        return registrationResponseBuilder(savedCustomer);
    }

    private EmailNotificationRequest buildEmailNotificationRequest(VerificationToken verificationToken, String customerName) {
        var message= getEmailTemplate();
        String mail = null;
        if(message != null){
            var verificationUrl = "http://localhost:8080/api/v1/customer/verify/"+verificationToken.getToken();
        mail = String.format(message, customerName, verificationUrl);
            log.info("mailed url->{}", verificationUrl);
        }

       return EmailNotificationRequest.builder()
                .userEmail(verificationToken.getUserEmail()).mailContent(mail).build();
    }
    private String getEmailTemplate(){
     try (BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/adeh/Documents/lum-express/src/main/resources/welcome.txt"))){
        return bufferedReader.lines().collect(Collectors.joining());
     }catch (IOException exception){
         exception.printStackTrace();

     }
        return null;
    }

    private void setCustomerAddress(CustomerRegistrationRequest registerRequest, Customer customer) {
        Address customerAddress = new Address();
        customerAddress.setCountry(registerRequest.getCountry());
        customer.getAddresses().add(customerAddress);
    }

    private CustomerRegistrationResponse registrationResponseBuilder(Customer customer) {
        return CustomerRegistrationResponse.builder().message("success").userId(customer.getId()).code(201).build();
    }

    @Override
    public String updateProfile(UpdateCustomerDetails updateCustomerDetails) {
        Customer customerToUpdate = customerRepository.findById(updateCustomerDetails.getCustomerId()).orElseThrow(()-> new UserNotFoundException
                (String.format("customer with id %d, noy found", updateCustomerDetails.getCustomerId())));
        log.info("before update->{}", customerToUpdate);
        mapper.map(updateCustomerDetails, customerToUpdate);

        Set<Address> customerAddressList = customerToUpdate.getAddresses();
        log.info("addresses{}", customerAddressList);

        Optional<Address> foundAddress = customerAddressList.stream().findFirst();
        if(foundAddress.isPresent()) applyAddressUpdate(foundAddress.get(), updateCustomerDetails);

        customerToUpdate.getAddresses().add(foundAddress.get());
        Customer updatedCustomer = customerRepository.save(customerToUpdate);
        log.info("updated guy->{}", updatedCustomer);

        return String.format("%s details updated successfully", updatedCustomer.getFirstName());
    }

    private void applyAddressUpdate(Address address, UpdateCustomerDetails updateCustomerDetails) {
        address.setCity(updateCustomerDetails.getCity());
        address.setBuildingNumber(updateCustomerDetails.getBuildingNumber());
        address.setState(updateCustomerDetails.getState());
        address.setStreet(updateCustomerDetails.getStreet());

    }

}
