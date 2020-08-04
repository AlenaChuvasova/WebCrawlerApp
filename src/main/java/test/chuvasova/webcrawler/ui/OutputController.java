package test.chuvasova.webcrawler.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.PrintStream;

import static test.chuvasova.webcrawler.ui.UiController.inputKeywords;
import static test.chuvasova.webcrawler.ui.UiController.topTenPages;

public class OutputController {

    @FXML
    private TextArea area;
    @FXML
    private Button close;
    @FXML
    private Button result;
    private PrintStream ps;

    @FXML
    public void exit(ActionEvent event) {
        Platform.exit();
    }

    public void button(ActionEvent event) {
        System.setOut(ps);
        System.setErr(ps);
        topTenPages.stream().forEach(t -> System.out.println(t.toCSVString(inputKeywords)));
    }

    @FXML
    void initialize() {
        ps = new PrintStream(new Console(area));
    }
}
