package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.AddProductRequest;
import africa.semicolon.lumexpress.data.dtos.requests.UpdateProductRequest;
import africa.semicolon.lumexpress.data.dtos.responses.AddProductResponse;
import africa.semicolon.lumexpress.data.models.Category;
import africa.semicolon.lumexpress.data.models.Product;
import africa.semicolon.lumexpress.data.repositories.ProductRepository;
import africa.semicolon.lumexpress.exceptions.ProductNotFoundException;
import africa.semicolon.lumexpress.services.cloud.CloudService;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

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
    public String updateProductDetails(UpdateProductRequest request) {
        return null;
    }

    @Override
    public Product getProductById(Long Id) {
        return productRepository.findById(Id).orElseThrow(()-> new ProductNotFoundException(String.format("product with id %d not found", Id)));
        /*if(foundProduct.isPresent()) return foundProduct.get();
        throw new ProductNotFoundException(String.format("product with id %d not found", Id));*/

    }

    @Override
    public Page<Product> getAllProducts() {

        Pageable pageSpecs = PageRequest.of(0, 5);
        productRepository.findAll(pageSpecs);

        return null;
    }

    @Override
    public String deleteProduct(Long Id) {
        return null;
    }
}
