package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.AddProductRequest;
import africa.semicolon.lumexpress.data.dtos.responses.AddProductResponse;
import africa.semicolon.lumexpress.data.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;
    AddProductRequest request;

    @BeforeEach
    void setUp() throws IOException {
        Path path = Paths.get("/home/adeh/Downloads/peak.jpeg");
        MultipartFile file = new MockMultipartFile("peak", Files.readAllBytes(path));
        request= AddProductRequest.builder().name("Milk").productCategory("Beverages").price(BigDecimal.valueOf(30.00)).quantity(10).
//                image(file).
                build();
    }

    @Test
    void addProduct() throws IOException {
       AddProductResponse response = productService.addProduct(request);
       assertThat(response).isNotNull();
       assertThat(response.getProductId()).isGreaterThan(0l);
       assertThat(response.getMessage()).isNotNull();
       assertThat(response.getCode()).isEqualTo(201);
    }

    @Test
    void updateProductDetails() {
    }

    @Test
    void getProductByIdTest() throws IOException {
        var response = productService.addProduct(request);
        var foundProduct = productService.getProductById(response.getProductId());
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(response.getProductId());
    }

    @Test
    void getAllProductsTest() {
        Page<Product> productPage = productService.getAllProducts();
        assertThat(productPage).isNotNull();
        assertThat(productPage.getTotalElements()).isGreaterThan(0);
    }
}