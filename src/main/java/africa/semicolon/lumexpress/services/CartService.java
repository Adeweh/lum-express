package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.CartRequest;
import africa.semicolon.lumexpress.data.dtos.responses.CartResponse;
import africa.semicolon.lumexpress.data.models.Cart;

import java.util.List;

public interface CartService {
    CartResponse addProductToCart(CartRequest cartRequest);
    List<Cart> getCartList();


}
