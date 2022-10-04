package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.AddProductRequest;
import africa.semicolon.lumexpress.data.dtos.requests.GetAllItemsRequest;
import africa.semicolon.lumexpress.data.dtos.requests.UpdateProductRequest;
import africa.semicolon.lumexpress.data.dtos.responses.AddProductResponse;
import africa.semicolon.lumexpress.data.dtos.responses.UpdateProductResponse;
import africa.semicolon.lumexpress.data.models.Category;
import africa.semicolon.lumexpress.data.models.Product;
import africa.semicolon.lumexpress.data.repositories.ProductRepository;
import africa.semicolon.lumexpress.exceptions.ProductNotFoundException;
import africa.semicolon.lumexpress.services.cloud.CloudService;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ProductRepository productRepository;
    private final ModelMapper mapper=new ModelMapper();

    private final CloudService cloudService;

    @Override
    public AddProductResponse addProduct(AddProductRequest request) throws IOException {
        Product product = mapper.map(request, Product.class);
        product.getCategories().add(Category.valueOf(request.getProductCategory().toUpperCase()));
        var imageUrl = cloudService.upload(request.getImage().getBytes(), ObjectUtils.emptyMap());
        log.info("cloudinary image url:: {}", imageUrl);
        product.setImageUrl(imageUrl);
        Product savedProduct = productRepository.save(product);
        return buildAddProductResponse(savedProduct);
    }

    private AddProductResponse buildAddProductResponse(Product savedProduct) {
        return AddProductResponse.builder().code(201).productId(savedProduct.getId()).message("product added successfully").build();
    }

    @Override
    public UpdateProductResponse updateProductDetails(Long productId, JsonPatch patch) throws JsonProcessingException, JsonPatchException {
        var foundProduct = productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException(String.format("product with id %d not found", productId)));

        Product updatedProduct = applyPatchToProduct(patch, foundProduct);

        var savedProduct = productRepository.save(updatedProduct);
        return buildUpdateResponse(savedProduct);
    }

    private UpdateProductResponse buildUpdateResponse(Product savedProduct) {
        return UpdateProductResponse.builder()
                .price(savedProduct.getPrice())
                .description(savedProduct.getDescription())
                .productName(savedProduct.getName())
                .statusCode(200)
                .message("product uploaded successfully")
                .build();
    }

    private Product applyPatchToProduct(JsonPatch patch, Product foundProduct) {
        var productNode = objectMapper.convertValue(foundProduct, JsonNode.class);

        JsonNode patchedProductNode;
        try {
            patchedProductNode = patch.apply(productNode);
            var updatedProduct = objectMapper.readValue(objectMapper.writeValueAsBytes(patchedProductNode), Product.class);
            return updatedProduct;
        } catch (IOException | JsonPatchException exception) {
            exception.printStackTrace();
            return null;
        }

    }


    @Override
    public Product getProductById(Long Id) {
        return productRepository.findById(Id).orElseThrow(()-> new ProductNotFoundException(String.format("product with id %d not found", Id)));
        /*if(foundProduct.isPresent()) return foundProduct.get();
        throw new ProductNotFoundException(String.format("product with id %d not found", Id));*/

    }

    @Override
    public Page<Product> getAllProducts(GetAllItemsRequest getAllItemsRequest) {

        Pageable pageSpecs = PageRequest.of(getAllItemsRequest.getPageNumber(), getAllItemsRequest.getNumberOfItemsPerPage() -1);
        Page<Product> products = productRepository.findAll(pageSpecs);

        return products;
    }

    @Override
    public String deleteProduct(Long Id) {
        return null;
    }
}
