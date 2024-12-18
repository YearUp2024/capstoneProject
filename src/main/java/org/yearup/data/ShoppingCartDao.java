package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    ShoppingCart addItem(int userId, int productId);
    ShoppingCart updateCartItem(int userId, int productId, int quantity);
    ShoppingCart deleteCart(int userId);
}
