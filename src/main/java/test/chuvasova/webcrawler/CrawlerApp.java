package test.chuvasova.webcrawler;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.log4j.Logger;
import test.chuvasova.webcrawler.crawler.Crawler;
import test.chuvasova.webcrawler.domain.CrawledPage;
import test.chuvasova.webcrawler.export.csv.CrawledPagesExporter;
import test.chuvasova.webcrawler.utils.CrawledPageComparator;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author A.Chuvasova
 * Main app class to run test example
 */
public class CrawlerApp {

    private static final Logger log = Logger.getLogger(CrawlerApp.class);

    /**
     * Just a normal psvm method to run the app
     *
     * @param args - command line args, not used
     *             maxLinkDepth - default for link depth
     *             maxPageCount - default for page count
     *             inputKeywords - input list of search words
     */
    public static void main(String[] args) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        log.info("start searching");
        Integer maxLinkDepth = 2;
        Integer maxPageCount = 15;
        List<String> inputKeywords = Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Mask");

        Crawler crawler = new Crawler(maxLinkDepth, maxPageCount);
        List<CrawledPage> crawledPages = crawler.crawlAndSearch(inputKeywords, "https://en.wikipedia.org/wiki/Elon_Musk");
        CrawledPagesExporter.exportCrawledToCSV(crawledPages, "D:\\csv\\fullData.csv", inputKeywords);
        Collections.sort(crawledPages, new CrawledPageComparator());
        Collections.reverse(crawledPages);
        List<CrawledPage> topTenPages = crawledPages.stream().limit(10).collect(Collectors.toList());
        CrawledPagesExporter.exportCrawledToCSV(topTenPages, "D:\\csv\\top10.csv", inputKeywords);
        topTenPages.stream().forEach(t -> System.out.println(t.toCSVString(inputKeywords)));
    }
}