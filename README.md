# Project README

## Overview
This project involves implementing various features for an e-commerce application, including category management, product management, a shopping cart system, and user profiles. Below are the detailed requirements and implementation phases.

---

## Phase 1 - CategoriesController

### Description
The `CategoriesController` class is provided but requires implementation of its methods and proper annotations. Only administrators (users with the `ADMIN` role) can insert, update, or delete a category. The `MySqlCategoriesDao` has defined methods that also need implementation.

### Requirements
The JSON format for a category should be as follows:
```json
{
  "categoryId": 1,
  "name": "Electronics",
  "description": "Explore the latest gadgets and ..."
}
```

### API Endpoints
| VERB   | URL                                  | BODY       |
|--------|--------------------------------------|------------|
| GET    | `http://localhost:8080/categories`  | No body    |
| GET    | `http://localhost:8080/categories/1`| No body    |
| POST   | `http://localhost:8080/categories`  | Category   |
| PUT    | `http://localhost:8080/categories/1`| Category   |
| DELETE | `http://localhost:8080/categories/1`| No body    |

---

## Phase 2 - Fix Bugs in ProductsController

### Bugs
1. **Search Functionality**:
    - Users reported incorrect search results.
    - Test and fix the logic in the search functionality.
    - Query parameters include:
        - `cat` (int): Category ID.
        - `minPrice` (BigDecimal): Lower price range.
        - `maxPrice` (BigDecimal): Upper price range.
        - `color` (String): Color filter.

2. **Duplicate Products**:
    - Updates to products are incorrectly adding new entries instead of modifying existing ones.
    - Ensure administrators can safely update products without duplication.

### Products API Endpoints
| VERB   | URL                                  | BODY       |
|--------|--------------------------------------|------------|
| GET    | `http://localhost:8080/products`    | No body    |
| GET    | `http://localhost:8080/products/1`  | No body    |
| POST   | `http://localhost:8080/products`    | Product    |
| PUT    | `http://localhost:8080/products/1`  | Product    |
| DELETE | `http://localhost:8080/products/1`  | No body    |

---

## Optional Phase 3 - Shopping Cart

### Features
The shopping cart feature allows logged-in users to manage their shopping cart, with data stored in the database. Users can add items, view their cart, and clear the cart.

### JSON Format
```json
{
  "items": {
    "1": {
      "product": {
        "productId": 1,
        "name": "Smartphone",
        "price": 499.99,
        "categoryId": 1,
        "description": "A powerful smartphone for all your communication needs.",
        "color": "Black",
        "stock": 50,
        "imageUrl": "smartphone.jpg",
        "featured": false
      },
      "quantity": 2,
      "discountPercent": 0,
      "lineTotal": 999.98
    },
    "15": {
      "product": {
        "productId": 15,
        "name": "External Hard Drive",
        "price": 129.99,
        "categoryId": 1,
        "description": "Expand your storage capacity.",
        "color": "Gray",
        "stock": 25,
        "imageUrl": "external-hard-drive.jpg",
        "featured": true
      },
      "quantity": 1,
      "discountPercent": 0,
      "lineTotal": 129.99
    }
  },
  "total": 1129.97
}
```

### API Endpoints
| VERB   | URL                                    | BODY       |
|--------|----------------------------------------|------------|
| GET    | `http://localhost:8080/cart`          | No body    |
| POST   | `http://localhost:8080/cart/products/15` | No body    |
| PUT    | `http://localhost:8080/cart/products/15` | Quantity   |
| DELETE | `http://localhost:8080/cart`          | No body    |

### Notes
- **POST**: Adds a product to the cart. If the product exists, increments its quantity.
- **PUT**: Updates the quantity of an existing product in the cart.
- **DELETE**: Clears all items from the cart.

---

## Optional Phase 4 - User Profile

### Features
Users can view and update their profiles. Profiles are created during user registration and managed via a `ProfileController`.

### API Endpoints
| VERB   | URL                                  | BODY         |
|--------|--------------------------------------|--------------|
| GET    | `http://localhost:8080/profile`     | No body      |
| PUT    | `http://localhost:8080/profile`     | Profile body |

### Notes
- Add methods `getByUserId` and `update` in `ProfileDao` and `MySqlProfileDao`.
- Plan and design the implementation before coding.

---

## Summary
This application aims to implement secure and robust features for category management, product management, shopping cart functionality, and user profile management. Each phase builds upon the existing framework to provide a seamless user experience.

