<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <style>
        /* CSS for table columns */
        td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            color: #333;
            font-weight: bold;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h2>Products</h2>
    <a class="btn btn-primary btn-sm" id="add-more-button" href="#">Add more</a>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>MaSP</th>
            <th>TenSP</th>
            <th>Gia</th>
            <th>SoLuong</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody id="product-list">
        </tbody>
    </table>
</div>

<script>
    // Add a click event handler for the "Add more" button
    $('#add-more-button').click(function(e) {
        e.preventDefault();
        // Redirect to the "add_product.html" page or handle adding a new product here.
        // Example: window.location.href = 'add_product.html';
    });

    // Function to retrieve product data from the RESTful API
    function getProducts() {
        $.ajax({
            url: 'show', // Replace with the correct URL to your RESTful API
            type: 'POST',
            dataType: 'json',
            success: function(data) {
                displayProducts(data);
            },
            error: function() {
                alert('Error retrieving product data.');
            }
        });
    }

    // Function to display product data obtained from the API
    function displayProducts(products) {
        var productTable = $('#product-list');
        productTable.empty(); // Clear the old table content

        products.forEach(function(product) {
            var row = '<tr>' +
                '<td>' + product.id_product + '</td>' +
                '<td>' + product.u_product + '</td>' +
                '<td>' + product.prices + '</td>' +
                '<td>' + product.quantity + '</td>' +
                '<td><a class="btn btn-danger btn-sm delete-button" data-id="' + product.id_product + '">Remove</a></td>' +
                '</tr>';
            productTable.append(row);
        });

        // Add a click event handler for the delete buttons
        $('.delete-button').click(function(e) {
            e.preventDefault();
            var productId = $(this).data('id');
            deleteProduct(productId);
        });
    }

    // Function to delete a product using the RESTful API
    function deleteProduct(productId) {
        $.ajax({
            url: 'view' + productId,
            type: 'DELETE',
            success: function() {
                // Reload the product data after deleting
                getProducts();
            },
            error: function() {
                alert('Error deleting the product.');
            }
        });

    }

    // When the web page is loaded, retrieve product data and display it
    $(document).ready(function() {
        getProducts();
    });
</script>
</body>
</html>