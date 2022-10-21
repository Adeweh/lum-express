package africa.semicolon.lumexpress.services;

import africa.semicolon.lumexpress.data.dtos.requests.CartRequest;
import africa.semicolon.lumexpress.data.dtos.responses.CartResponse;
import africa.semicolon.lumexpress.data.models.Cart;
import africa.semicolon.lumexpress.data.models.Item;
import africa.semicolon.lumexpress.data.models.Product;
import africa.semicolon.lumexpress.data.repositories.CartRepository;
import africa.semicolon.lumexpress.exceptions.CartNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    private final ProductService productService;

    @Override
    public CartResponse addProductToCart(CartRequest cartRequest) {
        Cart cart = cartRepository.findById(cartRequest.getCartId()).orElseThrow(()->
                new CartNotFoundException(String.format("cart with id %d not found", cartRequest.getCartId())));

        Product foundProduct = productService.getProductById(cartRequest.getProductId());

        Item item = buildCartItem(foundProduct);
        cart.getItems().add(item);
        Cart cartToSave = updateCartSUbTotal(cart);
        Cart savedCart= cartRepository.save(cartToSave);

        return CartResponse.builder().message("Item added to cart").cart(savedCart).build();
    }

    private Item buildCartItem(Product foundProduct) {
        return Item.builder().product(foundProduct).itemQuantity(1).build();
    }

    private Cart updateCartSUbTotal(Cart cart){
        cart.getItems().forEach(item -> sumCartItemPrices(cart, item));

        return cart;
    }

    private void sumCartItemPrices(Cart cart, Item item) {
        var itemPrice = item.getProduct().getPrice();
        cart.setSubTotal(itemPrice.add(cart.getSubTotal()));
    }
    @Override
    public List<Cart> getCartList() {
        return cartRepository.findAll();
    }
}
