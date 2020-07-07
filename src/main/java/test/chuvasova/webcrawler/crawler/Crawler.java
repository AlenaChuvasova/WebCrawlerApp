package test.chuvasova.webcrawler.crawler;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import test.chuvasova.webcrawler.domain.CrawledPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Crawls a page (get connection, collect data of keywords inclusion, search for childPages)
 */
public class Crawler {

    private final Integer maxLinkDepth;
    private final Integer maxPageCount;
    /** list of crawled pages */
    private List<CrawledPage> result = new ArrayList<>();

    /**
     * Default constructor for Crawler
     *
     * @param maxLinkDepth maximum depth of links on a page to follow
     * @param maxPageCount maximum number of pages to crawl
     */
    public Crawler(Integer maxLinkDepth, Integer maxPageCount) {
        this.maxLinkDepth = maxLinkDepth;
        this.maxPageCount = maxPageCount;
    }

    /**
     * General method for crawling pages and collect data
     *
     * @param keywordsToSearch - list of search words
     * @param startUrl         - url for the start connection
     * @return a of {@link List} {@link CrawledPage} with amount of keyword inclusion as {@link java.util.Map}
     */
    public List<CrawledPage> crawlAndSearch(List<String> keywordsToSearch, String startUrl) {
        if (result.size() < maxPageCount) {
            CrawledPage page = getCrawledPageFromUrl(keywordsToSearch, startUrl, null);
            if (!result.contains(page)) {
                result.add(page);
            }
            if (result.size() < maxPageCount) {
                crawlChildPages(keywordsToSearch, page);
            }
        }
        return result;
    }

    /**
     * Method for crawling child page(s) if exist
     *
     * @param keywordsToSearch - list of search words
     * @param page             - instance for crawling
     */
    private void crawlChildPages(List<String> keywordsToSearch, CrawledPage page) {
        if (page.getLinksOnPage() != null && !page.getLinksOnPage().isEmpty()) {
            List<CrawledPage> childPages = new ArrayList<>();
            for (String url : page.getLinksOnPage()) {
                if (result.size() < maxPageCount) {
                    CrawledPage crawledChild = getCrawledPageFromUrl(keywordsToSearch, url, page);
                    if (!result.contains(crawledChild)) {
                        result.add(crawledChild);
                    }
                    if (!childPages.contains(crawledChild)) {
                        childPages.add(crawledChild);
                    }
                } else {
                    break;
                }
            }
            if (result.size() < maxPageCount) {
                for (CrawledPage childPage : childPages) {
                    crawlChildPages(keywordsToSearch, childPage);
                }
            }
        }
    }

    /**
     * Get page for crawling
     *
     * @param keywordsToSearch - list of search words
     * @param startUrl         - url for the first  connection
     * @param parent           - parent page if exist
     * @return crawledPage with data
     */
    private CrawledPage getCrawledPageFromUrl(List<String> keywordsToSearch, String startUrl, CrawledPage parent) {
        CrawledPage crawledPage = null;
        Connection connection = Jsoup.connect(startUrl);
        Document htmlDocument;
        try {
            htmlDocument = connection.get();
            crawledPage = getCrawledPageFromHtmlDocument(keywordsToSearch, startUrl, parent, htmlDocument);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return crawledPage;
    }

    /**
     * Search and count keywords inclusion on the crawledPage (in bodeText using StringUtils)
     *
     * @param keywordsToSearch - list of search words
     * @param startUrl         - url for the first  connection
     * @param parent           - parent page if exist
     * @param htmlDocument     - html view from startUrl
     * @return crawledPage with data
     */
    public CrawledPage getCrawledPageFromHtmlDocument(List<String> keywordsToSearch, String startUrl, CrawledPage parent, Document htmlDocument) {
        CrawledPage crawledPage = new CrawledPage();
        String bodyText = htmlDocument.body().text().toLowerCase();
        Map<String, Integer> inclusionMap = new HashMap<>();
        for (String toSearch : keywordsToSearch) {
            inclusionMap.put(toSearch, StringUtils.countMatches(bodyText, toSearch.trim().toLowerCase()));
        }
        crawledPage.setKeywordMap(inclusionMap);
        crawledPage.setUrl(startUrl);
        if (parent != null) {
            crawledPage.setCurrentDepth(parent.getCurrentDepth() + 1);
        } else {
            crawledPage.setCurrentDepth(0);
        }
        if (crawledPage.getCurrentDepth() <= maxLinkDepth) {
            List<String> linksOnPage = htmlDocument.select("a[href]").stream().distinct().map(t -> t.absUrl("href")).collect(Collectors.toList());
            crawledPage.setLinksOnPage(linksOnPage);
        }
        return crawledPage;
    }
}
