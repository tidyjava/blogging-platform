<#include "head.ftl">

<div class="row">
    <div class="title-box">
        <div class="text-right">
        <#list socialLinks as link>
            <a href="${link.url}" target="_blank">${link.name}</a>
        </#list>
        </div>
        <h1 class="text-center">${title}</h1>
    </div>
</div>

<#include "posts.ftl">
<#include "footer.ftl">