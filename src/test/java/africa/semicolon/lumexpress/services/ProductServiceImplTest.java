package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.AddProductRequest;
import africa.semicolon.lumexpress.data.dtos.requests.GetAllItemsRequest;
import africa.semicolon.lumexpress.data.dtos.responses.AddProductResponse;
import africa.semicolon.lumexpress.data.dtos.responses.UpdateProductResponse;
import africa.semicolon.lumexpress.data.models.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;
    private AddProductRequest request;

    private  AddProductResponse response;

    @BeforeEach
    void setUp() throws IOException {

        Path path = Paths.get("/home/adeh/Downloads/peak.jpeg");
        MultipartFile file = new MockMultipartFile("peak", Files.readAllBytes(path));
        request= AddProductRequest.builder().name("Milk").productCategory("Beverages").price(BigDecimal.valueOf(30.00)).quantity(10).
               image(file).
                build();

        response = productService.addProduct(request);
    }

    @Test
    void addProduct() throws IOException {
       assertThat(response).isNotNull();
       assertThat(response.getProductId()).isGreaterThan(0l);
       assertThat(response.getMessage()).isNotNull();
       assertThat(response.getCode()).isEqualTo(201);
    }

    @Test
    void updateProductDetailsTest() throws JsonPointerException, IOException, JsonPatchException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode value = mapper.readTree("\"eggs\"");
        JsonPatch patch = new JsonPatch(List.of(new ReplaceOperation(new JsonPointer("/name"), value)));

        UpdateProductResponse updateResponse = productService.updateProductDetails(1L, patch);
        log.info("updated product:: {}", updateResponse);
        assertThat(updateResponse).isNotNull();
        assertThat(updateResponse.getStatusCode()).isEqualTo(200);
        assertThat(productService.getProductById(1L).getName()).isEqualTo("eggs");

    }

    @Test
    void getProductByIdTest() throws IOException {
        var foundProduct = productService.getProductById(response.getProductId());
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(response.getProductId());
    }

    @Test
    void getAllProductsTest() throws IOException {
        var getItemsRequest = buildGetItemsRequest();

        Page<Product> productPage = productService.getAllProducts(getItemsRequest);
        log.info("page contents:: {}", productPage);
        assertThat(productPage).isNotNull();
        assertThat(productPage.getTotalElements()).isGreaterThan(0);
    }

    private GetAllItemsRequest buildGetItemsRequest(){
        return GetAllItemsRequest.builder().numberOfItemsPerPage(8).pageNumber(1).build();
    }

    @Test
    void deleteProductTest(){

    }
}