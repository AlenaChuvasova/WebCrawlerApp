package test.chuvasova.webcrawler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 * @author A.Chuvasova
 * Main app class to run test example
 */
public class CrawlerApp extends Application {

    private static final Logger log = Logger.getLogger(CrawlerApp.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/crawler.fxml"));
        primaryStage.setTitle("Web Crawler");
        primaryStage.setScene(new Scene(root, 405, 526));
        primaryStage.show();
    }

    /**
     * Just a normal psvm method to run the app
     *
     * @param args - command line args, not used
     *             maxLinkDepth - default for link depth
     *             maxPageCount - default for page count
     *             inputKeywords - input list of search words
     */
    public static void main(String[] args) throws Exception {
        launch(args);
        log.info("start searching");
    }
}