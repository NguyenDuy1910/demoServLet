<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <style>
        /* CSS cho các cột trong bảng */
        td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            color: #333;
            font-weight: bold;}
        /* Thêm kiểu chữ đậm */
    </style>
    <script src="https://ajax.googleapis.com/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <h2>Products</h2>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>MaSP</th>
            <th>TenSP</th>
            <th>Gia</th>
            <th>Cart</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>SP01</td>
            <td>IPhone11</td>
            <td>300.00</td>
            <td>
                <a class="btn btn-primary btn-sm" href="javascript:void(0);" onclick="addToCart('SP01', 'IPhone11', '300.00')">Add to Cart</a>
            </td>
        </tr>
        <tr>
            <td>SP02</td>
            <td>IPhone12</td>
            <td>400.00</td>
            <td>
                <a class="btn btn-primary btn-sm" href="javascript:void(0);" onclick="addToCart('SP02', 'IPhone12', '400.00')">Add to Cart</a>
            </td>
        </tr>
        <tr>
            <td>SP03</td>
            <td>IPhone13</td>
            <td>450.00</td>
            <td>
                <a class="btn btn-primary btn-sm" href="javascript:void(0);" onclick="addToCart('SP03', 'IPhone13', '450.00')">Add to Cart</a>
            </td>
        </tr>
        <tr>
            <td>SP04</td>
            <td>IPhone14</td>
            <td>500.00</td>
            <td>
                <a class="btn btn-primary btn-sm" href="javascript:void(0);" onclick="addToCart('SP04', 'IPhone14', '500.00')">Add to Cart</a>
            </td>
        </tr>
        <tr>
            <td>SP05</td>
            <td>IPhone15</td>
            <td>600.00</td>
            <td>
                <a class="btn btn-primary btn-sm" href="javascript:void(0);" onclick="addToCart('SP05', 'IPhone15', '600.00')">Add to Cart</a>
            </td>
        </tr>
        </tbody>
    </table>
    <a class="btn btn-primary btn-sm" href="show" method="GET">View Cart</a></div>

<script>
    function addToCart(id, productName, price) {
        // Tạo dữ liệu để gửi lên máy chủ
        var formData = new FormData();

        formData.append('id_product', id);
        formData.append('u_product', productName);
        formData.append('prices', price);

        fetch('view', {
            method: 'POST',
            body: formData,
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    alert(data.message);
                } else {
                    alert("No insert product to cart.");
                }
            })
            .catch(error => {
                console.error('error:', error);
            });
    }
</script>
</body>
</html>
