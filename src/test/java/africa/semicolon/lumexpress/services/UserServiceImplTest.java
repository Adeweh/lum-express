package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.LoginRequest;
import africa.semicolon.lumexpress.data.models.Cart;
import africa.semicolon.lumexpress.data.models.Customer;
import africa.semicolon.lumexpress.data.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerRepository customerRepository;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        var customer= Customer.builder().cart(new Cart()).build();
        customer.setEmail("adewehabang@gmail.com");
        customer.setFirstName("Dee");
        customer.setLastName("Bee");
        customer.setPassword("pa55w0rd");

        customerRepository.save(customer);
        loginRequest= LoginRequest.builder().email("adewehabang@gmail.com").password("pa55w0rd").build();
    }

    @Test
    void loginUserTest() {
        var response = userService.login(loginRequest);
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(200);
    }
}