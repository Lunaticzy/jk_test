<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>

<#-- document -->
<#if document??>
    <#if document.title??>
        <h1>${document.title}</h1>
    </#if>
    <#if document.content??>
        <p>${document.content}</p>
    </#if>
</#if>

</body>
</html>