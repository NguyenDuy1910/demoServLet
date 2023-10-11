<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="../style/main.css">
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
<form action="test" method="post">
    <input type="hidden" name="action" value="join">
    <input type="submit" value="Return">
</form>
</body>
</html>