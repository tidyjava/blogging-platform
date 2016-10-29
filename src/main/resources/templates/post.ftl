<#include "head.ftl">
<#include "home-button.ftl">

<div class="row">
    <div class="post col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
        <h1 class="post-title post-page-title">${post.title}</h1>
        <div class="post-metadata">Posted by ${post.author} on ${post.date}
        <#if post.tags?size != 0>
            | Tags: <#list post.tags as tag><a href="${tag.url}">${tag.name}</a> </#list>
        </#if>
        </div>

        <p>${post.content}</p>

    <#include "disqus/disqus.ftl">
    </div>
</div>

<#include "footer.ftl">