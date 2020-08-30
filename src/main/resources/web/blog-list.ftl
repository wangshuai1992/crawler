<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
    />
    <title>blog list</title>
    <meta http-equiv="Window-target" content="_top" />
    <link rel="stylesheet" href="/lib/css/responsive.css" />
    <link rel="stylesheet" href="/lib/css/base.css" />
    <link rel="icon" type="image/png" href="images/favicon.png" />
    <link rel="apple-touch-icon" href="images/faviconH.png" />

    <link rel="stylesheet" href="/lib/css/index.css" />
  </head>
  <body>
    <div class="main">
      <div class="wrapper">
        <div class="content fn-clear" id="recent-pjax-container">
          <div class="module">
            <div class="module-header fn-clear">
              <style>
                span.module-recent-nav a {
                  padding: 0 5px;
                }
                span.module-recent-nav span.module-hot {
                  display: inline-block;
                  width: 54px;
                  vertical-align: top;
                }
                span.module-recent-nav span.module-hot span {
                  display: none;
                  white-space: nowrap;
                  font-size: 86%;
                }
                span.module-recent-nav span.module-hot:hover span {
                  display: block;
                }
                span.module-recent-nav span.module-hot:hover span a {
                  padding: 0 2px;
                }
                a.down-arrow {
                  display: inline-block;
                  position: relative;
                  width: 40px;
                  margin: 0 20px 5px 0;
                }
                a.down-arrow:after {
                  display: inline-block;
                  content: " ";
                  height: 14px;
                  width: 14px;
                  border-width: 0 4px 4px 0;
                  border-color: #999999;
                  border-style: solid;
                  transform: matrix(0.31, 0.31, -0.31, 0.31, 0, 0);
                  position: absolute;
                  top: -1px;
                }
              </style>
            </div>

            <div class="article-list list">
              <ul>
                <li>
                  <h2 class="fn-ellipsis">
                    <a
                      class="ft-a-title"
                      data-id="1597654554383"
                      data-type="0"
                      rel="bookmark"
                      href="article/1597654554383"
                      >分布式选举和一致性
                    </a>
                  </h2>
                  <div class="fn-flex">
                    <div class="fn-flex-1">
                      <a class="abstract" href="article/1597654554383">
                        前言 背景
                        　　“国不可一日无君”，对应到分布式系统中就是“集群不可一刻无主”。
                        　　相信你对集群的概念并不陌生。简单说，集群一般是由两个或两个以上的服务器组建而成，每个服务器都是一个节点。我们经常会听到数据库集群、管理集群等概念，也知道数据库集群提供了读写功能，管理集群提供了管理、故障恢复等功能。
                        ....
                      </a>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="pagination">
              <span class="current">1</span>
              <a pjax-title="排行" href="recent?p=2">2</a>
              <a pjax-title="排行" href="recent?p=3">3</a>
              <a pjax-title="排行" href="recent?p=4">4</a>
              <a pjax-title="排行" href="recent?p=5">5</a>
              <a pjax-title="排行" href="recent?p=6">6</a>
              <a pjax-title="排行" href="recent?p=7">7</a>
              <a pjax-title="排行" href="recent?p=8">8</a>
              <a pjax-title="排行" href="recent?p=9">9</a>
              <a pjax-title="排行" href="recent?p=10">10</a>
              <a pjax-title="排行" rel="next" href="recent?p=32"
                >32>></a
              >
            </div>
          </div>
        </div>

        <div class="side"></div>
      </div>
    </div>
    <div class="main__down">
      <div class="wrapper"></div>
    </div>
    <div class="footer">
      <!-- <div class="wrapper">
        <div class="slogan">
            学习经验、传播知识、分享智慧的技术交流平台
        </div>
        <div class="fn-flex-1">
        238ms
        </div>
    </div> -->
    </div>

    <script src="/js/symbol-defs.min.js"></script>
    <script src="/lib/js/compress/libs.min.js"></script>
    <script src="/js/common.min.js"></script>

    <script src="/js/channel.min.js"></script>
    <script>
      // Init [Article List] channel
    </script>
    <script>
      // $.pjax({
      //     selector: 'a',
      //     container: '#recent-pjax-container',
      //     show: '',
      //     cache: false,
      //     storage: true,
      //     titleSuffix: '',
      //     filter: function(href){
      //         return 0 > href.indexOf('recent');
      //     },
      //     callback: function () {
      //         Util.parseMarkdown();
      //     }
      // });
      // NProgress.configure({ showSpinner: false });
      // $('#recent-pjax-container').bind('pjax.start', function(){
      //     NProgress.start();
      // });
      // $('#recent-pjax-container').bind('pjax.end', function(){
      //     NProgress.done();
      // });
    </script>
  </body>
</html>
