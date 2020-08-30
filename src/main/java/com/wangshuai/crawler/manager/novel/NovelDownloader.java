package com.wangshuai.crawler.manager.novel;

import com.alibaba.fastjson.JSON;
import com.wangshuai.crawler.manager.http.HttpPool;
import com.wangshuai.crawler.manager.http.MessageHttpClient;
import com.wangshuai.crawler.vo.novel.NovelChapterVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NovelDownloader
 *
 * @author wangshuai
 * @version V1.0
 * @date 2020-05-15 23:15
 */
@Component
public class NovelDownloader {

    /**
     * 匹配章节的正则表达式
     */
    private Pattern chapterLiPattern = Pattern.compile("<dd><a href=.*?</dd>");

    private Pattern urlPattern = Pattern.compile("href=\".*?\"");

    private Pattern titlePattern = Pattern.compile("title=\".*?\">");

    /**
     * host
     */
    private String host = "http://www.xxxx.com";

    /**
     * http操作
     */
    @Resource
    private HttpPool httpPool;

    public void downloadNovel() throws Exception {
        Random random = new Random();
        List<String> listUrls = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            listUrls.add(host + "/chapter/20856?page=" + (i + 1));
        }

        MessageHttpClient mhc = httpPool.getMhc();
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0");

        List<NovelChapterVO> chapterVOS = new ArrayList<>();
        for (String url : listUrls) {
            String html = mhc.doGet(url, headers, "utf-8");
            Matcher matcher = chapterLiPattern.matcher(html);
            while (matcher.find()) {
                String chapterLi = html.substring(matcher.start(), matcher.end());
                if (chapterLi.contains("http")) {
                    continue;
                }
                System.out.println(chapterLi);
                chapterVOS.add(generateChapterVOFromLi(chapterLi));
            }
            Thread.sleep(random.nextInt(150));
        }

        File file = new File("/tmp/novel.txt");
        file.deleteOnExit();
        file.createNewFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (NovelChapterVO chapterVO : chapterVOS) {
//            for (int j = 0; j < 3; j ++) {
//                NovelChapterVO chapterVO = chapterVOS.get(j);
                System.out.println(JSON.toJSONString(chapterVO));
                writer.write(chapterVO.getTitle());
                writer.write("\n");
                for (int i = 1; i <= 30; i++) {
                    String html = mhc.doGet(host + chapterVO.getUrl().replace(".html", "") + "_" + i + ".html", headers, "utf-8");
                    Document doc = Jsoup.parse(html);
                    Elements contentElement = doc.select("#BookText");
                    String content = contentElement.html();
                    if (StringUtils.isEmpty(content)) {
                        break;
                    }
                    content = content.replaceAll("&nbsp;", " ").replace("<br>", "").replaceAll("<br />", "\n").replaceAll("<br/>", "\n");
                    chapterVO.setContent(content);
                    writer.write(content);
                    writer.write("\n");
                }
                writer.write("\n");
            }
        }
    }

    private NovelChapterVO generateChapterVOFromLi(String chapterLi) {
        NovelChapterVO chapterVO = new NovelChapterVO();
        Matcher matcher = urlPattern.matcher(chapterLi);
        if (matcher.find()) {
            String url = chapterLi.substring(matcher.start(), matcher.end()).replace("href=\"", "").replace("\"", "");
            chapterVO.setUrl(url);
        }

        matcher = titlePattern.matcher(chapterLi);
        if (matcher.find()) {
            String title = chapterLi.substring(matcher.start(), matcher.end()).replace("title=\"", "").replace("\">", "");
            chapterVO.setTitle(title);
        }
        return chapterVO;
    }

}
