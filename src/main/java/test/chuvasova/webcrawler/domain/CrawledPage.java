package test.chuvasova.webcrawler.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author A.Chuvasova
 * Domain class describes one page we crawl
 */
public class CrawledPage {
    /** url of page */
    private String url;
    /** map with pairs searching word : number of inclusion */
    private Map<String, Integer> keywordMap;
    /** list with no duplicates links */
    private List<String> linksOnPage;
    /** level of depth current page */
    private Integer currentDepth;

    public CrawledPage() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Integer> getKeywordMap() {
        return keywordMap;
    }

    public void setKeywordMap(Map<String, Integer> keywordMap) {
        this.keywordMap = keywordMap;
    }

    public List<String> getLinksOnPage() {
        return linksOnPage;
    }

    public void setLinksOnPage(List<String> linksOnPage) {
        this.linksOnPage = linksOnPage;
    }

    public Integer getCurrentDepth() {
        return currentDepth;
    }

    public void setCurrentDepth(Integer currentDepth) {
        this.currentDepth = currentDepth;
    }

    /**
     * Calculate total inclusion of searching words per one page
     *
     * @return sum of total inclusions each words from keyWordMap
     */
    public Integer calculateTotalInclusion() {
        if (keywordMap != null) {
            return keywordMap.values().stream().mapToInt(Integer::intValue).sum();
        } else {
            return 0;
        }
    }

    /**
     * Generates template of data views in .csv file and console
     *
     * @param keywords - list of searching words
     * @return string template of searching data
     */
    public String toCSVString(List<String> keywords) {
        StringBuilder stringBuilder = new StringBuilder(url);
        stringBuilder.append(" ");
        for (String keyword : keywords) {
            stringBuilder.append(keywordMap.get(keyword));
            stringBuilder.append(" ");
        }
        stringBuilder.append(calculateTotalInclusion());
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrawledPage that = (CrawledPage) o;
        return getUrl().equals(that.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl());
    }
}
