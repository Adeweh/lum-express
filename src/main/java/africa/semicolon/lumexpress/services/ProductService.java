package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.AddProductRequest;
import africa.semicolon.lumexpress.data.dtos.requests.UpdateProductRequest;
import africa.semicolon.lumexpress.data.dtos.responses.AddProductResponse;
import africa.semicolon.lumexpress.data.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {
    AddProductResponse addProduct(AddProductRequest request) throws IOException;
    String updateProductDetails(UpdateProductRequest request);
    Product  getProductById(Long Id);
    Page<Product> getAllProducts();
    String deleteProduct(Long Id);



}
