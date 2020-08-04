package test.chuvasova.webcrawler.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    static List<CrawledPage> topTenPages;
    private List<String> inputWords = new ArrayList<>();
    static List<String> inputKeywords;

    public List<String> inputData() {
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
        inputKeywords = inputData();
        Integer maxLinkDepth = setDepth();
        Integer maxPageCount = setPageCount();
        String startUrl = getUrl();
        Crawler crawler = new Crawler(maxLinkDepth, maxPageCount);
        List<CrawledPage> crawledPages = crawler.crawlAndSearch(inputKeywords, startUrl);
        CrawledPagesExporter.exportCrawledToCSV(crawledPages, "D:\\csv\\fullData.csv", inputKeywords);
        Collections.sort(crawledPages, new CrawledPageComparator());
        Collections.reverse(crawledPages);
        topTenPages = crawledPages.stream().limit(10).collect(Collectors.toList());
        CrawledPagesExporter.exportCrawledToCSV(topTenPages, "D:\\csv\\top10.csv", inputKeywords);
        Parent root = FXMLLoader.load(getClass().getResource("/output.fxml"));
        Scene scene = new Scene(root, 405, 526);
        scene.getStylesheets().add("/style.css");
        Stage secondStage = new Stage();
        secondStage.setScene(scene);
        secondStage.initModality(Modality.WINDOW_MODAL);
        secondStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        secondStage.show();
    }

    @FXML
    void initialize() {
    }
}
