package test.chuvasova.webcrawler.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import test.chuvasova.webcrawler.crawler.Crawler;
import test.chuvasova.webcrawler.domain.CrawledPage;
import test.chuvasova.webcrawler.export.csv.CrawledPagesExporter;
import test.chuvasova.webcrawler.utils.CrawledPageComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UiController {

    @FXML
    private Button search;
    @FXML
    private TextField wordFirst;
    @FXML
    private TextField wordSecond;
    @FXML
    private TextField wordThird;
    @FXML
    private TextField wordFourth;
    @FXML
    private TextField url;
    @FXML
    private TextField maxDepth;
    @FXML
    private TextField maxCountPage;

    public List<String> inputData() {
        List<String> inputWords = new ArrayList<>();
        String w1 = wordFirst.getText();
        String w2 = wordSecond.getText();
        String w3 = wordThird.getText();
        String w4 = wordFourth.getText();
        inputWords.add(w1);
        inputWords.add(w2);
        inputWords.add(w3);
        inputWords.add(w4);
        return inputWords;
    }

    public String getUrl() {
        String startUrl = url.getText();
        return startUrl;
    }

    public Integer setDepth() {
        Integer maxLinkDepth = Integer.valueOf(maxDepth.getText());
        return maxLinkDepth;
    }

    public Integer setPageCount() {
        Integer maxPageCount = Integer.valueOf(maxCountPage.getText());
        return maxPageCount;
    }

    public void onClickSearch(ActionEvent event) throws Exception {
        List<String> inputKeywords = inputData();
        Integer maxLinkDepth = setDepth();
        Integer maxPageCount = setPageCount();
        String startUrl = getUrl();
        Crawler crawler = new Crawler(maxLinkDepth, maxPageCount);
        List<CrawledPage> crawledPages = crawler.crawlAndSearch(inputKeywords, startUrl);
        CrawledPagesExporter.exportCrawledToCSV(crawledPages, "D:\\csv\\fullData.csv", inputKeywords);
        Collections.sort(crawledPages, new CrawledPageComparator());
        Collections.reverse(crawledPages);
        List<CrawledPage> topTenPages = crawledPages.stream().limit(10).collect(Collectors.toList());
        CrawledPagesExporter.exportCrawledToCSV(topTenPages, "D:\\csv\\top10.csv", inputKeywords);
        topTenPages.stream().forEach(t -> System.out.println(t.toCSVString(inputKeywords)));
    }

    @FXML
    void initialize() {
    }
}
