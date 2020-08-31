<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
    />
    <title>article-detail</title>
    <meta http-equiv="Window-target" content="_top"/>
    <link rel="stylesheet" href="/lib/css/responsive.css"/>
    <link rel="stylesheet" href="/lib/css/base.css"/>
    <link rel="icon" type="image/png" href="/images/favicon.png"/>
    <link rel="apple-touch-icon" href="/images/faviconH.png"/>

    <link rel="stylesheet" href="/lib/js/compress/article.min.css"/>
    <link rel="stylesheet" href="/lib/css/index.css"/>
    <script src="/lib/js/jquery-3.2.1.js" type="text/javascript"></script>
</head>
<body itemscope itemtype="http://schema.org/Product" class="article">
<div class="nav"></div>
<div class="article-body">
    <div class="wrapper">
<#--        <h1 class="article-title" itemprop="name">xxxxxxxx</h1>-->
        <h1 class="article-title" itemprop="name">Loading...</h1>
        <div class="content-reset article-content">

        </div>
    </div>
</div>

<div class="footer article-footer">
    <!-- <div class="wrapper">
      <div class="slogan">
      学习经验、传播知识、分享智慧的技术交流平台
      </div>
  </div> -->
</div>
<#--<script src="/js/symbol-defs.min.js"></script>-->
<#--<script src="/lib/js/compress/libs.min.js"></script>-->
<#--<script src="/js/common.min.js"></script>-->

<#--<script src="/lib/js/compress/article-libs.min.js"></script>-->
<#--<script src="/lib/js/editor/editor.js"></script>-->
<#--<script src="/js/channel.min.js"></script>-->
<#--<script src="/js/article.min.js"></script>-->
<script>

    let articleId = "${articleId}";
    $.getJSON('get-article.json?articleId=' + articleId, function (article) {
        let title = article.articleTitle;
        let content = article.articleContent;
        fillTitle(title);
        fillContent(content);
    });

    function fillTitle(title) {
        $("h1.article-title").html(title);
    }

    function fillContent(content) {
        $(".article-content").html(content);
    }
</script>
</body>
</html>
