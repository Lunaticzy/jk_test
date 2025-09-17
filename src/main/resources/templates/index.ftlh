<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
 <p>${message}</p>

<#-- current user -->
<#if user??>
    <p> hello ${user.name}! </p>
</#if>
</body>
</html>