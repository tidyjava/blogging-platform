<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${blogName} - ${tag}</title>
</head>

<body>

<#list posts as post>
<h1><a href="${post.url}">${post.title}</a></h1>

<p>${post.summary}</p>
</#list>

</body>

</html>