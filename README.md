# Product Master

A Spring Boot--based **Product Management System** for managing
**Categories, Sub-Categories, Products, and Users**. The application
uses **session-based authentication**, JSP pages, AJAX/jQuery for
frontend-backend communication, PostgreSQL for data persistence, and
Apache POI for Excel exports.

## Features

-   User login with session-based authentication
-   BCrypt password verification
-   Category CRUD operations
-   Sub-Category CRUD operations
-   Product CRUD operations
-   Product image upload
-   Search and filtering
-   Soft delete functionality
-   Excel export
-   Date validation
-   Uniqueness validation
-   AJAX-based frontend interactions

## Technology Stack

**Backend:** Java, Spring Boot, Spring MVC, Spring Data JPA, Hibernate\
**Security:** Session-based authentication, BCrypt password hashing\
**Frontend:** JSP, HTML, CSS, JavaScript, jQuery, AJAX\
**Database:** PostgreSQL\
**Excel Export:** Apache POI\
**Build Tool:** Maven

## Modules

### 1. User Management and Authentication

The application provides session-based authentication to protect the
master modules.

#### Login and Session Flow

1.  The user navigates to the login page and enters their username and
    password.
2.  The submitted password is verified against the stored BCrypt
    password hash.
3.  After successful authentication, a `UserDTO` is stored in the HTTP
    session.
4.  The session is used to validate subsequent requests.
5.  Category, Sub-Category, and Product pages verify that a valid user
    session exists.
6.  If the session is unavailable or expired, the user is redirected to
    the login page.
7.  On logout, the HTTP session is invalidated and the user is
    redirected to the login page.

## 2. Category Master

The Category Master module provides CRUD operations for product
categories.

### Functionality

-   Create a new category
-   View existing categories
-   Update category details
-   Soft delete categories
-   Validate category name uniqueness
-   Manage active status
-   Export category data to Excel

### Category CRUD Flow

The authenticated user opens the Category Master page. JSP pages use
jQuery and AJAX to communicate with REST endpoints.

When creating a category, the category name is checked for uniqueness
using a case-insensitive comparison before it is saved.

Users can update:

-   Category name
-   Description
-   Active status

Instead of permanently deleting records, the application uses **soft
delete**. Deleted records are assigned an active flag value of `9`.

Queries such as `findByActiveNot(9)` exclude soft-deleted records while
keeping them available in the database.

Category data can be exported as an XLSX file using the
`/category/excel` endpoint. The Excel file is generated using Apache POI
and returned as a downloadable attachment.

## 3. Sub-Category Master

The Sub-Category Master module manages sub-categories and their
relationship with parent categories.

### Functionality

-   Create sub-categories
-   View active sub-categories
-   Update sub-category information
-   Soft delete sub-categories
-   Associate a sub-category with a parent category
-   Search sub-categories
-   Export filtered or complete data to Excel

### Sub-Category CRUD and Search Flow

The Sub-Category Master page displays active sub-categories along with
their associated parent category names.

Before creating a new sub-category, the application verifies that the
sub-category name is unique.

Search functionality is available through:

`/subcategory/search?keyword=`

The search can filter records based on:

-   Sub-category name
-   Description
-   Parent category name

Excel export supports an optional keyword parameter, allowing users to
export either all sub-category records or only filtered search results.

## 4. Product Master

The Product Master module provides complete product management
functionality.

### Product Information

A product can contain information such as:

-   Product name
-   Price
-   Description
-   Discount
-   Product condition
-   Color
-   Manufacturing date
-   Valid-from date
-   Valid-to date
-   Serial number
-   Warranty
-   Product image

### Product CRUD Flow

Product creation uses `multipart/form-data` so that product details and
an optional product image can be submitted together.

The product is created through:

`POST /product`

When an image is uploaded, it is stored in the configured upload
directory with a UUID-based unique filename. The corresponding image
path or filename is persisted in the database.

The application checks product name uniqueness before creating a new
product.

### Date Validation

The Product Master applies the following validation rules:

-   Manufacturing date must not be in the future.
-   Valid-from date must not be in the future.
-   Valid-to date must be today or a future date.
-   Valid-to date must not be earlier than valid-from date.

### Soft Delete

Products are not permanently removed from the database.

When a product is deleted, its active flag is set to `9`. Queries such
as `findByActiveNot(9)` exclude deleted products from normal application
views while preserving their data.

### Excel Export

Product data can be exported to an XLSX file using:

`/product/excel`

The generated Excel file is returned as an attachment download response.

## Application Flow

``` text
User
  |
  v
Login Page
  |
  v
BCrypt Password Verification
  |
  v
HTTP Session Created (UserDTO)
  |
  +----------------------+
  |                      |
  v                      v
Category Master    Sub-Category Master
  |                      |
  +----------+-----------+
             |
             v
       Product Master
             |
             v
 CRUD / Search / Image Upload / Excel Export
             |
             v
          Database
```

## Entity Relationship Overview

``` text
Category
   |
   | 1
   |
   | N
Sub-Category
   |
   | 1
   |
   | N
Product
```

A **Category** can contain multiple **Sub-Categories**, and a
**Sub-Category** can be associated with multiple **Products**.

## Security

The application uses session-based authentication.

-   Passwords are verified using BCrypt.
-   Authenticated user information is maintained using an HTTP session.
-   Protected pages verify the session before allowing access.
-   Invalid or expired sessions redirect users to the login page.
-   Logout invalidates the current HTTP session.

## Soft Delete Strategy

Instead of permanently deleting records, the system uses an `active`
flag.

``` text
Normal Record  -> Active status
Deleted Record -> active = 9
```

This approach preserves historical data while preventing deleted records
from appearing in normal application queries.

## Future Enhancements

Possible future improvements include:

-   Spring Security integration
-   JWT-based authentication
-   Role-based authorization
-   Pagination and advanced filtering
-   Audit logging
-   Cloud-based image storage
-   REST API documentation using Swagger/OpenAPI
-   Docker deployment

## Author

**Janvi Chauhan**
