<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${blogName} - ${post.title}</title>
</head>

<body>

<h1>${post.title}</h1>
<div>by ${post.author} <#if post.tags?size != 0>on ${post.tags?join(", ")}</#if></div>

<p><i>${post.summary}</i></p>

<p>${post.content}</p>

</body>

</html>