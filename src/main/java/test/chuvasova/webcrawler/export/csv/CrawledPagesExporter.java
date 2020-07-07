package test.chuvasova.webcrawler.export.csv;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import test.chuvasova.webcrawler.domain.CrawledPage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author A.Chuvasova
 * Collect and write data to .csv files
 */
public class CrawledPagesExporter {

    /**
     * Collect and write data to .csv files (write all data in certain format in it)
     * @param pages - list of pages we crawled
     * @param exportPath - path to the generated csv files
     */
    public static void exportCrawledToCSV(List<CrawledPage> pages, String exportPath, List<String> keywords) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        CSVWriter writer = new CSVWriter(new FileWriter(exportPath));
        for (String record : pages.stream().map(t-> t.toCSVString(keywords)).collect(Collectors.toList())) {
            writer.writeNext(record.split(" "));
        }
        writer.close();
    }
}
