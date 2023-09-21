<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <style>
        /* CSS code here */
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }

        h1 {
            text-align: center;
            margin-top: 20px;
            color: #333;
        }

        .container {
            max-width: 400px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        label {
            font-weight: bold;
        }

        span {
            color: #333;
            display: block;
            margin-bottom: 10px;
        }

        .return-button {
            text-align: center;
            margin-top: 20px;
        }

        .return-button input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .return-button input[type="submit"]:hover {
            background-color: #0056b3;
        }

    </style>
</head>
<body>
<h1>Thanks for joining our email list</h1>
<p>That is information that you entered</p>

<label>Email:</label>
<span>${user.email}</span><br>
<label>Last Name:</label>
<span>${user.lastName}</span><br>
<label>First Name:</label>
<span>${user.firstName}</span><br>

<p>To enter another email address, click on the back button in your browser or the Return button shown below.</p>
<form action="" method="get">
    <input type="hidden" name="action" value="join">
    <input type="submit" value="Return">
</form>
</body>
</html>
