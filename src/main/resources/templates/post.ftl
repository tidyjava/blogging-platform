<#include "head.ftl">
<#include "home-button.ftl">

<div class="row">
    <div class="post col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
        <h1 class="post-title post-page-title">${post.title}</h1>
        <div class="post-metadata">Posted by ${post.author} on ${post.date}
        <#if post.tags?size != 0>
            | Tags: <#list post.tags as tag><a href="/tag/${tag}">${tag}</a> </#list>
        </#if></div>

        <p class="post-summary">${post.summary}</p>

        <p>${post.content}</p>

    <#if disqusEnabled!false>
        <div id="disqus_thread"></div>
        <script>
            var disqus_config = function () {
                this.page.url = '${blogUrl}' + '${post.url}'; // Replace PAGE_URL with your page's canonical URL variable
                this.page.identifier = '${post.id}'; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
            };
            (function () {
                var d = document, s = d.createElement('script');
                s.src = '//${disqusName}.disqus.com/embed.js';
                s.setAttribute('data-timestamp', +new Date());
                (d.head || d.body).appendChild(s);
            })();
        </script>
        <noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript" rel="nofollow">comments
            powered by Disqus.</a></noscript>
    </#if>
    </div>
</div>

<#include "footer.ftl">