<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
    />
    <title>分布式选举和一致性</title>
    <meta http-equiv="Window-target" content="_top" />
    <link rel="stylesheet" href="/lib/css/responsive.css" />
    <link rel="stylesheet" href="/lib/css/base.css" />
    <link rel="icon" type="image/png" href="/images/favicon.png" />
    <link rel="apple-touch-icon" href="/images/faviconH.png" />

    <link rel="stylesheet" href="/lib/js/compress/article.min.css" />
    <link rel="stylesheet" href="/lib/css/index.css" />
    <link rel="canonical" href="../article/1597654554383" />
  </head>
  <body itemscope itemtype="http://schema.org/Product" class="article">
    <div class="nav"></div>
    <div class="article-body">
      <div class="wrapper">
        <h1 class="article-title" itemprop="name">分布式选举和一致性</h1>
        <div class="content-reset article-content">
          <p></p>
          <h2 id="toc_h2_0">
            <a href="../forward?goto=" target="_blank" rel="nofollow"></a>前言
          </h2>
          <ul>
            <li>
              <strong>背景</strong><br />　　<strong
                >“国不可一日无君”，对应到分布式系统中就是“集群不可一刻无主”。</strong
              ><br />　　相信你对集群的概念并不陌生。简单说，集群一般是由两个或两个以上的服务器组建而成，每个服务器都是一个节点。我们经常会听到数据库集群、管理集群等概念，也知道数据库集群提供了读写功能，管理集群提供了管理、故障恢复等功能。<br />　　接下来，你开始好奇了，对于一个集群来说，多个节点到底是怎么协同，怎么管理的呢。比如，数据库集群，如何保证写入的数据在每个节点上都一致呢？<br />　　也许你会说，这还不简单，选一个“领导”来负责调度和管理其他节点就可以了啊。
              这个想法一点儿也没错。这个“领导”，在分布式中叫做主节点，而选“领导”的过程在分布式领域中叫作分布式选举。<br />　　而另一个解决方案，则是为了高可用，而牺牲了强一致性，只保证最终一致。<br />　　常见的
              Zookeeper 就是为了强一致而牺牲了高可用
              <strong>(满足 CP)</strong>，而 Eureka 则倾向于高可用而牺牲了强一致
              <strong>( 满足 AP）</strong
              ><br />　　分布式选举和一致性的算法，是一个技术活儿，目常用到选主方法有基于序号选举的算法（
              如 Bully 算法）、多数派算法（如 Raft 算法、ZAB
              算法）等，常用到的一致性算法如 Gossip 协议。
              所以接下来，我们就一起去看看吧。
            </li>
            <li>
              <strong>本文内容概要</strong
              ><br />　　为了节省篇幅，这里只是简单归纳一下几个开源组件的处理方式，不深入到代码和细节<br />　　以下内容建立在对相应组件有所了解的基础上进行。
              具体涉及到：<br />　　　　Zookeeper<br />　　　　Redis<br />　　　　Kafka<br />　　　　Eureka<br />　　　　etcd<br />　　　　ElasticSearch<br />　　　　Mysql<br />　　详细内容随性写的，不分先后主次
            </li>
          </ul>
          <h2 id="toc_h2_1">
            <a href="../forward?goto=" target="_blank" rel="nofollow"></a
            >一、ZooKeeper 选举（ZAB 算法）
          </h2>
          <p>
            <strong
              >1. ZK 的选举算法
              ZAB，相信是大家最熟悉或者听的最多的了，那么，在什么场景下，ZK
              需要做选举呢？</strong
            ><br />当 Zookeeper
            集群中的一台服务器出现以下两种情况之一时，需要进入 Leader 选举。<br />　　(1)
            服务器初始化启动，集群中没有 Leader。<br />　　(2)
            服务器运行期间无法和 Leader 保持连接，Leader 挂了。<br />这里分情况来说明<br />1.1：
            服务器启动时期的 Leader 选举
          </p>
          <blockquote>
            <p>
              　　若进行 Leader 选举，则至少需要两台机器，这里选取 3
              台机器组成的服务器集群为例。在集群初始化阶段，当有一台服务器
              Server1 启动时，其单独无法进行和完成 Leader 选举，当第二台服务器
              Server2 启动时，此时两台机器可以相互通信，每台机器都试图找到
              Leader，于是进入 Leader 选举过程。选举过程如下<br />　　<strong
                >(1) 每个 Server 发出一个投票</strong
              >。由于是初始情况，Server1 和 Server2 都会将自己作为 Leader
              服务器来进行投票，每次投票会包含所推举的服务器的 myid 和
              ZXID，使用 <strong>(myid, ZXID)</strong> 来表示，此时 Server1
              的投票为 (1, 0)，Server2 的投票为 (2,
              0)，然后各自将这个投票发给集群中其他机器。<br />　　<strong>(2)&nbsp;接受来自各个服务器的投票</strong>。集群的每个服务器收到投票后，首先判断该投票的有效性，如检查是否是本轮投票、是否来自
              LOOKING 状态的服务器。<br />　　<strong>(3) 处理投票</strong
              >。针对每一个投票，服务器都需要将别人的投票和自己的投票进行 PK，PK
              规则如下<br />　　　　· 优先检查 ZXID。ZXID 比较大的服务器优先作为
              Leader。<br />　　　　· 如果 ZXID 相同，那么就比较 myid。myid
              较大的服务器作为 Leader 服务器。<br />　　　　对于 Server1
              而言，它的投票是 (1, 0)，接收 Server2 的投票为 (2,
              0)，首先会比较两者的 ZXID，均为 0，再比较 myid，此时 Server2 的
              myid 最大，于是更新自己的投票为 (2, 0)，然后重新投票，对于 Server2
              而言，其无须更新自己的投票，只是再次向集群中所有机器发出上一次投票信息即可。<br />　　<strong
                >(4) 统计投票</strong
              >。每次投票后，服务器都会统计投票信息，判断是否已经有过半机器接受到相同的投票信息，对于
              Server1、Server2 而言，都统计出集群中已经有两台机器接受了 (2, 0)
              的投票信息，此时便认为已经选出了 Leader。<br />　　<strong
                >(5) 改变服务器状态</strong
              >。一旦确定了 Leader，每个服务器就会更新自己的状态，如果是
              Follower，那么就变更为 FOLLOWING，如果是 Leader，就变更为
              LEADING。
            </p>
          </blockquote>
          <p>1.2. 服务器运行时期的 Leader 选举</p>
          <blockquote>
            <p>
              　　在 Zookeeper 运行期间，Leader 与非 Leader
              服务器各司其职，即便当有非 Leader
              服务器宕机或新加入，此时也不会影响 Leader，但是一旦 Leader
              服务器挂了，那么整个集群将暂停对外服务，进入新一轮 Leader
              选举，其过程和启动时期的 Leader 选举过程基本一致。假设正在运行的有
              Server1、Server2、Server3 三台服务器，当前 Leader 是
              Server2，若某一时刻 Leader 挂了，此时便开始 Leader
              选举。选举过程如下<br />　　<strong>(1) 变更状态</strong>。Leader
              挂后，余下的非 Observer 服务器都会讲自己的服务器状态变更为
              LOOKING，然后开始进入 Leader 选举过程。<br />　　<strong
                >(2) 每个 Server 会发出一个投票</strong
              >。在运行期间，每个服务器上的 ZXID 可能不同，此时假定 Server1 的
              ZXID 为 123，Server3 的 ZXID 为 122；在第一轮投票中，Server1 和
              Server3 都会投自己，产生投票 (1, 123)，(3,
              122)，然后各自将投票发送给集群中所有机器。<br />　　<strong
                >(3) 接收来自各个服务器的投票</strong
              >。与启动时过程相同。<br />　　<strong>(4) 处理投票</strong
              >。与启动时过程相同，此时，Server1 将会成为 Leader。<br />　　<strong
                >(5) 统计投票</strong
              >。与启动时过程相同。<br />　　<strong
                >(6) 改变服务器的状态</strong
              >。与启动时过程相同。
            </p>
          </blockquote>
          <p>
            <strong>2. 这里实际结合一个例子看看</strong
            ><br />(1)&nbsp;<strong>第一次投票</strong>。无论哪种导致进行 Leader
            选举，集群的所有机器都处于试图选举出一个 Leader 的状态，即 LOOKING
            状态，LOOKING 机器会向所有其他机器发送消息，该消息称为投票。<br />　　假定
            Zookeeper 由 5 台机器组成，SID 分别为 1、2、3、4、5，ZXID 分别为
            9、9、9、8、8，并且此时 SID 为 2 的机器是 Leader
            机器，某一时刻，1、2 所在机器出现故障，因此集群开始进行 Leader
            选举。在第一次投票时，每台机器都会将自己作为投票对象，于是 SID 为
            3、4、5 的机器投票情况分别为 (3, 9)，(4, 8)， (5, 8)。
          </p>
          <p>
            (2)&nbsp;<strong>变更投票</strong>。每台机器发出投票后，也会收到其他机器的投票，每台机器会根据一定规则来处理收到的其他机器的投票，并以此来决定是否需要变更自己的投票，这个规则也是整个
            Leader 选举算法的核心所在，其中术语描述如下<br /><strong
              >· vote_sid</strong
            >：接收到的投票中所推举 Leader 服务器的 SID。<br /><strong
              >· vote_zxid</strong
            >：接收到的投票中所推举 Leader 服务器的 ZXID。<br /><strong
              >· self_sid</strong
            >：当前服务器自己的 SID。<br /><strong>· self_zxid</strong
            >：当前服务器自己的 ZXID。<br />　　每次对收到的投票的处理，都是对
            (vote_sid, vote_zxid) 和(self_sid, self_zxid)对比的过程。<br />　　　　规则一：如果
            vote_zxid 大于
            self_zxid，就认可当前收到的投票，并再次将该投票发送出去。<br />　　　　规则二：如果
            vote_zxid 小于 self_zxid，那么坚持自己的投票，不做任何变更。<br />　　　　规则三：如果
            vote_zxid 等于 self_zxid，那么就对比两者的 SID，如果 vote_sid 大于
            self_sid，那么就认可当前收到的投票，并再次将该投票发送出去。<br />　　　　规则四：如果
            vote_zxid 等于 self_zxid，并且 vote_sid 小于
            self_sid，那么坚持自己的投票，不做任何变更。<br />　　结合上面规则，给出下面的集群变更过程。<br /><img
              src="../upload/7542c96b9c474bfea1355dd6429bb951_image.png"
              alt="7542c96b9c474bfea1355dd6429bb951_image.png"
            />
          </p>
          <p>
            (3)
            <strong>确定 Leader</strong
            >。经过第二轮投票后，集群中的每台机器都会再次接收到其他机器的投票，然后开始统计投票，如果一台机器收到了超过半数的相同投票，那么这个投票对应的
            SID 机器即为 Leader。此时 Server3 将成为 Leader。
          </p>
          <p>
            　　由上面规则可知，通常那台服务器上的数据越新（ZXID
            会越大），其成为 Leader 的可能性越大，也就越能够保证数据的恢复。如果
            ZXID 相同，则 SID 越大机会越大。
          </p>
          <h2 id="toc_h2_2">
            <a href="../forward?goto=" target="_blank" rel="nofollow"></a
            >二、Redis 集群 的一致性 （Gossip 流言协议）
          </h2>
          <p>
            <strong
              >注 1：以下内容基于 Redis Cluster，非单机 redis 主从或哨兵</strong
            >
          </p>
          <p>
            <strong
              >1. 先感受下，下图是基于 Gossip
              协议的官方集群架构图（不用深究，往下看）：</strong
            ><br /><img
              src="../upload/0b08c05c8dce4b7091707741b43df116_image.png"
              alt="0b08c05c8dce4b7091707741b43df116_image.png"
            />
          </p>
          <p><strong>Gossip 协议的概念</strong></p>
          <blockquote>
            <p>
              　　gossip 协议（gossip protocol）又称 epidemic 协议（epidemic
              protocol），是基于流行病传播方式的节点或者进程之间信息交换的协议。<br />在分布式系统中被广泛使用，比如我们可以使用
              gossip 协议来确保网络中所有节点的数据一样。
            </p>
          </blockquote>
          <p>
            为了表述清楚，我们先做一些前提设定：<br />（1）Gossip
            是周期性的散播消息，把周期限定为 1 秒<br />（2）被感染节点随机选择 k
            个邻接节点（fan-out）散播消息，这里把 fan-out 设置为 3，每次最多往 3
            个节点散播。<br />（3）每次散播消息都选择<strong>尚未发送过的节点</strong>进行散播<br />（4）收到消息的节点不再往发送节点散播，比如
            A -&gt; B，那么 B 进行散播的时候，不再发给 A。<br />这里一共有 16
            个节点，节点 1 为初始被感染节点，通过 Gossip
            过程，最终所有节点都被感染：<br /><img
              src="https://img-blog.csdnimg.cn/20190116144643385.gif"
              alt="https://img-blog.csdnimg.cn/20190116144643385.gif"
            />
          </p>
          <p>
            <strong>2. Redis Cluster 的基本运行原理</strong><br />　　Redis
            Cluster
            中的每个节点都<strong>维护一份在自己看来当前整个集群的状态</strong>，主要包括：<br />　　　　1.
            <em>当前集群状态</em><br />　　　　2.
            <em>集群中各节点所负责的 slots 信息，及其 migrate 状态</em
            ><br />　　　　3. <em>集群中各节点的 master-slave 状态</em
            ><br />　　　　4. <em>集群中各节点的存活状态及不可达投票</em>
          </p>
          <p>
            　　也就是说上面的信息，就是集群中 Node
            相互八卦传播流言蜚语的内容主题，而且比较全面，既有自己的更有别人的，这么一来大家都相互传，最终信息就全面而且准确了，区别于拜占庭帝国问题，信息的可信度很高。<br />　　基于
            Gossip 协议当集群状态变化时，如新节点加入、slot
            迁移、节点宕机、slave 提升为新
            Master，我们希望这些变化尽快的被发现，传播到整个集群的所有节点并达成一致。节点之间相互的心跳（PING，PONG，MEET）及其携带的数据是集群状态传播最主要的途径。
          </p>
          <p>
            <strong
              >3. Redis 集群是去中心化的，彼此之间状态同步靠 gossip
              协议通信</strong
            ><br />　　集群的消息有以下几种类型：<br />　　　　*
            <strong>Meet</strong> 通过「cluster meet ip
            port」命令，已有集群的节点会向新的节点发送邀请，加入现有集群。<br />　　　　*
            <strong>Ping</strong> 节点每秒会向集群中其他节点发送 ping
            消息，消息中带有自己已知的两个节点的地址、槽、状态信息、最后一次通信时间等。<br />　　　　*
            <strong>Pong</strong> 节点收到 ping 消息后会回复 pong
            消息，消息中同样带有自己已知的两个节点信息。<br />　　　　*
            <strong>Fail</strong> 节点 ping
            不通某节点后，会向集群所有节点广播该节点挂掉的消息。其他节点收到消息后标记已下线。
          </p>
          <p>
            　　由于去中心化和通信机制，Redis Cluster
            选择了最终一致性和基本可用。
          </p>
          <h2 id="toc_h2_3">
            <a href="../forward?goto=" target="_blank" rel="nofollow"></a
            >三、Elasticsearch 选举 （Bully 算法）
          </h2>
          <p>
            <strong
              >1. Elasticsearch
              集群中节点数量有限，单个节点能够处理和其他所有节点间的连接，集群中不会出现节点频繁加入和离开的情况，因此使用了实现比较简单的
              Bully 算法（ES 有调整）。</strong
            >
          </p>
          <pre><code class="hljs">**Bully 算法**的选择特别霸道和简单，谁活着且谁的 ID 最大谁就是主节点，其他节点必须无条件服从。
这种算法的优点是，选举速度快、算法复杂度低、简单易实现。但这种算法的缺点在于，需要每个节点有全局的节点信息，因此额外信息存储较多；
其次，任意一个比当前主节点 ID 大的新节点或节点故障后恢复加入集群的时候，都可能会触发重新选举，成为新的主节点，如果该节点频繁退出、加入集群，就会导致频繁切主。
</code></pre>
          <p><strong>2. Bully 算法具体的选举过程</strong> 是：</p>
          <blockquote>
            <p>
              　　a. 集群中每个节点判断自己的 ID 是否为当前活着的节点中 ID
              最大的，如果是，则直接向其他节点发送 Victory
              消息，宣誓自己的主权；<br />　　b. 如果自己不是当前活着的节点中 ID
              最大的，则向比自己 ID 大的所有节点发送 Election
              消息，并等待其他节点的回复；<br />　　c.
              若在给定的时间范围内，本节点没有收到其他节点回复的 Alive
              消息，则认为自己成为主节点，并向其他节点发送 Victory
              消息，宣誓自己成为主节点；若接收到来自比自己 ID 大的节点的 Alive
              消息，则等待其他节点发送 Victory 消息；<br />　　d.
              若本节点收到比自己 ID 小的节点发送的 Election 消息，则回复一个
              Alive 消息，告知其他节点，我比你大，重新选举。<br />　　<img
                src="../upload/7388cddae29542f887efd515dcb302a8_image.png"
                alt="7388cddae29542f887efd515dcb302a8_image.png"
              />
            </p>
          </blockquote>
          <p>
            <strong>3 . Elasticsearch 的调整：</strong><br />1）<strong
              >master 节点假死</strong
            ><br />　　master
            可能因负载过重而处于不稳定的状态，可能无法响应某些节点的请求，但短时间内可以恢复正常，为了避免频繁的选举，ES
            中使用了推迟选举的方法，直到 master 失效才进行选举。当节点收不到
            master 的响应时会先请求其他节点，获取活跃 master 的列表，确定 master
            挂掉后再发起选举。<br />2）<strong>脑裂问题</strong
            ><br />　　这个老生常谈了， 一般就是设置 n/2+1
            票获胜（得票人数过半）的原则，Elasticsearch 也是如此。<br />　　Elasticsearch
            采用了设置 “法定得票人数过半”
            解决，在选举过程中当节点得票达到&nbsp;<code>discovery.zen.minimum_master_nodes</code>&nbsp;的值时才能成为
            master。<br />如下图中：<br />　　将上例中
            <code>discovery.zen.minimum_master_nodes</code> 设置为
            3。当两个交换机之间连接中断之后，A 不能再与 C，D，E
            进行通信，C、D、E 所组成的网络分区中不存在活跃的
            master，因此发起选举。A 的集群中只剩下 A 和 B，不足
            minimum_master_nodes，因此放弃 master 身份。C、D、E
            进行投票，直到达成一致选出一个
            master，形成一个新的集群，其中节点数量为 3，刚好满足
            minimum_master_nodes，而 A、B 不再处理来自客户端的请求。<br /><img
              src="../upload/70b89c3e8b6a488c90e16e593c5a3cfc_image.png"
              alt="70b89c3e8b6a488c90e16e593c5a3cfc_image.png"
            />
          </p>
          <h2 id="toc_h2_4">
            <a href="../forward?goto=" target="_blank" rel="nofollow"></a
            >四、Kafka 选举 （分布式锁）
          </h2>
          <p>
            <strong>1. 背景</strong><br />　　在 Kafka 在 0.8
            以前的版本中，是没有 Replication 的，一旦某一个 Broker
            宕机，则其上所有的 Partition 数据都不可被消费，这与 Kafka
            数据持久性及 Delivery Guarantee 的设计目标相悖。同时 Producer
            都不能再将数据存于这些 Partition 中。<br />　　引入 Replication
            之后，同一个 Partition 可能会有多个 Replica，而这时需要在这些
            Replication 之间选出一个 Leader，Producer 和 Consumer 只与这个
            Leader 交互，其它 Replica 作为 Follower 从 Leader 中复制数据。<br />　　因为需要保证同一个
            Partition 的多个 Replica 之间的数据一致性（其中一个宕机后其它
            Replica
            必须要能继续服务并且即不能造成数据重复也不能造成数据丢失）。如果没有一个
            Leader，所有 Replica 都可同时读 / 写数据，那就需要保证多个 Replica
            之间互相（N×N
            条通路）同步数据，数据的一致性和有序性非常难保证，大大增加了
            Replication 实现的复杂性，同时也增加了出现异常的几率。而引入 Leader
            后，只有 Leader 负责数据读写，Follower 只向 Leader 顺序 Fetch
            数据（N 条通路），系统更加简单且高效。<br /><strong>2. 选举</strong
            ><br />　　这里不深入 Kafka 的 replica 的均衡分配策略，只聊选举。<br />　　<strong
              >0).</strong
            >
            Kafka 的选举，本质上是一个分布式锁，有两种方式实现基于 ZooKeeper
            的分布式锁：<br />　　*
            节点名称唯一性：多个客户端创建一个节点，只有成功创建节点的客户端才能获得锁<br />　　*
            临时顺序节点：所有客户端在某个目录下创建自己的临时顺序节点，只有序号最小的才获得锁<br />　　这部分具体就不展开了，
            可以参照我另一篇文章： [
            <a href="../article/1577353910124" rel="nofollow"
              >ZooKeeper 分布式锁的理解</a
            >
            ] (<a href="../article/1577353910124" rel="nofollow"
              >../article/1577353910124</a
            >)<br />　　<strong>1).</strong> Kafka 在 ZooKeeper 中动态维护了一个
            ISR（in-sync replicas），这个 ISR 里的所有 Replica 都跟上了
            leader，只有 ISR 里的成员才有被选为 Leader
            的可能。在这种模式下，对于 f+1 个 Replica，一个 Partition
            能在保证不丢失已经 commit 的消息的前提下容忍 f 个 Replica
            的失败。在大多数使用场景中，这种模式是非常有利的。<br />　　<strong
              >2).</strong
            >
            如何处理所有 Replica 都不工作<br />　　　　在 ISR 中至少有一个
            follower 时，Kafka 可以确保已经 commit 的数据不丢失，但如果某个
            Partition 的所有 Replica
            都宕机了，就无法保证数据不丢失了。这种情况下有两种可行的方案：<br />　　　　　　a.
            等待 ISR 中的任一个 Replica“活”过来，并且选它作为 Leader<br />　　　　　　b.
            选择第一个“活”过来的 Replica（不一定是 ISR 中的）作为 Leader<br />　　　　这就需要在可用性和一致性当中作出一个简单的折衷。如果一定要等待
            ISR 中的 Replica“活”过来，那不可用的时间就可能会相对较长。而且如果
            ISR 中的所有 Replica 都无法“活”过来了，或者数据都丢失了，这个
            Partition 将永远不可用。选择第一个“活”过来的 Replica 作为
            Leader，而这个 Replica 不是 ISR 中的
            Replica，那即使它并不保证已经包含了所有已 commit 的消息，它也会成为
            Leader 而作为 consumer 的数据源（前文有说明，所有读写都由 Leader
            完成）。Kafka 使用了第二种方式。<br />　　<strong>3).</strong>
            如何感知到 Leader 挂了？<br />最简单最直观的方案是，所有 Follower
            都在 ZooKeeper 上设置一个 Watch，一旦 Leader
            宕机，其对应的临时节点会自动删除，此时所有 Follower
            都尝试创建该节点，而创建成功者（ZooKeeper
            保证只有一个能创建成功）即是新的 Leader，其它 Replica 即为
            Follower。<br />　但是该方法会有<strong>3</strong>个问题：<br />　　<strong
              >a. 脑裂</strong
            ><br />　　 　　这是由 ZooKeeper 的特性引起的，虽然 ZooKeeper
            能保证所有 Watch 按顺序触发，但并不能保证同一时刻所有
            Replica“看”到的状态是一样的，这就可能造成不同 Replica
            的响应不一致<br />　　<strong>b. 羊群效应</strong><br />　　
            　　如果宕机的那个 Broker 上的 Partition 比较多，会造成多个 Watch
            被触发，造成集群内大量的调整<br />　　<strong
              >c.ZooKeeper 负载过重</strong
            ><br />　　 　　每个 Replica 都要为此在 ZooKeeper 上注册一个
            Watch，当集群规模增加到几千个 Partition 时 ZooKeeper 负载会过重。<br />　　为此，Kafka
            所有 broker 中选出一个 controller，所有 Partition 的 Leader 选举都由
            controller 决定。controller 会将 Leader 的改变直接通过 RPC
            的方式（比 ZooKeeper Queue 的方式更高效）通知需为为此作为响应的
            Broker。同时 controller 也负责增删 Topic 以及 Replica
            的重新分配。<br />　　下面这个图是 kafka 0.8.x 版本在 zk
            中的路径图，随便看一眼吧，不用深究。<br /><img
              src="../upload/8f722b9755e341529487b326ab510237_image.png"
              alt="8f722b9755e341529487b326ab510237_image.png"
            />
          </p>
          <h2 id="toc_h2_5">
            <a href="../forward?goto=" target="_blank" rel="nofollow"></a
            >五、Eureka 的一致性（二级缓存 + 任务）
          </h2>
          <p>
            <strong
              >1. 就如篇头提到的，ZK 和 Eureka 的区别，Eureka
              其实是没有强一致性的，因为它更偏重于高可用，可以先看下下面的表格对比。</strong
            ><br />　　<img
              src="../upload/e842ee5625a94c1487702ed1e98dbdbc_image.png"
              alt="e842ee5625a94c1487702ed1e98dbdbc_image.png"
            /><br /><strong
              >2. 下面是 Eureka
              注册中心部署在多个机房的架构图，这正是他高可用性的优势（Zookeeper
              不能这么玩）。</strong
            ><br />　　<img
              src="https://static001.infoq.cn/resource/image/8b/8b/8bf6e27c60dbfd717b6830263890368b.png"
              alt=""
            /><br />　　<strong>注册中心在启动后，会进行如下操作</strong
            ><br />　　a). 启动后，从其他节点拉取服务注册信息。<br />　　b).
            运行过程中，定时运行 evict 任务，剔除没有按时 renew
            的服务（包括非正常停止和网络故障的服务）。<br />　　c).
            运行过程中，接收到的 register、renew、cancel
            请求，都会同步至其他注册中心节点。<br /><strong
              >3. Eureka 注册表的数据结构</strong
            ><br />　　Eureka
            的数据存储分了两层：数据存储层和缓存层。（均在内存中，不持久化）<br />　　Eureka
            Client 在拉取服务信息时，先从缓存层获取（相当于
            Redis），如果获取不到，先把数据存储层的数据加载到缓存中（相当于
            Mysql），再从缓存中获取。值得注意的是，数据存储层的数据结构是服务信息，而缓存中保存的是经过处理加工过的、可以直接传输到
            Eureka Client 的数据结构。<br />　　Eureka
            这样的数据结构设计是把内部的数据存储结构与对外的数据结构隔离开了，就像是我们平时在进行接口设计一样，对外输出的数据结构和数据库中的数据结构往往都是不一样的。<br />　　具体结构如下：<br />　　<img
              src="../upload/44e23cb221a14944937c39b724e811ce_image.png"
              alt="44e23cb221a14944937c39b724e811ce_image.png"
            /><br /><strong
              >4. 既然是缓存，那必然要有更新机制，来保证数据的一致性。</strong
            ><br />　　更新机制包含删除和加载两个部分，上图黑色箭头表示删除缓存的动作，绿色表示加载或触发加载的动作。<br />　　下面是缓存的更新机制：<br />　　<strong
              >删除二级缓存：</strong
            ><br />　　 　　a). Eureka Client 发送 register、renew 和 cancel
            请求并更新 registry 注册表之后，删除二级缓存；<br />　　 　　b).
            Eureka Server 自身的 Evict Task 剔除服务后，删除二级缓存；<br />　　
            　　c). 二级缓存本身设置了 guava
            的失效机制，隔一段时间后自己自动失效；<br />　　<strong
              >加载二级缓存：</strong
            ><br />　　 　　a). Eureka Client 发送 getRegistry
            请求后，如果二级缓存中没有，就触发 guava 的 load，即从 registry
            中获取原始服务信息后进行处理加工，再加载到二级缓存中。<br />　　
            　　b). Eureka Server
            更新一级缓存的时候，如果二级缓存没有数据，也会触发 guava 的
            load。<br />　　<strong>更新一级缓存：</strong><br />　　 　　Eureka
            Server 内置了一个
            TimerTask，定时将二级缓存中的数据同步到一级缓存（这个动作包括了删除和加载）。<br />　　<img
              src="../upload/4f2ed2e954db4ee2b40a8783351ab8f3_image.png"
              alt="4f2ed2e954db4ee2b40a8783351ab8f3_image.png"
            /><br /><strong>5. Eureka 的服务注册 / 续约 / 注销 / 剔除</strong
            ><br />　　这里不做深入探究，但都会涉及到二级缓存的清理，阈值的更新，以及服务信息的同步（这点最重要，否则集群里
            server 的持有信息不一致）<br /><strong>6. 服务同步机制</strong
            ><br />　　终于谈到了同步，我们看看 Eureka
            如何保证注册信息最终一致性<br />　　服务同步机制是用来同步 Eureka
            Server 节点之间服务信息的。它包括 Eureka Server
            启动时的同步，和运行过程中的同步。<br />　　<strong
              >a). 启动时同步</strong
            ><br />　　 　　Eureka Server 启动后，遍历
            eurekaClient.getApplications 获取服务信息，并将服务信息注册到自己的
            registry 中。<br />　　
            　　注意这里是两层循环，第一层循环是为了保证已经拉取到服务信息，第二层循环是遍历拉取到的服务信息。<br />　　
            　　<img
              src="../upload/650044b1a57c440b906db31086909207_image.png"
              alt="650044b1a57c440b906db31086909207_image.png"
            /><br />　　<strong>b). 运行时同步</strong><br />　　 　　当 Eureka
            Server 节点有 register、renew、cancel 请求进来时，会将这个请求封装成
            TaskHolder 放到 acceptorQueue 队列中，然后经过一系列的处理，放到
            batchWorkQueue 中。<br />　　
            　　<code>TaskExecutor.BatchWorkerRunnable</code>是个线程池，不断的从
            batchWorkQueue 队列中 poll 出 TaskHolder，然后向其他 Eureka Server
            节点发送同步请求。<br />　　 　　<img
              src="../upload/d14792c84b3a4327b1ad3d3f7c54fe07_image.png"
              alt="d14792c84b3a4327b1ad3d3f7c54fe07_image.png"
            />
          </p>
          <h2 id="toc_h2_6">
            <a href="../forward?goto=" target="_blank" rel="nofollow"></a
            >六、etcd 中的选举和一致性（Raft 算法）
          </h2>
          <p>
            <strong>1. Raft 算法简介</strong><br />　　etcd 核心使用了 RAFT
            分布式一致性协议。为了以容错方式达成一致，我们不可能要求所有服务器
            100%
            都达成一致状态，只要超过半数的大多数服务器达成一致就可以了，假设有 N
            台服务器，N/2 +1 就超过半数，代表大多数了。<br />　　<strong
              >Raft 算法实际上是一个用于管理日志一致性的协议</strong
            >。它将分布式一致性分解为多个子问题：<strong
              >Leader 选举（Leader election）、日志复制（Log
              replication）、安全性（Safety）、日志压缩（Log
              compaction）等</strong
            >。同时，Raft
            算法使用了更强的假设来减少了需要考虑的状态，使之变的易于理解和实现。<strong
              >Raft
              将系统中的角色分为领导者（Leader）、跟从者（Follower）和候选者</strong
            >（Candidate）：<br />　　*
            <strong>Leader</strong>：接受客户端请求，并向 Follower
            同步请求日志，当日志同步到大多数节点上后告诉 Follower 提交日志。<br />　　
            　　 　　<strong
              >注：处理所有客户端交互，日志复制等，一个任期只有一个。</strong
            ><br />　　* <strong>Follower</strong>：接受并持久化 Leader
            同步的日志，在 Leader 告之日志可以提交之后，提交日志。<br />　　
            　　 　　<strong>注：完全被动的选民，是只读的。</strong><br />　　*
            <strong>Candidate</strong>：Leader 选举过程中的临时角色。<br />　　
            　　 　　<strong>注：候选人，可以被选举为新领导。</strong><br />　　
            引用一张知乎上的图，为几个角色之间的状态转换：<br />　　<img
              src="../upload/b1f96ada18954fd397ba67625b2e5124_image.png"
              alt="b1f96ada18954fd397ba67625b2e5124_image.png"
            />
          </p>
          <p>
            　　<strong
              >Raft 算法将时间划分成为任意不同长度的任期（term）</strong
            >。Raft 要求系统在任意时刻最多只有一个 Leader，正常工作期间只有
            Leader 和 Followers。Raft
            算法将时间分为一个个的<strong>任期（term）</strong>，每一个 term
            的开始都是 Leader 选举。在成功选举 Leader 之后，Leader 会在整个 term
            内管理整个集群。如果 Leader 选举失败，该 term 就会因为没有 Leader
            而结束。<br />　　<img
              src="../upload/7a17663c277e46d2810ea32c7a465b9b_image.png"
              alt="7a17663c277e46d2810ea32c7a465b9b_image.png"
            />
          </p>
          <p>
            <strong>2. Raft 算法 选举部分</strong><br />　　Raft
            中使用心跳机制来出发 leader 选举。当服务器启动的时候，服务器成为
            follower。只要 follower 从 leader 或者 candidate 收到有效的 RPCs
            就会保持 follower 状态。如果 follower 在一段时间内（该段时间被称为
            election timeout）没有收到消息，则它会假设当前没有可用的
            leader，然后开启选举新 leader 的流程。<br /><img
              src="../upload/274b6360ff0c4641afe3b3d05add93a4_image.png"
              alt="274b6360ff0c4641afe3b3d05add93a4_image.png"
            /><br />　　如上图的集中可能情况，具体流程解释如下：<br />　　1.
            follower 增加当前的 term，转变为 candidate。<br />　　2. candidate
            投票给自己，并发送 RequestVote RPC 给集群中的其他服务器。<br />　　3.
            收到 RequestVote 的服务器，在同一 term
            中只会按照先到先得投票给至多一个 candidate。且只会投票给 log
            至少和自身一样新的 candidate。<br />　　4. candidate 节点保持 2
            的状态，直到下面三种情况中的一种发生。<br />　　 　　4.1
            该节点赢得选举。即收到大多数的节点的投票。则其转变为 leader
            状态。<br />　　 　　4.2 另一个服务器成为了 leader。即收到了 leader
            的合法心跳包（term 值等于或大于当前自身 term 值）。则其转变为
            follower 状态。<br />　　 　　4.3
            一段时间后依然没有胜者。该种情况下会开启新一轮的选举。 Raft
            中使用随机选举超时时间来保证当票数相同无法确定 leader
            的问题可以被快速的解决。
          </p>
          <h2 id="toc_h2_7">
            <a href="../forward?goto=" target="_blank" rel="nofollow"></a
            >七、Mysql 集群
          </h2>
          <p>
            Mysql 的集群方案比较多，以后另开帖子再展开吧<br />主要的，有几种：<br />　　1.
            官方 Mysql Cluster – 不温不火，近几年发展有点乱，商业应用不多<br />　　2.
            MMM（Master-Master replication manager for MySQL） —
            集群大的情况下，比较多坑<br />　　3. MHA（MySQL Master High
            Availability） — 只负责主库<br />　　4. Keepalived+MySQL —
            存在脑裂问题<br />　　5. DRBD+Heartbeat+MySQL — 同样存在脑裂问题<br />　　6.
            MySQL Proxy — 已不维护
          </p>
          <h2 id="toc_h2_8">
            <a href="../forward?goto=" target="_blank" rel="nofollow"></a
            >最后、关于选举算法的几点比较：
          </h2>
          <p>
            <img
              src="../upload/baeecc6d0f5149f687917527239ddbb3_image.png"
              alt="baeecc6d0f5149f687917527239ddbb3_image.png"
            />
          </p>
          <h4 id="toc_h4_9">
            <a href="../forward?goto=" target="_blank" rel="nofollow"></a
            >参考资料
          </h4>
          <p>
            美团数据库高可用架构的演进与设想：<a
              href="../forward?goto=https%3A%2F%2Ftech.meituan.com%2F2017%2F06%2F29%2Fdatabase-availability-architecture.html"
              target="_blank"
              rel="nofollow"
              >https://tech.meituan.com/2017/06/29/database-availability-architecture.html</a
            ><br />微服务注册中心 Eureka 架构深入解读<a
              href="../forward?goto=https%3A%2F%2Fwww.infoq.cn%2Farticle%2FjlDJQ*3wtN2PcqTDyokh"
              target="_blank"
              rel="nofollow"
              >https://www.infoq.cn/article/jlDJQ*3wtN2PcqTDyokh</a
            ><br />Raft 在 etcd 中的实现（三）选举流程：<a
              href="../forward?goto=https%3A%2F%2Fyuan1028.github.io%2Fetcd-raft-3%2F"
              target="_blank"
              rel="nofollow"
              >https://yuan1028.github.io/etcd-raft-3/</a
            ><br />Zookeeper 选举算法原理（摘选）：<a
              href="../forward?goto=https%3A%2F%2Fwww.jianshu.com%2Fp%2Fc2ced54736aa"
              target="_blank"
              rel="nofollow"
              >https://www.jianshu.com/p/c2ced54736aa</a
            ><br />Kafka 学习之路（三）Kafka 的高可用 ：<a
              href="../forward?goto=https%3A%2F%2Fwww.cnblogs.com%2Fqingyunzong%2Fp%2F9004703.html"
              target="_blank"
              rel="nofollow"
              >https://www.cnblogs.com/qingyunzong/p/9004703.html</a
            ><br />分布式选举 ：<a
              href="../forward?goto=https%3A%2F%2Fzhuanlan.zhihu.com%2Fp%2F142142957"
              target="_blank"
              rel="nofollow"
              >https://zhuanlan.zhihu.com/p/142142957</a
            ><br />Elasticsearch 选主流程：<a
              href="../forward?goto=https%3A%2F%2Fjuejin.im%2Fpost%2F6844903975234306062"
              target="_blank"
              rel="nofollow"
              >https://juejin.im/post/6844903975234306062</a
            ><br />高性能、高可用、可扩展的 MySQL 集群如何组建？：<a
              href="../forward?goto=https%3A%2F%2Fwww.zhihu.com%2Fquestion%2F21307639"
              target="_blank"
              rel="nofollow"
              >https://www.zhihu.com/question/21307639</a
            ><br />Etcd Raft 算法机制：<a
              href="../forward?goto=https%3A%2F%2Fwww.jianshu.com%2Fp%2F6a54e53ae034"
              target="_blank"
              rel="nofollow"
              >https://www.jianshu.com/p/6a54e53ae034</a
            ><br />浅谈集群版 Redis 和 Gossip 协议：<a
              href="../forward?goto=https%3A%2F%2Fjuejin.im%2Fpost%2F6844904002098823181"
              target="_blank"
              rel="nofollow"
              >https://juejin.im/post/6844904002098823181</a
            ><br />MySQL Cluster CGE：<a
              href="../forward?goto=https%3A%2F%2Fwww.mysql.com%2Fcn%2Fproducts%2Fcluster%2F"
              target="_blank"
              rel="nofollow"
              >https://www.mysql.com/cn/products/cluster/</a
            ><br />还有不记得了
          </p>
          <p></p>
        </div>
      </div>
    </div>

    <div class="footer">
      <!-- <div class="wrapper">
        <div class="slogan">
        学习经验、传播知识、分享智慧的技术交流平台
        </div>
    </div> -->
    </div>

    <script src="/js/symbol-defs.min.js"></script>
    <script src="/lib/js/compress/libs.min.js"></script>
    <script src="/js/common.min.js"></script>

    <script src="/lib/js/compress/article-libs.min.js"></script>
    <script src="/lib/js/editor/editor.js"></script>
    <script src="/js/channel.min.js"></script>
    <script src="/js/article.min.js"></script>
  </body>
</html>
