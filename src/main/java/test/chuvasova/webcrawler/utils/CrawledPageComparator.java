package test.chuvasova.webcrawler.utils;

import test.chuvasova.webcrawler.domain.CrawledPage;

import java.util.Comparator;

/**
 * @author A.Chuvasova
 * Comparator for CrawledPage class, needed to compare pages based on total keyword inclusions
 */
public class CrawledPageComparator implements Comparator<CrawledPage> {
    @Override
    public int compare(CrawledPage page1, CrawledPage page2) {
        return page1.calculateTotalInclusion().compareTo(page2.calculateTotalInclusion());
    }
}
