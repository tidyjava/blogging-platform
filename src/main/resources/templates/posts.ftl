<#list posts as post>

<div class="row">
    <div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
        <a class="no-decor-link" href="${post.url}"><h1 class="post-title">${post.title}</h1></a>

        <div class="post-summary">${post.summary}</div>

        <div class="post-metadata">Posted by ${post.author} on ${post.date}
            <#if post.tags?size != 0>
                | Tags: <#list post.tags as tag><a href="${tag.url}">${tag.name}</a> </#list>
            </#if>
        </div>
        <hr>
    </div>
</div>

</#list>