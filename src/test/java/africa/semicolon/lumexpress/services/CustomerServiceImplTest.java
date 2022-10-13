package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.CustomerRegistrationRequest;
import africa.semicolon.lumexpress.data.dtos.requests.UpdateCustomerDetails;
import africa.semicolon.lumexpress.data.dtos.responses.CustomerRegistrationResponse;
import africa.semicolon.lumexpress.utils.LumExpressUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest

class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    private CustomerRegistrationRequest request;

    @BeforeEach
    void setUp() {
         request = CustomerRegistrationRequest.builder().email("adewehabang@gmail.com").password("Pa55Word").country("Nigeria").build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerTest() {
         CustomerRegistrationResponse customerRegistrationResponse = customerService.register(request);

         assertThat(customerRegistrationResponse).isNotNull();
         assertThat(customerRegistrationResponse.getMessage()).isNotNull();
         assertThat(customerRegistrationResponse.getUserId()).isGreaterThan(0);
         assertThat(customerRegistrationResponse.getCode()).isEqualTo(201);
    }

    @Test
    void login() {
    }

    @Test
    void updateProfileTest() {
        CustomerRegistrationResponse customerRegistrationResponse = customerService.register(request);
        UpdateCustomerDetails details = UpdateCustomerDetails.builder().customerId(customerRegistrationResponse.getUserId())
                .imageUrl(LumExpressUtils.getMockCloudinaryImageUrl())
                .lastName("Ade").city("Lagos").street("Hebert Macauley").state("Lagos").phoneNumber("2209").build();

        var updateResponse = customerService.updateProfile(details);
        assertThat(updateResponse).isNotNull();
        assertThat(updateResponse.contains("success")).isTrue();
    }
}