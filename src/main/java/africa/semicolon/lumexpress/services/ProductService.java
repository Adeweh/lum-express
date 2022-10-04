package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.AddProductRequest;
import africa.semicolon.lumexpress.data.dtos.requests.GetAllItemsRequest;
import africa.semicolon.lumexpress.data.dtos.requests.UpdateProductRequest;
import africa.semicolon.lumexpress.data.dtos.responses.AddProductResponse;
import africa.semicolon.lumexpress.data.dtos.responses.UpdateProductResponse;
import africa.semicolon.lumexpress.data.models.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ProductService {
    AddProductResponse addProduct(AddProductRequest request) throws IOException;
    UpdateProductResponse updateProductDetails(Long productId, JsonPatch patch) throws JsonProcessingException, JsonPatchException;
    Product  getProductById(Long Id);
    Page<Product> getAllProducts(GetAllItemsRequest getAllItemsRequest);
    String deleteProduct(Long Id);



}
