<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${blogName}</title>
</head>

<body>

<#list posts as post>
<h1><a href="${post.url}">${post.title}</a></h1>
<div>by ${post.author} <#if post.tags?size != 0>on ${post.tags?join(", ")}</#if></div>

<p>${post.summary}</p>
</#list>

</body>

</html>