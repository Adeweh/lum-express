package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.CartRequest;
import africa.semicolon.lumexpress.data.dtos.requests.GetAllItemsRequest;
import africa.semicolon.lumexpress.data.dtos.responses.CartResponse;
import africa.semicolon.lumexpress.data.models.Cart;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CartServiceImplTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
    }
    @Test
    @DisplayName("test that product can be added to Cart")
    void addProductToCartTest(){
        CartRequest cartRequest = CartRequest.builder().cartId(cartService.getCartList()
                .get(cartService.getCartList().size()-1).getId()).productId(productService.getAllProducts(new GetAllItemsRequest())
                .getContent().get(productService.getAllProducts(new GetAllItemsRequest()).getNumberOfElements()-1).getId()).build();
        CartResponse cartResponse = cartService.addProductToCart(cartRequest);
        log.info("{}", cartResponse);
        assertThat(cartResponse).isNotNull();
    }

}