package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    ShoppingCart addProductToCart(int userId, int productId);
    ShoppingCart updateProductInCart(int userId, int productId, int quantity);
    ShoppingCart clearCart(int userId);
}
