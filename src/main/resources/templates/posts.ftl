<#list posts as post>

<div class="row">
    <div class="col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
        <a class="no-decor-link" href="${post.url}"><h1 class="post-title"><strong>${post.title}</strong></h1></a>

        <p class="summary">${post.summary}</p>
        <div class="post-metadata">Posted by ${post.author} on ${post.date} |
            <#if post.tags?size != 0>
                Tags: <#list post.tags as tag><a href="/tag/${tag}">${tag}</a> </#list>
            </#if></div>
        <hr>
    </div>
</div>

</#list>